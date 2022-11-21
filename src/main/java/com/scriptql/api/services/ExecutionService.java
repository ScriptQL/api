package com.scriptql.api.services;

import com.opencsv.CSVWriter;
import com.scriptql.api.domain.entities.DatabaseConnection;
import com.scriptql.api.domain.entities.Query;
import com.scriptql.api.domain.enums.DatabaseDriver;
import com.scriptql.api.domain.enums.QueryStatus;
import com.scriptql.api.domain.repositories.QueryRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ExecutionService {

    private final QueryRepository repository;
    private final ExecutorService scheduler = Executors.newCachedThreadPool();

    public ExecutionService(QueryRepository repository) {
        this.repository = repository;
    }

    public void execute(Query query) {
        this.scheduler.submit(() -> this.onExecute(query));
    }

    private void onExecute(Query query) {
        try (Connection connection = this.open(query.getConnection())) {
            String sql = query.getQuery();
            boolean isSelect = sql.toUpperCase().startsWith("SELECT");
            if (isSelect) {
                connection.setReadOnly(true);
            }
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                if (isSelect) {
                    try (ResultSet rs = ps.executeQuery()) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(stream))) {
                            writer.writeAll(rs, true, false, true);
                        }
                        query.setResult(stream.toByteArray());
                    }
                } else {
                    int result = ps.executeUpdate();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(stream))) {
                        writer.writeNext(new String[]{"updated", String.valueOf(result)});
                    }
                    query.setResult(stream.toByteArray());
                }
            }
            query.setStatus(QueryStatus.DONE);
        } catch (SQLException | IOException e) {
            query.setStatus(QueryStatus.ERROR);
            query.setError(e.getMessage());
        } finally {
            query.setExecutionDate(System.currentTimeMillis());
            query.setUpdatedAt(System.currentTimeMillis());
            this.repository.save(query);
        }
    }

    private Connection open(DatabaseConnection details) throws SQLException {
        String url;
        if (details.getDriver() == DatabaseDriver.MYSQL) {
            url = String.format("jdbc:mysql://%s:%s/%s",
                    details.getHost(), details.getPort(), details.getDatabase());
        } else {
            url = String.format("jdbc:postgresql://%s:%s/%s",
                    details.getHost(), details.getPort(), details.getDatabase());
        }
        return DriverManager.getConnection(url, details.getUsername(), details.getPassword());
    }

}
