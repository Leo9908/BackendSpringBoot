package com.store.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dto.AddressDTO;
import com.store.dto.UserDTO;
import com.store.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService service;

	@CrossOrigin(origins = "https://localhost:9000")
	@PutMapping("/edit/{id}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO dto, @PathVariable(name = "id") Long id) {
		UserDTO userDTO = service.editUser(dto, id);
		return ResponseEntity.ok(userDTO);
	}

	@PostMapping("/edit/{id}/address")
	public ResponseEntity<AddressDTO> setAddress(@Valid @RequestBody AddressDTO addressDTO,
			@PathVariable(name = "id") Long id) {
		AddressDTO dto = service.setAddress(addressDTO, id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/{userId}/address")
	public List<AddressDTO> getAllAddress(@PathVariable(name = "userId") Long userId) {
		return service.getAllAddress(userId);
	}

	@PutMapping("/{userId}/address/{addressId}")
	public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO,
			@PathVariable(name = "addressId") Long addressId) {
		AddressDTO address = service.updateAddress(addressDTO, addressId);
		return ResponseEntity.ok(address);
	}

	@DeleteMapping("/{userId}/address/{addressId}")
	public ResponseEntity<Boolean> deleteAddress(@PathVariable(name = "addressId") Long addressId) {
		service.deleteAddress(addressId);
		return ResponseEntity.ok(true);
	}

}
