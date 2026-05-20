package com.final_project.workgroup_final_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_project.workgroup_final_project.exceptions.BookNotFoundException;
import com.final_project.workgroup_final_project.exceptions.BookUnavailableException;
import com.final_project.workgroup_final_project.exceptions.BorrowingNotFoundException;
import com.final_project.workgroup_final_project.models.Book;
import com.final_project.workgroup_final_project.models.Borrowing;
import com.final_project.workgroup_final_project.models.User;
import com.final_project.workgroup_final_project.models.records.BorrowingRequest;
import com.final_project.workgroup_final_project.models.records.BorrowingResponse;
import com.final_project.workgroup_final_project.repos.BookRepo;
import com.final_project.workgroup_final_project.repos.BorrowingRepo;
import com.final_project.workgroup_final_project.repos.UserRepository;

@Service
public class BorrowingService {

    private final BorrowingRepo borrowingRepo;
    private final BookRepo bookRepo;
    private final UserRepository userRepository;

    public BorrowingService(BorrowingRepo borrowingRepo, BookRepo bookRepo, UserRepository userRepository) {
        this.borrowingRepo = borrowingRepo;
        this.bookRepo = bookRepo;
        this.userRepository = userRepository;
    }

    public List<BorrowingResponse> findAll() {
        List<Borrowing> borrowings = isAdmin()
                ? borrowingRepo.findAll()
                : borrowingRepo.findByUserEmail(currentUserEmail());

        return borrowings
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Borrowing findById(Integer id) {
        Optional<Borrowing> result = borrowingRepo.findById(id);

        if (result.isEmpty()) {
            throw new BorrowingNotFoundException(id);
        }

        return result.get();
    }

    public BorrowingResponse getById(Integer id) {
        Borrowing borrowing = findById(id);
        checkCanAccess(borrowing);
        return toResponse(borrowing);
    }

    @Transactional
    public BorrowingResponse save(BorrowingRequest request) {
        Borrowing borrowing = toEntity(request);
        borrowing.setReturnDate(null);
        Book book = borrowing.getBook();

        if (borrowingRepo.existsActiveBorrowingByBookId(book.getId())) {
            throw new BookUnavailableException();
        }

        borrowing.setUser(currentUser());
        Borrowing saved = borrowingRepo.save(borrowing);
        recomputeAvailability(book);

        return toResponse(saved);
    }

    @Transactional
    public BorrowingResponse update(Integer id, BorrowingRequest request) {
        Borrowing existing = findById(id);
        checkCanAccess(existing);
        Book oldBook = existing.getBook();
        Borrowing updated = toEntity(request);
        Book newBook = updated.getBook();
        boolean sameBook = oldBook.getId().equals(newBook.getId());

        if (updated.getReturnDate() == null
                && borrowingRepo.existsOtherActiveBorrowingByBookId(newBook.getId(), existing.getId())) {
            throw new BookUnavailableException();
        }

        updated.setId(existing.getId());
        updated.setUser(existing.getUser());
        Borrowing saved = borrowingRepo.save(updated);

        recomputeAvailability(oldBook);
        if (!sameBook) {
            recomputeAvailability(newBook);
        }

        return toResponse(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only admins can delete borrowings");
        }

        Borrowing borrowing = findById(id);
        Book book = borrowing.getBook();
        borrowingRepo.delete(borrowing);
        recomputeAvailability(book);
    }

    private Borrowing toEntity(BorrowingRequest request) {
        Book book = bookRepo.findById(request.bookId())
                .orElseThrow(() -> new BookNotFoundException(request.bookId()));

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setBorrowingDate(request.borrowingDate());
        borrowing.setReturnDate(request.returnDate());
        borrowing.setNotes(request.notes());
        return borrowing;
    }

    private void recomputeAvailability(Book book) {
        boolean hasActive = borrowingRepo.existsActiveBorrowingByBookId(book.getId());
        book.setDisponibile(!hasActive);
        bookRepo.save(book);
    }

    private void checkCanAccess(Borrowing borrowing) {
        if (isAdmin()) {
            return;
        }

        User user = borrowing.getUser();
        if (user == null || !currentUserEmail().equals(user.getEmail())) {
            throw new AccessDeniedException("You can access only your own borrowings");
        }
    }

    private User currentUser() {
        return userRepository.findByEmail(currentUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
    }

    private String currentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new IllegalArgumentException("Authentication is required");
        }

        return authentication.getName();
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null && authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

    private BorrowingResponse toResponse(Borrowing borrowing) {
        User user = borrowing.getUser();

        return new BorrowingResponse(
                borrowing.getId(),
                borrowing.getBook().getId(),
                user != null ? user.getId() : null,
                user != null ? user.getFullName() : null,
                borrowing.getBorrowingDate(),
                borrowing.getReturnDate(),
                borrowing.getNotes());
    }
}
