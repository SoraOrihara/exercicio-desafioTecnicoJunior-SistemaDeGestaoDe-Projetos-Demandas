package com.Estudo.DesafioTecnico.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Estudo.DesafioTecnico.model.TaskModel;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {

}
