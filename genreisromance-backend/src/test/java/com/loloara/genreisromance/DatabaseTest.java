package com.loloara.genreisromance;

import com.loloara.genreisromance.config.ApplicationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class DatabaseTest {

    @Autowired
    ApplicationProperties.DataSource dataSource;

    @Test
    public void ApplicationPropertiesTest() {
        boolean cachePrepStmts = true;
        int prepStmtCacheSize = 250;
        int prepStmtCacheSqlLimit = 2048;
        boolean useServerPrepStmts = true;
        boolean useLocalSessionState = true;
        boolean rewriteBatchedStatements = true;
        boolean cacheResultSetMetadata = true;
        boolean cacheServerConfiguration = true;
        boolean elideSetAutoCommits = true;
        boolean maintainTimeStats = false;

        System.out.println("DataSource.toString(): " + dataSource.toString());
        assertEquals(cachePrepStmts, dataSource.isCachePrepStmts());
        assertEquals(prepStmtCacheSize, dataSource.getPrepStmtCacheSize());
        assertEquals(prepStmtCacheSqlLimit, dataSource.getPrepStmtCacheSqlLimit());
        assertEquals(useLocalSessionState, dataSource.isUseLocalSessionState());
        assertEquals(useServerPrepStmts, dataSource.isUseServerPrepStmts());
        assertEquals(rewriteBatchedStatements, dataSource.isRewriteBatchedStatements());
        assertEquals(cachePrepStmts, dataSource.isCachePrepStmts());
        assertEquals(cacheResultSetMetadata, dataSource.isCacheResultSetMetadata());
        assertEquals(cacheServerConfiguration, dataSource.isCacheServerConfiguration());
        assertEquals(elideSetAutoCommits, dataSource.isElideSetAutoCommits());
        assertEquals(maintainTimeStats, dataSource.isMaintainTimeStats());
    }
}
