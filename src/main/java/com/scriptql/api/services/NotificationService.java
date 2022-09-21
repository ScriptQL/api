package com.scriptql.api.services;

import com.scriptql.api.domain.entities.NotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class NotificationService {

    public void sendMessage(NotificationEvent notificationEvent) {

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/notify/apprise");

        HttpEntity<?> entity = new HttpEntity<>(notificationEvent);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                entity,
                String.class);

        log.debug("teste");
    }

}
