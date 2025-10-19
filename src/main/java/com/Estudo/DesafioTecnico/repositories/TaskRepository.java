package com.Estudo.DesafioTecnico.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Estudo.DesafioTecnico.model.ProjectModel;
import com.Estudo.DesafioTecnico.model.TaskModel;
import com.Estudo.DesafioTecnico.model.enums.EnumPriority;
import com.Estudo.DesafioTecnico.model.enums.EnumStatus;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

	Page<TaskModel> findAll(Pageable pageable);
	Page<TaskModel> findByProjectAndStatusAndPriority(ProjectModel projectId,EnumStatus status,EnumPriority priority,Pageable pageable);
}
