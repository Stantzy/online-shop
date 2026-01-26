package io.github.onlineshop.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DatabaseConfig.class)
@RequiredArgsConstructor
public class DatabaseConnectionConfig {
    private static final Logger log =
        LoggerFactory.getLogger(DatabaseConnectionConfig.class);

    private final DatabaseConfig databaseConfig;

    @Profile("dev")
    @Bean
    public DataSource devDatabaseConnection() {
        log.info("Connected to Dev Database: {}", databaseConfig.getUrl());

        return buildDataSourceFromConfig();
    }

    @Profile("prod")
    @Bean
    public DataSource prodDatabaseConnection() {
        log.info("Connected to Prod Database: {}", databaseConfig.getUrl());

        return buildDataSourceFromConfig();
    }

    private DataSource buildDataSourceFromConfig() {
        return DataSourceBuilder.create()
            .url(databaseConfig.getUrl())
            .username(databaseConfig.getUsername())
            .password(databaseConfig.getPassword())
            .build();
    }
}
