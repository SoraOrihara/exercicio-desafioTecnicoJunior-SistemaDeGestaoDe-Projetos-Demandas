package com.Estudo.DesafioTecnico.repositories;


import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Estudo.DesafioTecnico.model.ProjectModel;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectModel, UUID> {

	Page<ProjectModel> findAll(Pageable pageable);
}
