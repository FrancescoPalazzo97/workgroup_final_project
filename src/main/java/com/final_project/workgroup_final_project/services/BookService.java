package com.final_project.workgroup_final_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.final_project.workgroup_final_project.models.records.BookDetailResponse;
import com.final_project.workgroup_final_project.models.records.BookRequest;
import com.final_project.workgroup_final_project.models.records.BookResponse;
import com.final_project.workgroup_final_project.exceptions.BookNotFoundException;
import com.final_project.workgroup_final_project.models.Book;
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

    public BookDetailResponse getById(Integer id) {
        Book book = findById(id);
        return toDetailResponse(book);
    }

    public BookResponse save(BookRequest newBookRequest) {
        Book newBook = new Book();
        applyRequest(newBook, newBookRequest);
        newBook.setDisponibile(true);
        return toResponse(bookRepo.save(newBook));
    }

    public BookResponse update(Integer id, BookRequest bookRequestUpdate) {
        Book book = findById(id);
        applyRequest(book, bookRequestUpdate);
        return toResponse(bookRepo.save(book));
    }

    public void delete(Integer id) {
        if (!bookRepo.existsById(id)) {
            throw new BookNotFoundException(id);
        }

        bookRepo.deleteById(id);
    }

    private void applyRequest(Book book, BookRequest request) {
        book.setTitolo(request.titolo());
        book.setAutore(request.autore());
        book.setAnnoPubblicazione(request.annoPubblicazione());
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitolo(),
                book.getAutore(),
                book.getAnnoPubblicazione(),
                book.getDisponibile());
    }

    private BookDetailResponse toDetailResponse(Book book) {
        return new BookDetailResponse(
                book.getId(),
                book.getTitolo(),
                book.getAutore(),
                book.getAnnoPubblicazione(),
                book.getDisponibile());
    }

}
