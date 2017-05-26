package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.model.Customer;
import com.example.domain.repository.primary.PrimaryCustomerRepository;

@Service
@Transactional(transactionManager = "primaryTransactionManager")
public class PrimaryCustomerService {
  @Autowired
  @Qualifier("jdbcPrimary")
  private JdbcTemplate jdbcPrimary;

  @Autowired
  @Qualifier("primaryEntityManagerFactory")
  private EntityManager em;

  @Autowired
  private PrimaryCustomerRepository repo;

  public List<Customer> getCustomersByJdbcTemplate() {
    List<Customer> list = new ArrayList<>();

    jdbcPrimary.execute("INSERT INTO customer(first_name, last_name) VALUES ('yamada','jdbc')");

    list = jdbcPrimary.query("SELECT id, first_name, last_name FROM customer",
        (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"),
            rs.getString("last_name")));

    return list;
  }

  @SuppressWarnings("unchecked")
  public List<Customer> getCustomersByEntityManager() {
    List<Customer> list = new ArrayList<>();
    Customer customer = new Customer(null, "yamada", "entity");
    em.merge(customer);
    list = em.createQuery("FROM Customer").getResultList();
    return list;
  }

  public List<Customer> getCustomersByJpa() {
    List<Customer> list = new ArrayList<>();
    Customer customer = new Customer(null, "yamada", "jpa");
    repo.save(customer);
    list = repo.findAll();
    return list;
  }
}
