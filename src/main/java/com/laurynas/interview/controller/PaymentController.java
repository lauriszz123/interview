package com.laurynas.interview.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import com.laurynas.interview.service.PaymentService;
import com.laurynas.interview.model.Payment;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public Payment create(@RequestBody Payment payment, HttpServletRequest request) {
        System.out.println("Client IP: " + request.getRemoteAddr());
        return paymentService.createPayment(payment);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Payment> cancel(@PathVariable Long id) {
        if (!paymentService.paymentExists(id)) {
            return ResponseEntity.notFound().build();
        }

        if (paymentService.isPaymentCanceled(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if (!paymentService.isPaymentCreatedToday(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(paymentService.cancelPayment(id));
    }

    @GetMapping
    public List<Payment> getAll(@RequestParam(required = false) BigDecimal amount) {
        return paymentService.getAllPayments(amount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        if (!paymentService.paymentExists(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }
}
