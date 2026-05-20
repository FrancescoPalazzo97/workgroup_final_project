package com.final_project.workgroup_final_project.exceptions;

public class BookUnavailableException extends RuntimeException {
    public BookUnavailableException() {
        super("Book is already borrowed");
    }
}
