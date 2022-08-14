package com.multi.datasource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: xiaodian-order
 * @author: zxliuyu@didiglobal.com
 * @create: 2021-01-14 14:56
 **/
@ConfigurationProperties(prefix = "spring.datasource.write")
@Data
public class DataSourceWriteProperties {

    private String url;
    private String username;
    private String password;
}
