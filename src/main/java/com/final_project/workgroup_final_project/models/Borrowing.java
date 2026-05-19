package com.final_project.workgroup_final_project.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "borrowings")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference("book-borrowings")
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
<<<<<<< HEAD
    @JoinColumn(name = "user_id")
=======
    @JoinColumn(name = "user_id", nullable = false)
>>>>>>> e60386c94d19b25e074e1256d0eb9af7516d57f1
    private User user;

    @NotNull(message = "The borrowing date cannot be null")
    @PastOrPresent(message = "The borrowing date cannot be set in the future")
    private LocalDate borrowinDate;

    @FutureOrPresent(message = "The return date cannot be set in the past")
    private LocalDate returDate;

    @Lob
    private String notes;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getBorrowinDate() {
        return this.borrowinDate;
    }

    public void setBorrowinDate(LocalDate borrowinDate) {
        this.borrowinDate = borrowinDate;
    }

    public LocalDate getReturDate() {
        return this.returDate;
    }

    public void setReturDate(LocalDate returDate) {
        this.returDate = returDate;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
