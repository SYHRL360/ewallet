package com.assessment.ewallet.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.driver-class-name}")
    private static String databaseDriver;
    @Value("${spring.datasource.url}")
    private static String databaseUrl;
    @Value("${spring.datasource.username}")
    private static String databaseUsername;
    @Value("${spring.datasource.password}")
    private static String databasePassword;

    @Bean
    public static DataSource source(){
        DataSourceBuilder<?> dsb = DataSourceBuilder.create();
        dsb.driverClassName(databaseDriver);
        dsb.url(databaseUrl);
        dsb.username(databaseUsername);
        dsb.password(databasePassword);
        return dsb.build();
    }
}
