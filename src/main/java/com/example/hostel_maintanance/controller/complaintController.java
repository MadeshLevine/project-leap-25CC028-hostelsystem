package com.example.hostel_maintanance.controller;

import com.example.hostel_maintanance.model.complaint;
import com.example.hostel_maintanance.service.ComplaintService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class complaintController {

    private final ComplaintService service;

    public complaintController(ComplaintService service) {
        this.service = service;
    }

    @PostMapping
    public complaint create(@Valid @RequestBody complaint complaint) {
        return service.createComplaint(complaint);
    }

    @GetMapping
    public List<complaint> getAll() {
        return service.getAllComplaints();
    }

    @GetMapping("/{id}")
    public complaint getOne(@PathVariable Long id) {
        return service.getComplaint(id);
    }

    @PutMapping("/{id}/assign/{staffId}")
    public complaint assignStaff(
            @PathVariable Long id,
            @PathVariable Long staffId) {
        return service.assignStaff(id, staffId);
    }

    @PutMapping("/{id}/status")
    public complaint updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam Long staffId) {
        return service.updateStatus(id, status, staffId);
    }
}