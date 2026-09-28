package com.example.hostel_maintanance.repository;

import com.example.hostel_maintanance.model.complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface complaintRepository extends JpaRepository<complaint, Long> {

    List<complaint> findByResidentId(Long residentId);

    List<complaint> findByStaffId(Long staffId);
}