package com.final_project.workgroup_final_project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.final_project.workgroup_final_project.models.Borrowing;

public interface BorrowingRepo extends JpaRepository<Borrowing, Integer> {
    List<Borrowing> findByUserEmail(String email);
}
