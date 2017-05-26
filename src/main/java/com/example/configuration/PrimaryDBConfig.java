package com.example.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableJpaRepositories(basePackages = "com.example.domain.repository.primary",
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager")
public class PrimaryDBConfig {

  @Value("${spring.datasource.primary.jpa.database-platform:}")
  private String databasePlatform;
  @Value("${spring.datasource.primary.jpa.show-sql:false}")
  private String showSQL;
  @Value("${spring.datasource.primary.jpa.hibernate.ddl-auto:}")
  private String ddlAuto;
  @Value("${spring.datasource.primary.jpa.component-scans:}")
  private String componentScans;

  @Bean(name = "dsPrimary")
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.primary")
  public DataSource primaryDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "jdbcPrimary")
  @Primary
  public JdbcTemplate primaryJdbcTemplate(@Qualifier("dsPrimary") DataSource ds) {
    return new JdbcTemplate(ds);
  }

  /**
   * エンティティマネージャー
   *
   * @return EntityManager
   */
  @Bean(name = "primaryEntityManagerFactory")
  @Primary
  @Autowired
  LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory() {

    LocalContainerEntityManagerFactoryBean factoryBean =
        new LocalContainerEntityManagerFactoryBean();
    //
    factoryBean.setDataSource(primaryDataSource());
    factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factoryBean.setJpaProperties(additionalProperties());
    // // Entityを検索するパッケージ
    factoryBean.setPackagesToScan(StringUtils.split(componentScans, ','));

    return factoryBean;
  }

  /**
   * トランザクションマネージャー
   *
   * @return TransactionManager
   */
  @Bean
  PlatformTransactionManager primaryTransactionManager() {
    return new JpaTransactionManager(primaryEntityManagerFactory().getObject());
  }

  private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("database-platform", databasePlatform);
    properties.setProperty("show-sql", showSQL);
    properties.setProperty("hibernate.ddl-auto", ddlAuto);
    properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
    return properties;
  }
}
