package com.final_project.workgroup_final_project.models.records;

import java.time.LocalDate;

public record BorrowingResponse(
        Integer id,
        Integer bookId,
        Long userId,
        String userFullName,
        LocalDate borrowingDate,
        LocalDate returnDate,
        String notes) {
}
