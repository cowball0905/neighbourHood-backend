package com.example.neighbourHood_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class DatabaseController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db-test")
    public String testDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            String dbName = connection.getMetaData().getDatabaseProductName();
            String dbVersion = connection.getMetaData().getDatabaseProductVersion();
            return "✅ Database connected!\nDatabase: " + dbName + "\nVersion: " + dbVersion;
        } catch (Exception e) {
            return "❌ Database connection failed: " + e.getMessage();
        }
    }
}
