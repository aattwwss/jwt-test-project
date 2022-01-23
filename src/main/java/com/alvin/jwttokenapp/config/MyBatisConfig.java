package com.alvin.jwttokenapp.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.alvin.*")
public class MyBatisConfig {

    @Bean
    SqlSessionFactory sqlSessionFactory(ApplicationContext context, DataSource dataSource) throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setConfiguration(configuration);
//        sessionFactoryBean.setMapperLocations(context.getResource("classpath*:mapper/*.xml"));
        sessionFactoryBean.setDataSource(dataSource);

        return sessionFactoryBean.getObject();
    }
}
