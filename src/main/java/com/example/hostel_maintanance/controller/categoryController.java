package com.example.hostel_maintanance.controller;

import com.example.hostel_maintanance.model.category;
import com.example.hostel_maintanance.repository.categoryRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class categoryController {

    private final categoryRepository repository;

    public categoryController(categoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public category create(@Valid @RequestBody category category) {
        return repository.save(category);
    }

    @GetMapping
    public List<category> getAll() {
        return repository.findAll();
    }
}