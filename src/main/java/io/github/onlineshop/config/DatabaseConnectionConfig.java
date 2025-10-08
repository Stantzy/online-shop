package io.github.onlineshop.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableConfigurationProperties(DatabaseConfig.class)
public class DatabaseConnectionConfig {
    private static final Logger log =
            LoggerFactory.getLogger(DatabaseConnectionConfig.class);
    private final DatabaseConfig databaseConfig;

    public DatabaseConnectionConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Profile("dev")
    @Bean
    public String devDatabaseConnection() {
        log.info("Connected to Dev Database: {}", databaseConfig.getUrl());
        return "DB Connection for Dev";
    }

    @Profile("prod")
    @Bean
    public String prodDatabaseConnection() {
        log.info("Connected to Prod Database: {}", databaseConfig.getUrl());
        return "DB Connection for Prod";
    }
}
