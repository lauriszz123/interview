package com.laurynas.interview.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laurynas.interview.model.Payment;
import com.laurynas.interview.model.PaymentType;
import com.laurynas.interview.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private NotificationService notificationService;

    public Payment createPayment(Payment payment) {
        validatePayment(payment);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setCanceled(false);
        Payment saved = repository.save(payment);
        // Notify for TYPE1 and TYPE2 only, asynchronously
        if (saved.getType() == PaymentType.TYPE1 ||
            saved.getType() == PaymentType.TYPE2) {
            notificationService.notifyExternalService(saved);
        }
        return saved;
    }

    private void validatePayment(Payment payment) {
        switch (payment.getType()) {
            case TYPE1 -> {
                if (!"EUR".equals(payment.getCurrency())) throw new IllegalArgumentException("TYPE1 requires EUR");
                if (payment.getDetails() == null) throw new IllegalArgumentException("TYPE1 requires details");
            }
            case TYPE2 -> {
                if (!"USD".equals(payment.getCurrency())) throw new IllegalArgumentException("TYPE2 requires USD");
            }
            case TYPE3 -> {
                if (payment.getCreditorBic() == null) throw new IllegalArgumentException("TYPE3 requires BIC");
            }
        }
    }

    public boolean paymentExists(Long id) {
        return repository.existsById(id);
    }

    public boolean isPaymentCanceled(Long id) {
        Payment payment = repository.findById(id).get();
        return payment.isCanceled();
    }

    public boolean isPaymentCreatedToday(Long id) {
        Payment payment = repository.findById(id).get();
        return isToday(payment.getCreatedAt());
    }

    public Payment cancelPayment(Long id) {
        Payment payment = repository.findById(id).get();

        // Using seconds because we need a faster calculation
        // and the fee is based on the time since creation.
        long h = Duration.between(payment.getCreatedAt(), LocalDateTime.now()).toSeconds();
        BigDecimal k = switch (payment.getType()) {
            case TYPE1 -> BigDecimal.valueOf(0.05);
            case TYPE2 -> BigDecimal.valueOf(0.1);
            case TYPE3 -> BigDecimal.valueOf(0.15);
        };

        BigDecimal fee = BigDecimal.valueOf(h).multiply(k);
        payment.setCanceled(true);
        payment.setCancelationFee(fee);
        return repository.save(payment);
    }

    public List<Payment> getAllPayments(BigDecimal amountFilter) {
        return amountFilter != null
            ? repository.findByCanceledFalseAndAmount(amountFilter)
            : repository.findByCanceledFalse();
    }

    public Payment getPaymentById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    private boolean isToday(LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(LocalDate.now());
    }
}