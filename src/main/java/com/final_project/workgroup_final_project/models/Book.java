package com.final_project.workgroup_final_project.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(min = 2, max = 255, message = "Il titolo non può superare i 255 caratteri")
    @Column(nullable = false)
    private String titolo;

    @NotBlank(message = "L'autore è obbligatorio")
    @Size(min = 2, max = 255, message = "Il nome dell'autore non può superare i 255 caratteri")
    @Column(nullable = false)
    private String autore;

    @NotNull(message = "L'anno di pubblicazione è obbligatorio")
    @Min(value = 1000, message = "L'anno di pubblicazione deve essere almeno 1000")
    @Max(value = 2100, message = "L'anno di pubblicazione non può superare il 2100")
    @Column(name = "anno_pubblicazione", nullable = false)
    private Integer annoPubblicazione;

    @NotNull(message = "Il campo disponibile è obbligatorio")
    @Column(nullable = false)
    private Boolean disponibile;

    @JsonManagedReference("book-borrowings")
    @OneToMany(mappedBy = "book", cascade = { CascadeType.REMOVE })
    private List<Borrowing> borrowings;

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public Integer getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(Integer annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public Boolean getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(Boolean disponibile) {
        this.disponibile = disponibile;
    }

    public Boolean isDisponibile() {
        return this.disponibile;
    }

    public List<Borrowing> getBorrowings() {
        return this.borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

}
