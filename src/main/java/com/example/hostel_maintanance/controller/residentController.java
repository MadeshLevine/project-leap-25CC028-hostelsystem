package com.example.hostel_maintanance.controller;

import com.example.hostel_maintanance.model.resident;
import com.example.hostel_maintanance.repository.residentRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
@CrossOrigin(origins = "*")
public class residentController {

    private final residentRepository repository;

    public residentController(residentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public resident create(@Valid @RequestBody resident resident) {
        return repository.save(resident);
    }

    @GetMapping
    public List<resident> getAll() {
        return repository.findAll();
    }
}