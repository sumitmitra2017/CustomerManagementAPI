package com.customermanagement.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDTO {
	
	private UUID id;
	private String name;
	private String email;
	private Double annualSpend;
	private LocalDateTime  lastPurchaseDate;
	private String tier;
	
	// NoArgsConstructor
    public CustomerDTO() {
        super();
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
	public String getTier() {
		return tier;
	}
	/**
	 * @param tier the tier to set
	 */
	public void setTier(String tier) {
		this.tier = tier;
	}
	
	

}
