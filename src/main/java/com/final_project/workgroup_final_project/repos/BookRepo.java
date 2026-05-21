package com.final_project.workgroup_final_project.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.final_project.workgroup_final_project.models.Book;

import jakarta.persistence.LockModeType;

public interface BookRepo extends JpaRepository<Book, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findByIdForUpdate(@Param("id") Integer id);
}
