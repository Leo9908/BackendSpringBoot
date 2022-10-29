package com.store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dto.UserDTO;
import com.store.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		return new ResponseEntity<>(service.createUser(userDTO), HttpStatus.CREATED);
	}

}
