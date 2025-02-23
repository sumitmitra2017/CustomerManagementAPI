package com.customermanagement.customer;

import java.util.UUID;

import com.customermanagement.exceptionHandling.CustomerNotFoundException;

public interface CustomerService {
	
	CustomerDTO saveCustomer(CustomerDTO cust);
	
	CustomerDTO getCustomerById(UUID id) throws CustomerNotFoundException;	
	
	CustomerDTO getCustomerByName(String name) throws CustomerNotFoundException;

	CustomerDTO getCustomerByEmail(String email) throws CustomerNotFoundException;

	String updateCustomerById(UUID id, CustomerDTO cust);

	String deleteCustomerById(UUID id);

}
