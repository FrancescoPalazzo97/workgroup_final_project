package com.final_project.workgroup_final_project.services;

import java.time.LocalDate;
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
        Book book = borrowing.getBook();
        checkBookCanBeBorrowed(book);
        checkBookHasNoActiveBorrowing(book);

        borrowing.setUser(currentUser());
        markBookUnavailable(book);

        return toResponse(borrowingRepo.save(borrowing));
    }

    @Transactional
    public BorrowingResponse update(Integer id, BorrowingRequest request) {
        Borrowing existing = findById(id);
        checkCanAccess(existing);
        Borrowing updated = toEntity(request);
        Book oldBook = existing.getBook();
        Book newBook = updated.getBook();
        boolean sameBook = oldBook.getId().equals(newBook.getId());

        if (!sameBook) {
            checkBookCanBeBorrowed(newBook);
        }
        if (borrowingRepo.existsOtherActiveBorrowingByBookId(newBook.getId(), existing.getId(), LocalDate.now())) {
            throw new BookUnavailableException();
        }

        updated.setId(existing.getId());
        updated.setUser(existing.getUser());
        Borrowing saved = borrowingRepo.save(updated);

        markBookUnavailable(newBook);

        return toResponse(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!isAdmin()) {
            throw new AccessDeniedException("Only admins can delete borrowings");
        }

        Borrowing borrowing = findById(id);
        borrowingRepo.deleteById(id);
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

    private void checkBookCanBeBorrowed(Book book) {
        if (Boolean.FALSE.equals(book.getDisponibile())) {
            throw new BookUnavailableException();
        }
    }

    private void checkBookHasNoActiveBorrowing(Book book) {
        if (borrowingRepo.existsActiveBorrowingByBookId(book.getId(), LocalDate.now())) {
            throw new BookUnavailableException();
        }
    }

    private void markBookUnavailable(Book book) {
        book.setDisponibile(false);
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
