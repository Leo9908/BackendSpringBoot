package com.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.entitys.User;

//DTO (Data Transfer Object) y es un patrón de diseño diferente a DAO (Data Access Object)
//DTO permite un acceso mas seguro de los datos, por eso lo estoy usando para la entidad User
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);

	public Optional<User> findByUserOrEmail(String username, String emial);

	public Optional<User> findByUser(String username);

	public Boolean existsByUser(String username);

	public Boolean existsByEmail(String email);

	public Optional<User> findByToken(String token);
}
