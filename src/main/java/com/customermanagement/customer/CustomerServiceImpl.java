package com.customermanagement.customer;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customermanagement.exceptionHandling.CustomerNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService{

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private CustomerRepository custRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	enum Tier {
		SILVER,
		GOLD,
		PLATINUM;
	}
	
	public CustomerDTO saveCustomer(CustomerDTO cust) {
		Customer customer = new Customer();
		customer = this.modelMapper.map(cust, Customer.class);		
		customer=this.custRepository.save(customer);		
		CustomerDTO custSavedToDBDto = new CustomerDTO();
		custSavedToDBDto = this.modelMapper.map(customer, CustomerDTO.class);		
		Tier tier = calculateTier(custSavedToDBDto);
		logger.info("Inside saveCustomer method in CustomerServiceImpl tier:" + tier);
		custSavedToDBDto.setTier(tier);		
		return custSavedToDBDto;
	}
	
	
	public CustomerDTO getCustomerById(UUID Id) throws CustomerNotFoundException {
		logger.info("Inside getCustomerById method in CustomerServiceImpl Id:" +Id);		
		CustomerDTO customerDto =new CustomerDTO();
		try {
			Customer customer = custRepository.findById(Id).orElseThrow(EntityNotFoundException::new);
			if(customer != null) {
			customerDto = this.modelMapper.map(customer, CustomerDTO.class);
			Tier tier = calculateTier(customerDto);
			customerDto.setTier(tier);	
			}
			
			} catch (EntityNotFoundException ex) {
				
				throw new CustomerNotFoundException("Customer not found with id : " + Id);
			} 
		return customerDto;
	}
			
	
	@Override
	public CustomerDTO getCustomerByName(String name) throws CustomerNotFoundException {
		logger.info("Inside getCustomerByName method in CustomerServiceImpl name:" +name);		
		CustomerDTO customerDto =new CustomerDTO();
		try {		
			Customer customer = custRepository.findByNameIgnoreCase(name);
			if (customer != null) {
				customerDto = this.modelMapper.map(customer, CustomerDTO.class);
		        Tier tier = calculateTier(customerDto);
		        customerDto.setTier(tier);
				}
			} catch (EntityNotFoundException ex) {
				
			throw new CustomerNotFoundException("Customer not found with name : " + name);
		}
		return customerDto;
	}
	
	@Override
	public CustomerDTO getCustomerByEmail(String email) throws CustomerNotFoundException{
		logger.info("Inside getCustomerByEmail method in CustomerServiceImpl email:" +email);
		CustomerDTO customerDto =new CustomerDTO();
		try {		
			Customer customer = custRepository.findByEmailIgnoreCase(email);
			if (customer != null) {
				customerDto = this.modelMapper.map(customer, CustomerDTO.class);
				Tier tier = calculateTier(customerDto);
				customerDto.setTier(tier);
			}
			
			} catch (EntityNotFoundException ex) {
				
				throw new CustomerNotFoundException("Customer not found with email : " + email);
			}
			return customerDto;
	}
	
	
		
	@Override
	public String updateCustomerById(UUID Id, CustomerDTO cust)  {
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
		} catch (EntityNotFoundException ex) {
				return "Customer not found";
		}
		return "Customer details updated.";			
	}
	
	@Override
	public String deleteCustomerById(UUID Id) {
		logger.info("Inside deleteCustomerById method in CustomerServiceImpl Id:" +Id);	
		try {			
				this.custRepository.findById(Id).orElseThrow(EntityNotFoundException::new);			
		        this.custRepository.deleteById(Id);		
	    } catch (EntityNotFoundException ex) {
			
	    	return "Customer not found";
		}
		return "Customer deleted with Id:"+ Id;		
	}
	
	public Tier calculateTier(CustomerDTO customer) {
		
		LocalDateTime lastPurchaseDate=customer.getLastPurchaseDate();
		Double annualSpend=customer.getAnnualSpend();
		Tier tier = null;
		
		if(lastPurchaseDate != null && annualSpend != null ) {
		long monthsBetween = ChronoUnit.MONTHS.between(
				lastPurchaseDate, 
				LocalDateTime.now()
			    );	
		
		if(annualSpend != null && annualSpend < 1000) {
				tier=Tier.SILVER;
			}
		else if ((annualSpend != null && annualSpend >= 1000 && annualSpend < 10000) && monthsBetween <=12) {
			    tier=Tier.GOLD;
			 }
		else if ((annualSpend != null && annualSpend >= 10000) && monthsBetween <= 6) {
			    tier=Tier.PLATINUM;
		}
		
		}
			 
		return tier;
	}
}
