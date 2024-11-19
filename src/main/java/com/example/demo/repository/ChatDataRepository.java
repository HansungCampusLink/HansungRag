package com.example.demo.repository;

import com.example.demo.entity.ChatData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatDataRepository extends JpaRepository<ChatData, Long> {
}
