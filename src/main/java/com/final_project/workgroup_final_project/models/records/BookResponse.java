package com.final_project.workgroup_final_project.models.records;

public record BookResponse(
                Integer id,
                String titolo,
                String autore,
                Integer annoPubblicazione,
                Boolean disponibile) {

}
