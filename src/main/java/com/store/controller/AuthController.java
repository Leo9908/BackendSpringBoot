package com.store.controller;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.dto.LoginDTO;
import com.store.dto.UserDTO;
import com.store.entitys.Rol;
import com.store.entitys.User;
import com.store.repository.RolRepository;
import com.store.repository.UsersRepository;
import com.store.security.JWTAuthResonseDTO;
import com.store.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private UsersRepository repository;

	@Autowired
	private RolRepository repository2;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
		Authentication authentication = manager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Obtener el token del jwtTokenProvider
		String token = jwtTokenProvider.buildToken(authentication);
		return ResponseEntity.ok(new JWTAuthResonseDTO(token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> userRegistered(@RequestBody UserDTO userDTO) {
		if (repository.existsByUser(userDTO.getUser())) {
			return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
		}
		if (repository.existsByUser(userDTO.getEmail())) {
			return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
		}
		User user = mapper.map(userDTO, User.class);
		user.setPass(encoder.encode(userDTO.getPass()));

		Rol rol = repository2.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(rol));
		repository.save(user);

		return new ResponseEntity<>("Successfully registered user", HttpStatus.OK);
	}

}
