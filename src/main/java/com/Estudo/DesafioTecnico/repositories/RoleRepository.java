package com.Estudo.DesafioTecnico.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Estudo.DesafioTecnico.model.RoleModel;
import com.Estudo.DesafioTecnico.model.enums.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

	Optional<RoleModel> findByRoleName(RoleName roleUser);

}
