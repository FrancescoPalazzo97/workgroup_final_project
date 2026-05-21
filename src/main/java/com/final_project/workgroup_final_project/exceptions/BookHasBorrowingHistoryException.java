package com.final_project.workgroup_final_project.exceptions;

public class BookHasBorrowingHistoryException extends RuntimeException {
    public BookHasBorrowingHistoryException(Integer id) {
        super("Book with id " + id + " cannot be deleted because it has borrowing history");
    }
}
