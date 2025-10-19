package com.Estudo.DesafioTecnico.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Estudo.DesafioTecnico.exceptions.ResourceNotFoundException;
import com.Estudo.DesafioTecnico.model.ProjectModel;
import com.Estudo.DesafioTecnico.repositories.ProjectRepository;

import jakarta.transaction.Transactional;

@Service
public class ProjectService {
	private ProjectRepository projectRepository;
	private Logger logger = LoggerFactory.getLogger(ProjectService.class.getName());

	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}
	@Transactional
	public Page<ProjectModel> getProjectsPageable(Pageable pageable) {
		logger.info("Fetching projects pageable: {}", pageable);
		return projectRepository.findAll(pageable);
	}
	@Transactional
	public ProjectModel getProjectById(UUID projectId) {
		logger.info("Fetching project by ID: {}", projectId);
		return projectRepository.findById(projectId).orElseGet(() -> {
			logger.warn("Project not Found with Id: ", projectId);
			throw new ResourceNotFoundException("Project not found");
		});
	}
	@Transactional
	public ProjectModel saveProject(ProjectModel projectModel) {
		logger.info("Saving new project: {}", projectModel.getName());
		return projectRepository.save(projectModel);
	}
	@Transactional
	public ProjectModel updateProject(UUID projectId, ProjectModel projectModel) {
		if (projectRepository.existsById(projectId)==false) {
			logger.warn("Project not Found with Id: ", projectId);
			throw new ResourceNotFoundException("Project not found");
		}
		
		ProjectModel existingProject = getProjectById(projectId);
		existingProject.setName(projectModel.getName());
		existingProject.setDescription(projectModel.getDescription());
		existingProject.setStartDate(projectModel.getStartDate());
		existingProject.setEndDate(projectModel.getEndDate());
		logger.info("Updating project with ID: {}", projectId);
		return projectRepository.save(existingProject);
	}
	@Transactional
	public void deleteProject(UUID projectId) {
		if (projectRepository.existsById(projectId) == false) {
			logger.warn("Project not Found with Id: ", projectId);
			throw new ResourceNotFoundException("Project not found");

		}
		
		projectRepository.deleteById(projectId);
		logger.info("Deleting project with ID: {}", projectId);
	}
}
