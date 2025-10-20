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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Estudo.DesafioTecnico.dtos.ProjectRequestDto;
import com.Estudo.DesafioTecnico.dtos.ProjectResponseDto;
import com.Estudo.DesafioTecnico.model.ProjectModel;
import com.Estudo.DesafioTecnico.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	private final ProjectService projectService;
	private Logger logger = LoggerFactory.getLogger(ProjectController.class.getName());
	
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	@PostMapping(value="/projects", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjectResponseDto> createProject(@RequestBody ProjectRequestDto projectModel) {
		logger.info("Received request to create project: {}", projectModel.name());
		ProjectResponseDto createdProject = projectService.saveProject(projectModel);
		return ResponseEntity.ok(createdProject);
	}
	
	@GetMapping(value ="/projects", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ProjectResponseDto>> getProjects(Pageable pageable) {
		logger.info("Received request to get projects pageable: {}", pageable);
		Page<ProjectResponseDto> projects = projectService.getProjectsPageable(pageable);
		return ResponseEntity.ok(projects);
	}
	
	@DeleteMapping(value="/{projectId}")
	public ResponseEntity<Void> deleteProject(@PathVariable UUID projectId) {
		logger.info("Received request to delete project with ID: {}", projectId);
		projectService.deleteProject(projectId);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
