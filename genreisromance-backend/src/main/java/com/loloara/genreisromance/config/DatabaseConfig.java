package com.loloara.genreisromance.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.metrics.micrometer.MicrometerMetricsTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Arrays;

public class DatabaseConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment environment;

    @Autowired(required = false)
    private MicrometerMetricsTracker micrometerMetricsTracker;

    @Bean(destroyMethod = "close")
    public DataSource dataSource(DataSourceProperties dataSourceProperties, ApplicationProperties applicationProperties) throws Exception {
        log.debug("Configuring Datasource");
        log.debug("url: " + dataSourceProperties.getUrl());
        log.debug("driver: " + dataSourceProperties.getDriverClassName());
        log.debug("username: " + dataSourceProperties.getUsername());
        log.debug("psssword: " + dataSourceProperties.getPassword());

        if(dataSourceProperties.getUrl() == null) {
            log.error("Your database connection pool configuration is incorrect! The application cannot start. " +
                    "Please check your Spring profile, current profiles are: {}", Arrays.toString(environment.getActiveProfiles()));
            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }

        HikariConfig config = new HikariConfig();

        config.setDataSourceClassName(dataSourceProperties.getDriverClassName());
        config.setJdbcUrl(dataSourceProperties.getUrl());
        config.setUsername(dataSourceProperties.getUsername() != null ? dataSourceProperties.getUsername() : "");
        config.setPassword(dataSourceProperties.getPassword() != null ? dataSourceProperties.getPassword() : "");

        if(micrometerMetricsTracker != null) {
            System.out.println("micrometer: NOT NULL");
            config.setMetricRegistry(micrometerMetricsTracker);
        }

        return new HikariDataSource(config);
    }
}
