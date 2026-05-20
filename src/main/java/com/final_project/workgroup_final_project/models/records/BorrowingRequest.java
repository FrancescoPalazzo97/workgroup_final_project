package com.final_project.workgroup_final_project.models.records;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record BorrowingRequest(
        @NotNull(message = "Il libro è obbligatorio") Integer bookId,

        @NotNull(message = "La data di prestito è obbligatoria")
        @PastOrPresent(message = "La data di prestito non può essere nel futuro")
        LocalDate borrowingDate,

        LocalDate returnDate,

        String notes) {

        @AssertTrue(message = "La data di restituzione non può essere antecedente alla data di prestito")
        public boolean isReturnDateNotBeforeBorrowingDate() {
                return returnDate == null || borrowingDate == null || !returnDate.isBefore(borrowingDate);
        }
}
