package com.scriptql.scriptqlapi.services;

import com.scriptql.api.Application;
import com.scriptql.api.domain.entities.DatabaseConnection;
import com.scriptql.api.domain.entities.Query;
import com.scriptql.api.domain.entities.User;
import com.scriptql.api.domain.enums.DatabaseDriver;
import com.scriptql.api.services.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    @Test
    public void deveEnviarNotificacao() {
        User user = new User();
        user.setName("Elma Maria");
        user.setEmail("elma_maria@scriptql.com");

        DatabaseConnection connection = new DatabaseConnection();
        connection.setDriver(DatabaseDriver.POSTGRES);
        connection.setName("Staging");

        Query query = new Query();
        query.setId(123L);
        query.setRequester(user);
        query.setConnection(connection);
        query.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        query.setQuery("select id, nome, valor from tabela_de_teste \n where id = 123 and nome != \"Teste Name\"");

        notificationService.sendMessage(query);
    }

}
