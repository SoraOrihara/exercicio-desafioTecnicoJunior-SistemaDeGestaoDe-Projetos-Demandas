package com.Estudo.DesafioTecnico.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Estudo.DesafioTecnico.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel,UUID>{
	Optional<UserModel> findByUsername(String username);
	boolean existsByUsername(String username);
}


