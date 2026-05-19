package com.final_project.workgroup_final_project.models.records;

import java.util.List;

public record BookDetailResponse(
        Integer id,
        String titolo,
        String autore,
        Integer annoPubblicazione,
        Boolean disponibile,
        List<BorrowingResponse> borrowings) {
}
