package com.Estudo.DesafioTecnico.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Estudo.DesafioTecnico.exceptions.ResourceNotFoundException;
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
	private Logger logger = LoggerFactory.getLogger(TaskService.class.getName());

	public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
		this.taskRepository = taskRepository;
		this.projectRepository = projectRepository;
	}
	@Transactional
	public Page<TaskModel> getTasksByStatusPriorityProjectId(UUID projectId,EnumStatus status,EnumPriority priority, Pageable pageable) {
		logger.info("Fetching tasks by Status, Priority and Project ID: {}", projectId);
		ProjectModel project = projectRepository.findById(projectId).
				orElseThrow(() -> {
					logger.warn("Project not Found with Id: ", projectId);
					throw new ResourceNotFoundException("Project not found");
				});
		return taskRepository.findByProjectAndStatusAndPriority(project, status, priority, pageable);
	}
	@Transactional
	public Page<TaskModel> getTasksPageable(Pageable pageable) {
		logger.info("Fetching tasks pageable: {}", pageable);
		return taskRepository.findAll(pageable);
	}
	@Transactional
	public TaskModel getTaskById(UUID taskId) {
		logger.info("Fetching task by ID: {}", taskId);
		return taskRepository.findById(taskId).orElseThrow(() -> {
			logger.warn("Task not Found with Id: ", taskId);
			throw new ResourceNotFoundException("Task not found");
		});
	}
	@Transactional
	public TaskModel saveTask(TaskModel taskModel) {
		logger.info("Saving new task: {}", taskModel.getTitle());
		UUID projectId = taskModel.getProject().getProjectId();
		ProjectModel project = projectRepository.findById(projectId).
				orElseThrow(() -> {
					logger.warn("Project not Found with Id: ", projectId);
					throw new ResourceNotFoundException("Project not found");
				});
		logger.info("Associating task with Project ID: {}", projectId);
		taskModel.setProject(project);
		return taskRepository.save(taskModel);
		
	}
	@Transactional
	public TaskModel updateTaskStatus(UUID taskId, TaskModel taskModel) {

		TaskModel existingTask = getTaskById(taskId);

		existingTask.setStatus(taskModel.getStatus());

		logger.info("Updating task with ID: {}", taskId);
		return taskRepository.save(existingTask);
	}
	@Transactional
	public void deleteTask(UUID taskId) {
		getTaskById(taskId);
		logger.info("Deleting task with ID: {}", taskId);
		taskRepository.deleteById(taskId);
	}
}
