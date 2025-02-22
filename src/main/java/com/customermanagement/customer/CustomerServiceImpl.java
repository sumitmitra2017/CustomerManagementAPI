package com.customermanagement.customer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService{

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerRepository custRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Override
	public Customer saveCustomer(Customer cust) {
		logger.info("Inside saveCustomer method in CustomerServiceImpl cust:" +cust);
		Customer custSavedToDB=this.custRepository.save(cust);
		return custSavedToDB;
	}
	
	@Override
	public CustomerDTO getCustomerById(UUID Id) {
		logger.info("Inside getCustomerById method in CustomerServiceImpl Id:" +Id);		
		CustomerDTO customerDto =new CustomerDTO();
		String tier="";		
			Customer customer = custRepository.findById(Id).orElseThrow(EntityNotFoundException::new);
			tier = calculateTier(customer);
			customerDto = this.modelMapper.map(customer, CustomerDTO.class);
			customerDto.setTier(tier);			
			return customerDto;
	}
			
	
	@Override
	public CustomerDTO getCustomerByName(String name){
		logger.info("Inside getCustomerByName method in CustomerServiceImpl name:" +name);		
		CustomerDTO customerDto =new CustomerDTO();
		String tier="";
		try {		
		Customer customer = custRepository.findByNameIgnoreCase(name);
		tier = calculateTier(customer);
		customerDto = this.modelMapper.map(customer, CustomerDTO.class);
		customerDto.setTier(tier);
		
		} catch (EntityNotFoundException ex) 
			{
			return customerDto;
			}
		return customerDto;
	}
	
	@Override
	public CustomerDTO getCustomerByEmail(String email){
		logger.info("Inside getCustomerByEmail method in CustomerServiceImpl email:" +email);
		CustomerDTO customerDto =new CustomerDTO();
		String tier="";
		try {		
			Customer customer = custRepository.findByEmailIgnoreCase(email);
			tier = calculateTier(customer);
			customerDto = this.modelMapper.map(customer, CustomerDTO.class);
			customerDto.setTier(tier);
			
			} catch (EntityNotFoundException ex) 
				{
				return customerDto;
				}
			return customerDto;
	}
	
	public String calculateTier(Customer cust) {
		String tier="";
		LocalDateTime lastPurchaseDate=cust.getLastPurchaseDate();
		long monthsBetween = ChronoUnit.MONTHS.between(
				lastPurchaseDate, 
				LocalDateTime.now()
			    );		
		Double annualSpend=cust.getAnnualSpend();
		if(annualSpend != null && annualSpend<1000)
		     tier="Silver";
		else if ((annualSpend != null && annualSpend>=1000 && annualSpend<10000) && monthsBetween <=12)
			 tier="Gold";
		else if ((annualSpend != null && annualSpend>=10000) && monthsBetween <= 6)
			 tier="Platinum";
		
		return tier;
	}
		
	@Override
	public String updateCustomerById(UUID Id, CustomerDTO cust) {
		logger.info("Inside updateCustomerById method in CustomerServiceImpl Id:" +Id);
		try {
		Customer customer = custRepository.findById(Id).orElseThrow(EntityNotFoundException::new);
		if(customer != null) {
			customer.setName(cust.getName());
			customer.setEmail(cust.getEmail());
			customer.setAnnualSpend(cust.getAnnualSpend());
			customer.setLastPurchaseDate(cust.getLastPurchaseDate());
			custRepository.save(customer);			
			} 
		} catch (EntityNotFoundException ex) 
			{
				return "Customer not found";
			}
		return "Customer details updated.";			
	}
	
	@Override
	public String deleteCustomerById(UUID Id) {
		logger.info("Inside deleteCustomerById method in CustomerServiceImpl Id:" +Id);	
		try {			
				Customer customer = this.custRepository.findById(Id).orElseThrow(EntityNotFoundException::new);			
		        this.custRepository.deleteById(Id);		
	    } catch (EntityNotFoundException ex) 
		{
			return "Customer not found";
		}
		return "Customer deleted with Id:"+ Id;		
	}
}
