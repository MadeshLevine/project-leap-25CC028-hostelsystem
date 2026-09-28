package com.example.hostel_maintanance.service;

import com.example.hostel_maintanance.model.complaint;
import com.example.hostel_maintanance.repository.complaintRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    private final complaintRepository repository;

    public ComplaintService(complaintRepository repository) {
        this.repository = repository;
    }

    public complaint createComplaint(complaint complaint) {
        complaint.setStatus("OPEN");
        return repository.save(complaint);
    }

    public List<complaint> getAllComplaints() {
        return repository.findAll();
    }

    public complaint getComplaint(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
    }

    public complaint assignStaff(Long id, Long staffId) {
        complaint complaint = getComplaint(id);
        complaint.setStaffId(staffId);
        return repository.save(complaint);
    }

    public complaint updateStatus(Long id, String status, Long staffId) {

        complaint complaint = getComplaint(id);

        if (status.equals("IN_PROGRESS")
                && complaint.getStatus().equals("OPEN")) {

            complaint.setStatus("IN_PROGRESS");

        } else if (status.equals("RESOLVED")
                && complaint.getStatus().equals("IN_PROGRESS")
                && complaint.getStaffId() != null
                && complaint.getStaffId().equals(staffId)) {

            complaint.setStatus("RESOLVED");

        } else {
            throw new RuntimeException("Invalid status change");
        }

        return repository.save(complaint);
    }
}