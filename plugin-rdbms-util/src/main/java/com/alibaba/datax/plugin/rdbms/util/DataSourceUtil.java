package com.alibaba.datax.plugin.rdbms.util;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shadowyy
 * @version 2020/6/20 17:26
 */
public final class DataSourceUtil {

    private static final Logger log = LoggerFactory.getLogger(DataSourceUtil.class);

    private static final Map<String, HikariDataSource> MAP = new ConcurrentHashMap<>();

    public static Connection getConnection(final String driverClassName,
                                           final String jdbcUrl, final String username, final String password) {
        if (StringUtils.isNotBlank(jdbcUrl)) {
            HikariDataSource dataSource = MAP.computeIfAbsent(jdbcUrl, x -> {
                //System.out.println("hikariDataSource创建了一次" + jdbcUrl);
                HikariDataSource hikariDataSource = new HikariDataSource();
                hikariDataSource.setPoolName(jdbcUrl);
                hikariDataSource.setDriverClassName(driverClassName);
                hikariDataSource.setJdbcUrl(jdbcUrl);
                hikariDataSource.setUsername(username);
                hikariDataSource.setPassword(password);
                hikariDataSource.setIdleTimeout(0);
                hikariDataSource.setMaximumPoolSize(50);
                //hikariDataSource.setMaxLifetime();
                //hikariDataSource.setMinimumIdle();
                return hikariDataSource;
            });
            try {
                return dataSource.getConnection();
            } catch (Exception e) {
                log.error("DataSourceUtil|getConnection|[dataBaseType, jdbcUrl, username, password]|:{0}", e);
            }
        }
        return null;
    }

    public static void close() {
        MAP.forEach((x,y)-> y.close());
    }

    private DataSourceUtil() {
    }
}
