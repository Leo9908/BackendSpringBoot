package com.store.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dto.UserDTO;
import com.store.entitys.User;
import com.store.repository.UsersRepository;
import com.store.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UsersRepository repo;

	@Override
	public UserDTO createUser(UserDTO userDTO) {

		// Convertimos de DTO a entidad
		User user = mapDTO(userDTO);

		User newUser = repo.save(user);

		// Convertimos de entidad a DTO
		UserDTO userResponse = mapEntity(newUser);

		return userResponse;
	}

	private User mapDTO(UserDTO usersDTO) {
		User user = mapper.map(usersDTO, User.class);
		return user;
	}

	private UserDTO mapEntity(User user) {
		UserDTO usersDTO = mapper.map(user, UserDTO.class);
		return usersDTO;
	}

}
