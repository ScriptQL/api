package com.scriptql.api.services;

import com.scriptql.api.domain.NotificationEvent;
import com.scriptql.api.domain.entities.DatabaseConnection;
import com.scriptql.api.domain.entities.Query;
import com.scriptql.api.domain.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NotificationService {

    @Value("${scriptql.apprise.enabled}")
    private boolean enabled;

    @Value("${scriptql.apprise.url}")
    private String appriseUrl;

    @Value("${scriptql.front}")
    private String frontUrl;

    public void sendMessage(Query query) {
        if (!this.enabled) {
            return;
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(appriseUrl);
        NotificationEvent event = new NotificationEvent(getMessage(query));
        new RestTemplate().exchange(builder.toUriString(), HttpMethod.POST, new HttpEntity<>(event), String.class);
    }

    private String getMessage(Query query) {
        User user = query.getRequester();
        DatabaseConnection connection = query.getConnection();
        String url = frontUrl.concat("info/").concat(query.getId().toString());
        return String.format("""
                **Query Review Notification**
                \s%s
                **Name** : *%s*
                **E-mail** : *%s*
                **Database** : *%s*
                **Driver** : *%s*
                ```SQL
                %s```
                **See full details :** *%s*
                """, query.getDescription(), user.getName(), user.getEmail(), connection.getName(), connection.getDriver().name(), query.getQuery(), url);
    }

}
