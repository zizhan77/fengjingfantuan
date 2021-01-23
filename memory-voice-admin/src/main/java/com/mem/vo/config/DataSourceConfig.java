package com.mem.vo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


@Configuration
@MapperScan(basePackages = "com.mem.vo.business.base.dao", sqlSessionTemplateRef = "memvoSqlSessionTemplate")
@PropertySources({
    @PropertySource(value = {
            "classpath:/application.yml"}, encoding = "utf-8")
})
public class DataSourceConfig {

    @Bean(name = "memvoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource memvoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "memvoSqlSessionFactory")
    @Primary
    public SqlSessionFactory memvoSqlSessionFactory(@Qualifier("memvoDataSource") DataSource dataSource)
        throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        bean.setDataSource(dataSource);
        bean.setMapperLocations(
            new PathMatchingResourcePatternResolver()
                    .getResources("classpath:mybatis/mappers/*.xml"));
        bean.setConfigLocation(resolver
                .getResource("classpath:/mybatis/mybatis-config.xml"));

        return bean.getObject();
    }

    @Bean(name = "memvoTransactionManager")
    @Primary
    public DataSourceTransactionManager memvoTransactionManager(@Qualifier("memvoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "memvoSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate memvoSqlSessionTemplate(
        @Qualifier("memvoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
