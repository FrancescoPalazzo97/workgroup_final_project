package com.final_project.workgroup_final_project.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.final_project.workgroup_final_project.models.Borrowing;

public interface BorrowingRepo extends JpaRepository<Borrowing, Integer> {
    List<Borrowing> findByUserEmail(String email);

    @Query("""
            select count(b) > 0
            from Borrowing b
            where b.book.id = :bookId
            and (b.returDate is null or b.returDate >= :today)
            """)
    boolean existsActiveBorrowingByBookId(@Param("bookId") Integer bookId, @Param("today") LocalDate today);

    @Query("""
            select count(b) > 0
            from Borrowing b
            where b.book.id = :bookId
            and b.id <> :borrowingId
            and (b.returDate is null or b.returDate >= :today)
            """)
    boolean existsOtherActiveBorrowingByBookId(
            @Param("bookId") Integer bookId,
            @Param("borrowingId") Integer borrowingId,
            @Param("today") LocalDate today);
}
