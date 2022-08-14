package com.multi.datasource.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DbContextHolder.getDbType();
        if (typeKey.equals(DbContextHolder.WRITE)) {
            log.info("dataSource is use write");
            return typeKey;
        }
        log.info("dataSource is use read");
        return DbContextHolder.READ;
    }
}