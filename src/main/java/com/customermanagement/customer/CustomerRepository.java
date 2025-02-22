package com.customermanagement.customer;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CustomerRepository extends JpaRepository<Customer, UUID>{
	
    Customer findByNameIgnoreCase(String input);

	Customer findByEmailIgnoreCase(String email);

}
