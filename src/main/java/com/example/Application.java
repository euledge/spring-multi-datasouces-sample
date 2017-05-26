package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.service.PrimaryCustomerService;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
@EnableTransactionManagement
public class Application implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(Application.class);
  @Autowired
  PrimaryCustomerService service;

  public static void main(String args[]) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {}
}
