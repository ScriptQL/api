package com.scriptql.scriptqlapi.services;

import com.scriptql.api.Application;
import com.scriptql.api.domain.enums.DatabaseDriver;
import com.scriptql.api.services.NotificationService;
import com.scriptql.api.domain.entities.NotificationEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    @Test
    public void deveEnviarNotificacao() {
        String url = "http://www.site-de-teste.blogspot.com.br";
        String sql = "select id, nome, valor from tabela_de_teste " +
                "where id = 123 and nome != \"Caballo Homosexual De Las Montañas\"";

        NotificationEvent event = new NotificationEvent(456L, "descricao", "name", "email", "target", DatabaseDriver.MYSQL, sql, url);
        notificationService.sendMessage(event);
    }

}
