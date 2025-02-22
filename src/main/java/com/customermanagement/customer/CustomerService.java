package com.customermanagement.customer;

import java.util.UUID;

public interface CustomerService {
	
	Customer saveCustomer(Customer cust);
	
	CustomerDTO getCustomerById(UUID id);	
	
	CustomerDTO getCustomerByName(String name);

	CustomerDTO getCustomerByEmail(String email);

	String updateCustomerById(UUID id, CustomerDTO cust);

	String deleteCustomerById(UUID id);

}
