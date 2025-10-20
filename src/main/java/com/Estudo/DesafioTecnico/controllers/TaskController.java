package com.Estudo.DesafioTecnico.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Estudo.DesafioTecnico.dtos.TaskRequestDto;
import com.Estudo.DesafioTecnico.dtos.TaskResponseDto;
import com.Estudo.DesafioTecnico.dtos.TaskUpdateDto;
import com.Estudo.DesafioTecnico.model.enums.EnumPriority;
import com.Estudo.DesafioTecnico.model.enums.EnumStatus;
import com.Estudo.DesafioTecnico.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TaskController {

	private final TaskService taskService;
	private Logger logger = LoggerFactory.getLogger(TaskController.class.getName());
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@PostMapping(value="/tasks", 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto taskModel) {
		logger.info("Received request to create task: {}", taskModel.title());
		TaskResponseDto createdTask = taskService.saveTask(taskModel);
		return ResponseEntity.ok(createdTask);
	}
	
	@GetMapping(value ="/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<TaskResponseDto>> getTasksFiltered(@RequestParam UUID projectId,
			@RequestParam(required=false) EnumStatus status,
			@RequestParam(required=false)EnumPriority priority,
			Pageable pageable) {
		Page<TaskResponseDto> tasksFiltered=taskService.getTasksByStatusPriorityProjectId(projectId, status, priority, pageable);
		
		return ResponseEntity.ok().body(tasksFiltered);
	}
	
	
	@PutMapping(value="/tasks/{taskId}/status", 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskResponseDto> updateTaskStatus(@PathVariable UUID taskId,
			@RequestBody @Valid TaskUpdateDto status) {
		logger.info("Received request to update task status with ID: {}", taskId);
		TaskResponseDto updatedTask = taskService.updateTaskStatus(taskId, status);
		return ResponseEntity.ok(updatedTask);
	}
	
	@DeleteMapping(value="/tasks/{taskId}")
	public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
		logger.info("Received request to delete task with ID: {}", taskId);
		taskService.deleteTask(taskId);
		return ResponseEntity.noContent().build();
	}
	
	
}
