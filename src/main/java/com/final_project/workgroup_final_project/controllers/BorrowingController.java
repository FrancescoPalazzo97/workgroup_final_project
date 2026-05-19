package com.final_project.workgroup_final_project.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.final_project.workgroup_final_project.models.records.BorrowingRequest;
import com.final_project.workgroup_final_project.models.records.BorrowingResponse;
import com.final_project.workgroup_final_project.services.BorrowingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/borrowings")
@SecurityRequirement(name = "bearerAuth")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping
    public ResponseEntity<List<BorrowingResponse>> findAll() {
        return ResponseEntity.ok(borrowingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(borrowingService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BorrowingResponse> save(@Valid @RequestBody BorrowingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowingResponse> update(@PathVariable Integer id,
            @Valid @RequestBody BorrowingRequest request) {
        return ResponseEntity.ok(borrowingService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        borrowingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
