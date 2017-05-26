package com.example.domain.repository.secondary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.model.Customer;

@Repository
public interface SecondaryCustomerRepository extends JpaRepository<Customer, Long> {

}
