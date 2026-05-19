package com.final_project.workgroup_final_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.final_project.workgroup_final_project.models.records.BookDetailResponse;
import com.final_project.workgroup_final_project.models.records.BookRequest;
import com.final_project.workgroup_final_project.models.records.BookResponse;
import com.final_project.workgroup_final_project.exceptions.BookNotFoundException;
import com.final_project.workgroup_final_project.models.Book;
import com.final_project.workgroup_final_project.models.records.BorrowingResponse;
import com.final_project.workgroup_final_project.repos.BookRepo;

@Service
public class BookService {

    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<BookResponse> findAll() {
        return bookRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Book findById(Integer id) {
        Optional<Book> result = bookRepo.findById(id);

        if (result.isEmpty()) {
            throw new BookNotFoundException(id);
        }

        return result.get();
    }

    @Transactional
    public BookDetailResponse getById(Integer id) {
        Book book = findById(id);

        List<BorrowingResponse> borrowings = book.getBorrowings()
                .stream()
                .map(b -> new BorrowingResponse(
                        b.getId(),
                        b.getBook().getId(),
                        b.getBorrowinDate(),
                        b.getReturDate(),
                        b.getNotes()))
                .toList();

        return new BookDetailResponse(
                book.getId(),
                book.getTitolo(),
                book.getAutore(),
                book.getAnnoPubblicazione(),
                book.getDisponibile(),
                borrowings);
    }

    public BookResponse save(BookRequest newBookRequest) {
        Book newBook = toEntity(newBookRequest);
        return toResponse(bookRepo.save(newBook));
    }

    public BookResponse update(Integer id, BookRequest bookRequestUpdate) {
        Book oldBook = findById(id);
        Book bookUpdate = toEntity(bookRequestUpdate);
        bookUpdate.setId(oldBook.getId());
        return toResponse(bookRepo.save(bookUpdate));
    }

    public void delete(Integer id) {
        if (!bookRepo.existsById(id)) {
            throw new BookNotFoundException(id);
        }

        bookRepo.deleteById(id);
    }

    private Book toEntity(BookRequest bookRequest) {
        return new Book(
                bookRequest.titolo(),
                bookRequest.autore(),
                bookRequest.annoPubblicazione(),
                bookRequest.disponibile());
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitolo(),
                book.getAutore(),
                book.getAnnoPubblicazione(),
                book.getDisponibile());
    }
}
