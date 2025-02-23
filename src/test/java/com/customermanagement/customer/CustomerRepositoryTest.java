package com.customermanagement.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.customermanagement.customer.CustomerServiceImpl.Tier;

import jakarta.validation.*;




@DataJpaTest
public class CustomerRepositoryTest {
	
	private static ValidatorFactory validatorFactory;
	private static Validator validator;	
	
	@Autowired
	private CustomerRepository customerRepository;	

    private ModelMapper modelMapper = new ModelMapper() ;
	
	CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl() ;
	
	private Customer testCustomer;
	
	@BeforeEach
	public void setUp() {
	    // Initialize test data before each test method
		testCustomer = new Customer();
		testCustomer.setName("customer");
		testCustomer.setEmail("customer@domain.extn");
		customerRepository.save(testCustomer);
		
		validatorFactory=Validation.buildDefaultValidatorFactory();
		validator=validatorFactory.getValidator();
		
	}

	@AfterEach
	public void tearDown() {
	    // Release test data after each test method
		customerRepository.deleteById(testCustomer.getId());
	}
	
	// Test for Create : Save customer operation
	@Test
	public void givenCustomer_whenSaved_thenCanBeFoundById() {
	    Customer savedCustomer = customerRepository.findById(testCustomer.getId()).orElse(null);
	    assertNotNull(savedCustomer);
	    assertEquals(testCustomer.getName(), savedCustomer.getName());
	    assertEquals(testCustomer.getEmail(), savedCustomer.getEmail());
	}
	
	// Test for Retrieve : Find customer by name operation
	
	@Test
	public void givenCustomer_whenFindByCustomerNameCalled_thenCustomerIsFound() {
	    Customer foundCustomer = customerRepository.findByNameIgnoreCase("customer");
	    assertNotNull(foundCustomer);
	    assertEquals("customer", foundCustomer.getName());
	}
	
	// Test for Retrieve : Find customer by Email operation
	
		@Test
	public void givenCustomer_whenFindByCustomerEmailCalled_thenCustomerIsFound() {
		 Customer foundCustomer = customerRepository.findByEmailIgnoreCase("customer@domain.extn");
		 assertNotNull(foundCustomer);
		 assertEquals("customer@domain.extn", foundCustomer.getEmail());
	}
	
	
	// Test for Update : update customer operation
	@Test
	public void givenCustomer_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		testCustomer.setName("updatedCustomerName");
		testCustomer.setEmail("updatedEmail@domain.extn");
		testCustomer.setAnnualSpend(12000.00);
		testCustomer.setLastPurchaseDate(LocalDateTime.parse("2025-01-20T18:30:00",formatter));
		customerRepository.save(testCustomer);

		Customer updatedCustomer = customerRepository.findById(testCustomer.getId()).orElse(null);

	    assertNotNull(updatedCustomer);
	    assertEquals("updatedCustomerName", updatedCustomer.getName());
	    assertEquals("updatedEmail@domain.extn", updatedCustomer.getEmail());
	    assertEquals(12000.00, updatedCustomer.getAnnualSpend());
	    assertEquals(LocalDateTime.parse("2025-01-20T18:30:00",formatter), updatedCustomer.getLastPurchaseDate());
	}
	
	// Test for Delete : delete customer operation
	 @Test
	    public void testDeleteCustomer() {
		 Customer customer = customerRepository.save(new Customer("John Doe", "John@Doe.com",null,null));
		 customerRepository.deleteById(customer.getId());
		 Customer deletedCustomer = customerRepository.findById(customer.getId()).orElse(null);
	     assertNull(deletedCustomer);
	    }
	
	 // Test for Silver Tier calculation based on customer's annual spend
	 
	 @Test
	    public void testCalculationForSilverTier() {
		 DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		 Customer customer = customerRepository.save(new Customer("John Doe", "John@Doe.com",100.00,LocalDateTime.parse("2024-05-20T12:37:00",formatter)));
		 Customer savedCustomer = this.customerRepository.findById(customer.getId()).orElse(null);
		 assertNotNull(savedCustomer);	
		 CustomerDTO customerDtoSaved = this.modelMapper.map(customer, CustomerDTO.class);
		 Tier tier = customerServiceImpl.calculateTier(customerDtoSaved);
		 assertEquals(Tier.SILVER, tier);
		 
	 }
	 
	// Test for Gold Tier calculation based on customer's annual spend and lastPurchaseDate
	 
	 @Test
		public void testCalculationForGoldTier() {		 
	    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	    Customer customer = customerRepository.save(new Customer("John Doe", "John@Doe.com",5000.00,LocalDateTime.parse("2024-09-20T18:30:00",formatter)));
		Customer savedCustomer = customerRepository.findById(customer.getId()).orElse(null);
		assertNotNull(savedCustomer);
		CustomerDTO customerDtoSaved = this.modelMapper.map(customer, CustomerDTO.class);
		Tier tier = customerServiceImpl.calculateTier(customerDtoSaved);
		assertEquals(Tier.GOLD, tier);
			 
	}
		 
		// Test for Platinum Tier calculation based on customer's annual spend and lastPurchaseDate
		 
	 @Test
		public void testCalculationForPlatinumTier() {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		Customer customer = customerRepository.save(new Customer("John Doe", "John@Doe.com",15000.00,LocalDateTime.parse("2025-02-11T14:20:00",formatter)));
		Customer savedCustomer = customerRepository.findById(customer.getId()).orElse(null);
		assertNotNull(savedCustomer);
		CustomerDTO customerDtoSaved = this.modelMapper.map(customer, CustomerDTO.class);
		Tier tier = customerServiceImpl.calculateTier(customerDtoSaved);
		assertEquals(Tier.PLATINUM, tier);
				 
	}
	 
	 // Test for incorrect Email validation
	 @Test()
	 public void validationIsInvokedBeforeSavingContact() {
		 DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		 Customer customer = new Customer("John Doe", "JohnDoe.com",15000.00,LocalDateTime.parse("2025-02-11T14:20:00",formatter));
		 customer.setEmail("Jackyahoo.com");
		 customer.setName("Jack");
		 Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
	        assertFalse(violations.isEmpty());
	 }
	 
	 
}
