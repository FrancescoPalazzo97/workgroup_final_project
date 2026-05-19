package com.final_project.workgroup_final_project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.final_project.workgroup_final_project.models.Borrowing;

public interface BorrowingRepo extends JpaRepository<Borrowing, Integer> {

<<<<<<< HEAD
    List<Borrowing> findByUserEmail(String email);
=======
    List<Borrowing> findByUserId(Long userId);

>>>>>>> e60386c94d19b25e074e1256d0eb9af7516d57f1
}
