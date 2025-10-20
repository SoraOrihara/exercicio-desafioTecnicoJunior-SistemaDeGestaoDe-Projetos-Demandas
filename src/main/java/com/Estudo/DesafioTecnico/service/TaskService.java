package com.Estudo.DesafioTecnico.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Estudo.DesafioTecnico.dtos.TaskRequestDto;
import com.Estudo.DesafioTecnico.dtos.TaskResponseDto;
import com.Estudo.DesafioTecnico.dtos.TaskUpdateDto;
import com.Estudo.DesafioTecnico.exceptions.ResourceNotFoundException;
import com.Estudo.DesafioTecnico.mapper.TaskMapper;
import com.Estudo.DesafioTecnico.model.ProjectModel;
import com.Estudo.DesafioTecnico.model.TaskModel;
import com.Estudo.DesafioTecnico.model.enums.EnumPriority;
import com.Estudo.DesafioTecnico.model.enums.EnumStatus;
import com.Estudo.DesafioTecnico.repositories.ProjectRepository;
import com.Estudo.DesafioTecnico.repositories.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

	private TaskRepository taskRepository;
	private ProjectRepository projectRepository;
	private TaskMapper taskMapper;
	private Logger logger = LoggerFactory.getLogger(TaskService.class.getName());

	public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper) {
		this.taskRepository = taskRepository;
		this.projectRepository = projectRepository;
		this.taskMapper = taskMapper;
	}
	@Transactional
	public Page<TaskResponseDto> getTasksByStatusPriorityProjectId(UUID projectId,EnumStatus status,EnumPriority priority, Pageable pageable) {
		logger.info("Fetching tasks by Status, Priority and Project ID: {}", projectId);
		//transformar projectId em projectModel
		ProjectModel project = projectRepository.findById(projectId).
				orElseThrow(() -> {
					logger.warn("Project not Found with Id: ", projectId);
					throw new ResourceNotFoundException("Project not found");
				});
		//listar tasks filtradas
		Page<TaskModel> tasks =taskRepository.findByProjectAndStatusAndPriority(project, status, priority, pageable);
		//transformar model em response dto
		Page<TaskResponseDto> responseDtos = tasks.map(taskMapper::toDto);
		//retornar response dto
		return responseDtos;
	}
	@Transactional
	public Page<TaskResponseDto> getTasksPageable(Pageable pageable) {
		logger.info("Fetching tasks pageable: {}", pageable);
		//listar tasks 
		Page<TaskModel> tasks =taskRepository.findAll(pageable);
		//transformar model em response dto
		Page<TaskResponseDto> responseDtos = tasks.map(taskMapper::toDto);
		return responseDtos;
	}
	@Transactional
	public TaskResponseDto getTaskById(UUID taskId) {
		logger.info("Fetching task by ID: {}", taskId);
		TaskModel task= taskRepository.findById(taskId).orElseThrow(() -> {
			logger.warn("Task not Found with Id: ", taskId);
			throw new ResourceNotFoundException("Task not found");
		});
		return taskMapper.toDto(task);
	}
	@Transactional
	public TaskResponseDto saveTask(TaskRequestDto dto) {
		logger.info("Saving new task: {}", dto.title());
		ProjectModel project = projectRepository.findById(dto.projectId()).
				orElseThrow(() -> {
					logger.warn("Project not Found with Id: ", dto.projectId());
					throw new ResourceNotFoundException("Project not found");
				});
		logger.info("Associating task with Project ID: {}", dto.projectId());
		TaskModel taskModel = taskMapper.toModel(dto);
		taskModel.setProject(project);
		TaskModel taskSalva =taskRepository.save(taskModel);
		//transformar model em response dto
		return taskMapper.toDto(taskSalva);
	}
	@Transactional
	public TaskResponseDto updateTaskStatus(UUID taskId, TaskUpdateDto dto) {

		TaskModel existingTask = taskRepository.findById(taskId).orElseThrow(() -> {
			logger.warn("Task not Found with Id: ", taskId);
			throw new ResourceNotFoundException("Task not found");
		});
		taskMapper.updateStatus(dto, existingTask);

		logger.info("Updating task with ID: {}", taskId);
		TaskModel taskSalva= taskRepository.save(existingTask);
		return taskMapper.toDto(taskSalva);
	}
	@Transactional
	public void deleteTask(UUID taskId) {
		getTaskById(taskId);
		logger.info("Deleting task with ID: {}", taskId);
		taskRepository.deleteById(taskId);
	}
}
