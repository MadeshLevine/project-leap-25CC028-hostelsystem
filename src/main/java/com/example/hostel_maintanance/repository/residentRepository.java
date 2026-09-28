package com.example.hostel_maintanance.repository;

import com.example.hostel_maintanance.model.resident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface residentRepository extends JpaRepository<resident, Long> {
}