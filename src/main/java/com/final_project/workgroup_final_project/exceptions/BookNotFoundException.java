package com.final_project.workgroup_final_project.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Integer id) {
        super("Book with id " + id + " not found!");
    }
}
