package com.laurynas.interview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue
    private Long id;

    private Long paymentId;
    private String paymentType;
    private String url;
    private boolean success;
    private int httpStatus;
    private LocalDateTime notifiedAt;
    private String errorMessage;
}
