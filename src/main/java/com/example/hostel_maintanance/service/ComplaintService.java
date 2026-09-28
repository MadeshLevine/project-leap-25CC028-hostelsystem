package com.example.hostel_maintanance.service;

import com.example.hostel_maintanance.model.complaint;
import com.example.hostel_maintanance.repository.complaintRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        List<complaint> complaints = repository.findAll();

        for (complaint complaint : complaints) {
            checkOverdue(complaint);
        }

        return complaints;
    }

    public complaint getComplaint(Long id) {

        complaint complaint = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        checkOverdue(complaint);

        return complaint;
    }

    private void checkOverdue(complaint complaint) {

        if (complaint.getStatus().equals("OPEN")
                && complaint.getCreatedAt().isBefore(LocalDateTime.now().minusDays(5))) {

            complaint.setOverdue(true);
            repository.save(complaint);
        }
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