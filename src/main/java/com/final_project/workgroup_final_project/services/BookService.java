package com.final_project.workgroup_final_project.services;

import org.springframework.stereotype.Service;

import com.final_project.workgroup_final_project.repos.BookRepo;

@Service
public class BookService {
    private BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

}
