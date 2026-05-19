package com.final_project.workgroup_final_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.final_project.workgroup_final_project.exceptions.BookNotFoundException;
import com.final_project.workgroup_final_project.exceptions.BorrowingNotFoundException;
import com.final_project.workgroup_final_project.models.Book;
import com.final_project.workgroup_final_project.models.Borrowing;
import com.final_project.workgroup_final_project.models.records.BorrowingRequest;
import com.final_project.workgroup_final_project.models.records.BorrowingResponse;
import com.final_project.workgroup_final_project.repos.BookRepo;
import com.final_project.workgroup_final_project.repos.BorrowingRepo;

@Service
public class BorrowingService {

    private final BorrowingRepo borrowingRepo;
    private final BookRepo bookRepo;

    public BorrowingService(BorrowingRepo borrowingRepo, BookRepo bookRepo) {
        this.borrowingRepo = borrowingRepo;
        this.bookRepo = bookRepo;
    }

    public List<BorrowingResponse> findAll() {
        return borrowingRepo.findAll()
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
        return toResponse(findById(id));
    }

    public BorrowingResponse save(BorrowingRequest request) {
        Borrowing borrowing = toEntity(request);
        return toResponse(borrowingRepo.save(borrowing));
    }

    public BorrowingResponse update(Integer id, BorrowingRequest request) {
        Borrowing existing = findById(id);
        Borrowing updated = toEntity(request);
        updated.setId(existing.getId());
        return toResponse(borrowingRepo.save(updated));
    }

    public void delete(Integer id) {
        if (!borrowingRepo.existsById(id)) {
            throw new BorrowingNotFoundException(id);
        }

        borrowingRepo.deleteById(id);
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
                borrowing.getBorrowinDate(),
                borrowing.getReturDate(),
                borrowing.getNotes());
    }
}
