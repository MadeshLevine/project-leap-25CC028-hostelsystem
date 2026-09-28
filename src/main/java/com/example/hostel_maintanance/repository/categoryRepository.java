package com.example.hostel_maintanance.repository;

import com.example.hostel_maintanance.model.category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoryRepository extends JpaRepository<category, Long> {
}