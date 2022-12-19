package com.store.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginDTO {

	@NotEmpty
	private String usernameOrEmail;
	@Size(min = 8, message = "Password must contain more than eight characters")
	private String password;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
