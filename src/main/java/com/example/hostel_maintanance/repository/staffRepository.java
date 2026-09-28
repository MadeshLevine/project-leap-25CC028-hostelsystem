package com.example.hostel_maintanance.repository;

import com.example.hostel_maintanance.model.staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface staffRepository extends JpaRepository<staff, Long> {
}