package com.final_project.workgroup_final_project.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.final_project.workgroup_final_project.models.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {

}
