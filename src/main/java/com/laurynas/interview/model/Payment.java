package com.laurynas.interview.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
@EqualsAndHashCode
public class Payment {
    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private String currency;
    private String debtorIban;
    private String creditorIban;
    private String details;
    private String creditorBic;
    private LocalDateTime createdAt;
    private boolean canceled;
    private BigDecimal cancelationFee;
}