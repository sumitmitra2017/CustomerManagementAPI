package com.customermanagement.customer;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@NotNull(message = "Customer Name is required")
	private String name;
	
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	@NotNull(message = "This field is required")
    private String email;
	
    private Double annualSpend;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime  lastPurchaseDate;
  
    public Customer() {
        super();
    }   

	public Customer(@NotNull(message = "This field is required") String name,
			@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") @NotNull(message = "This field is required") String email,
			Double annualSpend, LocalDateTime lastPurchaseDate) {
		super();
		this.name = name;
		this.email = email;
		this.annualSpend = annualSpend;
		this.lastPurchaseDate = lastPurchaseDate;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Double getAnnualSpend() {
		return annualSpend;
	}	

	public void setAnnualSpend(Double annualSpend) {
		this.annualSpend = annualSpend;
	}

	
	public LocalDateTime getLastPurchaseDate() {
		return lastPurchaseDate;
	}

	public void setLastPurchaseDate(LocalDateTime lastPurchaseDate) {
		this.lastPurchaseDate = lastPurchaseDate;
	}	
    
}
