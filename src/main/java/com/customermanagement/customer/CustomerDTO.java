package com.customermanagement.customer;

import java.time.LocalDateTime;
import java.util.UUID;

import com.customermanagement.customer.CustomerServiceImpl.Tier;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class CustomerDTO {
	
	private UUID id;
	@NotNull(message = "Customer Name is mandatory")
	private String name;
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	@NotNull(message = "Customer email is mandatory")
	private String email;
	private Double annualSpend;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime  lastPurchaseDate;
	private Tier tier;
	
    public CustomerDTO() {
        super();
    }
    
	public CustomerDTO(String name, String email, Double annualSpend, LocalDateTime lastPurchaseDate, Tier tier) {
		super();
		this.name = name;
		this.email = email;
		this.annualSpend = annualSpend;
		this.lastPurchaseDate = lastPurchaseDate;
		this.tier = tier;
	}
	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the annualSpend
	 */
	public Double getAnnualSpend() {
		return annualSpend;
	}
	/**
	 * @param annualSpend the annualSpend to set
	 */
	public void setAnnualSpend(Double annualSpend) {
		this.annualSpend = annualSpend;
	}
	/**
	 * @return the lastPurchaseDate
	 */
	public LocalDateTime getLastPurchaseDate() {
		return lastPurchaseDate;
	}
	/**
	 * @param lastPurchaseDate the lastPurchaseDate to set
	 */
	public void setLastPurchaseDate(LocalDateTime lastPurchaseDate) {
		this.lastPurchaseDate = lastPurchaseDate;
	}
	/**
	 * @return the tier
	 */
	public Tier getTier() {
		return tier;
	}
	/**
	 * @param tier the tier to set
	 */
	public void setTier(Tier tier) {
		this.tier = tier;
	}
	
	

}
