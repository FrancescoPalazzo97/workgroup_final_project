package com.final_project.workgroup_final_project.models.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record BookRequest(
        @NotBlank(message = "Il titolo è obbligatorio") @Size(min = 2, max = 255, message = "Il titolo non può superare i 255 caratteri") String titolo,

        @NotBlank(message = "L'autore è obbligatorio") @Size(min = 2, max = 255, message = "Il nome dell'autore non può superare i 255 caratteri") String autore,

        @NotNull(message = "L'anno di pubblicazione è obbligatorio")
        @Min(value = 1000, message = "L'anno di pubblicazione deve essere almeno 1000")
        @Max(value = 2100, message = "L'anno di pubblicazione non può superare il 2100")
        Integer annoPubblicazione) {
}

