package com.store.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductRatingDTO {

	private Long id;
	@NotEmpty(message = "The name must not be empty")
	private String nameUser;
	@NotEmpty(message = "The last name must not be empty")
	@Email
	private String emailUser;
	@NotNull
	private Double stars;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public Double getStars() {
		return stars;
	}

	public void setStars(Double stars) {
		this.stars = stars;
	}

	public ProductRatingDTO() {
		super();
	}

}
