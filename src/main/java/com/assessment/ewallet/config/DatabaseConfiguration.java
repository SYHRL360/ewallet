package com.assessment.ewallet.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {



    @Bean
    public static DataSource source(){
        DataSourceBuilder<?> dsb = DataSourceBuilder.create();
        dsb.driverClassName("com.mysql.cj.jdbc.Driver");
        dsb.url("jdbc:mysql://localhost:3306/e_wallet?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false");
        dsb.username("root");
        dsb.password("55555");
        return dsb.build();
    }
}
