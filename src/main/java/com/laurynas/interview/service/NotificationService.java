package com.laurynas.interview.service;

import com.laurynas.interview.config.NotificationConfig;
import com.laurynas.interview.model.Notification;
import com.laurynas.interview.model.Payment;
import com.laurynas.interview.model.PaymentType;
import com.laurynas.interview.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationConfig notificationConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public void notifyExternalService(Payment payment) {
        if (!notificationConfig.getEnabled()) return;

        if (payment.getType() != PaymentType.TYPE1 && payment.getType() != PaymentType.TYPE2) return;

        String url = payment.getType() == PaymentType.TYPE1
                                            ? this.notificationConfig.getType1Url()
                                            : this.notificationConfig.getType2Url();

        Notification notification = new Notification();
        notification.setPaymentId(payment.getId());
        notification.setPaymentType(payment.getType().name());
        notification.setUrl(url);
        notification.setNotifiedAt(LocalDateTime.now());

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            notification.setHttpStatus(response.getStatusCode().value());
            notification.setSuccess(response.getStatusCode().is2xxSuccessful());
        } catch (Exception e) {
            notification.setSuccess(false);
            notification.setErrorMessage(e.getMessage());
            notification.setHttpStatus(0);
        }

        notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
