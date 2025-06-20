package com.laurynas.interview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class NotificationConfig {
    @Value("${notification.enabled}")
    private Boolean enabled;

    @Value("${notification.url.type1}")
    private String type1Url;
    
    @Value("${notification.url.type2}")
    private String type2Url;
}
