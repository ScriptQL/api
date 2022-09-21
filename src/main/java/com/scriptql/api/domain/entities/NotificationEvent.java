package com.scriptql.api.domain.entities;

import com.scriptql.api.domain.enums.DatabaseDriver;
import lombok.Data;

@Data
public class NotificationEvent {

    public NotificationEvent(long id, String description, String name, String email, String target, DatabaseDriver type, String sql, String url) {
        this.body = String.format("""
                **Review Task #%d**
                %s

                **name** : *%s*
                **email** : *%s*
                **target** : *%s*
                **type** : *%s*

                ```SQL
                %s```
                **More info :** *%s*
                """, id, description, name, email, target, type.name(), sql, url);
    }

    private String body;

}
