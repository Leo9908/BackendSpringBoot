package com.store.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {

	private Long id;
	@NotEmpty(message = "The name must not be empty")
	@Size(min = 2, message = "Name must contain more than two characters")
	private String name;
	@NotEmpty(message = "The last name must not be empty")
	@Size(min = 5, message = "Last name must contain more than five characters")
	private String last_name;
	@NotEmpty(message = "The username must not be empty")
	@Size(min = 2, message = "User name must contain more than two characters")
	private String user;
	@NotEmpty(message = "The last name must not be empty")
	@Email
	private String email;
	@NotEmpty(message = "The password must not be empty")
	@Size(min = 8, message = "Password must contain more than eight characters")
	private String pass;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public UserDTO() {
		super();
	}

}
