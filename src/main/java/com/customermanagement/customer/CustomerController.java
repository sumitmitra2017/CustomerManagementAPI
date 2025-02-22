package com.customermanagement.customer;

import jakarta.validation.Valid;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping
	public Customer createCustomer(@Valid @RequestBody Customer cust) {		
		logger.info("Inside createCustomer method in CustomerController cust:" +cust);		
		return customerService.saveCustomer(cust);		
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID Id) {
		logger.info("Inside getCustomerById method in CustomerController Id:" +Id);
		try {
		CustomerDTO custDto = this.customerService.getCustomerById(Id);		
		return new ResponseEntity<CustomerDTO>(custDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
			
		}
	}	
	
	@GetMapping(params="name")
	public ResponseEntity<CustomerDTO> getCustomerByName(@RequestParam String name){
		logger.info("Inside getCustomerByName method in CustomerController RequestParam:" +name);
		try {
		CustomerDTO custDto = this.customerService.getCustomerByName(name);
		return new ResponseEntity<CustomerDTO>(custDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
			
		}
	}
	
	@GetMapping(params="email")
	public ResponseEntity<CustomerDTO> getCustomerByEmail(@RequestParam String email){
		logger.info("Inside getCustomerByEmail method in CustomerController RequestParam:" +email);
		try {
		CustomerDTO custDto = this.customerService.getCustomerByEmail(email);
		return new ResponseEntity<CustomerDTO>(custDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
			
		}
	}
	
	@PutMapping("/{Id}")
	public String updateCustomerById(@PathVariable UUID Id , @RequestBody CustomerDTO cust) {
		logger.info("Inside updateCustomerById method in CustomerController PathVariable:" +Id);
		return customerService.updateCustomerById(Id,cust);
		
	}
	
	@DeleteMapping("/{Id}")
	public String deleteCustomerById(@PathVariable UUID Id) {
		logger.info("Inside deleteCustomerById method in CustomerController PathVariable:" +Id);
		return customerService.deleteCustomerById(Id);
		
	}

}
