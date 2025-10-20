package com.Estudo.DesafioTecnico.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.Estudo.DesafioTecnico.dtos.UserRequestDto;
import com.Estudo.DesafioTecnico.model.RoleModel;
import com.Estudo.DesafioTecnico.model.UserModel;
import com.Estudo.DesafioTecnico.model.enums.RoleName;
import com.Estudo.DesafioTecnico.repositories.RoleRepository;
import com.Estudo.DesafioTecnico.repositories.UserRepository;

import jakarta.transaction.Transactional;

public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
    public UserModel registerNewUser(UserRequestDto dto) {

        // 1. Validação: Checa se o usuário já existe
        if (userRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("Username '" + dto.username() + "' is already taken.");
        }

        // 2. Busca o Perfil Padrão (ROLE_USER)
        RoleModel userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseGet(() -> {
                    // Se o ROLE_USER não existir no DB, cria e salva ele (apenas na primeira vez)
                    RoleModel newRole = new RoleModel(null, RoleName.ROLE_USER, null);
                    return roleRepository.save(newRole);
                });
        
        // 3. Cria a Entidade
        UserModel newUser = UserModel.builder()
                .username(dto.username())
                // CRIPTOGRAFA A SENHA ANTES DE SALVAR
                .password(passwordEncoder.encode(dto.password()))
                // 4. Atribui o Perfil Padrão
                .roles(Set.of(userRole))
                .build();

        // 5. Salva no Banco de Dados
        return userRepository.save(newUser);
	}
}
