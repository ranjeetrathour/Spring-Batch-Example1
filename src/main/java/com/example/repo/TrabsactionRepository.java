package com.example.repo;

import com.example.modle.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabsactionRepository extends JpaRepository<Transaction, String> {
}
