package com.final_project.workgroup_final_project.exceptions;

public class BorrowingNotFoundException extends RuntimeException {
    public BorrowingNotFoundException(Integer id) {
        super("Borrowing with id " + id + " not found!");
    }
}
