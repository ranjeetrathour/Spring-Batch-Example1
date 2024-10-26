package com.example.modle;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Entity
@Data
public class Transaction {

    @Id
    private String id;

    private String accountNumber;
    private Float amount;
    private LocalDateTime transactionDate;

    @PrePersist
    void generatedId(){
        this.id = UUID.randomUUID().toString();
    }
}
