package com.example.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.model.Customer;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {"spring.datasource.primary.url:jdbc:h2:mem:/primary",
    "spring.datasource.primary.jpa.hibernate.ddl-auto:create-drop"})
@Transactional(transactionManager = "primaryTransactionManager")
public class PrimaryCustomerServiceTest {
  @Autowired
  PrimaryCustomerService service;

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testGetCustomersByEntityManager() {
    List<Customer> list = service.getCustomersByEntityManager();
    assertThat(list.size(), is(1));
  }

  @Test
  public void testGetCustomersByJpa() {
    List<Customer> list = service.getCustomersByJpa();
    assertThat(list.size(), is(1));
  }


  @Test
  public void testGetCustomersByJdbcTemplate() {
    List<Customer> list = service.getCustomersByJdbcTemplate();
    assertThat(list.size(), is(1));
  }
}
