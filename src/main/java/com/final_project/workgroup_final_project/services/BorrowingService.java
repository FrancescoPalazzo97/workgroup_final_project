package com.final_project.workgroup_final_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.final_project.workgroup_final_project.exceptions.BookNotFoundException;
import com.final_project.workgroup_final_project.exceptions.BorrowingNotFoundException;
import com.final_project.workgroup_final_project.models.Book;
import com.final_project.workgroup_final_project.models.Borrowing;
import com.final_project.workgroup_final_project.models.Role;
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
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() == Role.ADMIN) {
            return borrowingRepo.findAll().stream().map(this::toResponse).toList();
        }

        return borrowingRepo.findByUserId(currentUser.getId()).stream().map(this::toResponse).toList();
    }

    public BorrowingResponse getById(Integer id) {
        Borrowing borrowing = findById(id);
        checkOwnershipOrAdmin(borrowing);
        return toResponse(borrowing);
    }

    public BorrowingResponse save(BorrowingRequest request) {
        User currentUser = getAuthenticatedUser();
        Borrowing borrowing = toEntity(request);
        borrowing.setUser(currentUser);
        return toResponse(borrowingRepo.save(borrowing));
    }

    public BorrowingResponse update(Integer id, BorrowingRequest request) {
        Borrowing existing = findById(id);
        checkOwnershipOrAdmin(existing);
        Borrowing updated = toEntity(request);
        updated.setId(existing.getId());
        updated.setUser(existing.getUser());
        return toResponse(borrowingRepo.save(updated));
    }

    public void delete(Integer id) {
        Borrowing borrowing = findById(id);
        checkOwnershipOrAdmin(borrowing);
        borrowingRepo.deleteById(id);
    }

    private Borrowing findById(Integer id) {
        Optional<Borrowing> result = borrowingRepo.findById(id);

        if (result.isEmpty()) {
            throw new BorrowingNotFoundException(id);
        }

        return result.get();
    }

    private void checkOwnershipOrAdmin(Borrowing borrowing) {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() == Role.ADMIN) {
            return;
        }

        if (!borrowing.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can only access your own borrowings");
        }
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }

    private Borrowing toEntity(BorrowingRequest request) {
        Book book = bookRepo.findById(request.bookId())
                .orElseThrow(() -> new BookNotFoundException(request.bookId()));

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setBorrowinDate(request.borrowingDate());
        borrowing.setReturDate(request.returnDate());
        borrowing.setNotes(request.notes());
        return borrowing;
    }

    private BorrowingResponse toResponse(Borrowing borrowing) {
        return new BorrowingResponse(
                borrowing.getId(),
                borrowing.getBook().getId(),
                borrowing.getUser().getId(),
                borrowing.getUser().getFullName(),
                borrowing.getBorrowinDate(),
                borrowing.getReturDate(),
                borrowing.getNotes());
    }
}
