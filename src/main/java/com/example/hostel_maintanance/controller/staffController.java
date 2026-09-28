package com.example.hostel_maintanance.controller;

import com.example.hostel_maintanance.model.staff;
import com.example.hostel_maintanance.repository.staffRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class staffController {

    private final staffRepository repository;

    public staffController(staffRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public staff create(@Valid @RequestBody staff staff) {
        return repository.save(staff);
    }

    @GetMapping
    public List<staff> getAll() {
        return repository.findAll();
    }
}