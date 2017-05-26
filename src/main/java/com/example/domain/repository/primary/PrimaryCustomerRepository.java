package com.example.domain.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.model.Customer;

@Repository
public interface PrimaryCustomerRepository extends JpaRepository<Customer, Long> {

}
