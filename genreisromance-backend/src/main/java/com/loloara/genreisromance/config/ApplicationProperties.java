package com.loloara.genreisromance.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private final DataSource dataSource = new DataSource();

    @Bean
    public DataSource getDataSource(){
        return dataSource;
    }

    @Getter
    @Setter
    public static class DataSource {
        private boolean cachePrepStmts = true;
        private int prepStmtCacheSize = 250;
        private int prepStmtCacheSqlLimit = 2048;
        private boolean useServerPrepStmts = true;
        private boolean useLocalSessionState = true;
        private boolean rewriteBatchedStatements = true;
        private boolean cacheResultSetMetadata = true;
        private boolean cacheServerConfiguration = true;
        private boolean elideSetAutoCommits = true;
        private boolean maintainTimeStats = false;
    }
}
