package com.laurynas.interview.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laurynas.interview.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByCanceledFalse();
    List<Payment> findByCanceledFalseAndAmount(BigDecimal amount);
}