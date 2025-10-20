package com.Estudo.DesafioTecnico.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Estudo.DesafioTecnico.dtos.ProjectRequestDto;
import com.Estudo.DesafioTecnico.dtos.ProjectResponseDto;
import com.Estudo.DesafioTecnico.exceptions.ResourceNotFoundException;
import com.Estudo.DesafioTecnico.mapper.ProjectMapper;
import com.Estudo.DesafioTecnico.model.ProjectModel;
import com.Estudo.DesafioTecnico.model.UserModel;
import com.Estudo.DesafioTecnico.repositories.ProjectRepository;
import com.Estudo.DesafioTecnico.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ProjectService {
	private ProjectRepository projectRepository;
	private ProjectMapper mapper;
	private UserRepository userRepository;
	private Logger logger = LoggerFactory.getLogger(ProjectService.class.getName());

	public ProjectService(ProjectRepository projectRepository,ProjectMapper mapper, UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.mapper = mapper;
		this.userRepository = userRepository;
	}
	@Transactional
	public Page<ProjectResponseDto> getProjectsPageable(Pageable pageable) {
		logger.info("Fetching projects pageable: {}", pageable);
		Page<ProjectModel> projects =projectRepository.findAll(pageable);
		//transformar model em response dto
		Page<ProjectResponseDto> responseDtos= projects.map(mapper::toDto);
		//retornar response dto
		return responseDtos;
	}
	@Transactional
	public ProjectResponseDto getProjectById(UUID projectId) {
		logger.info("Fetching project by ID: {}", projectId);
		ProjectModel project =projectRepository.findById(projectId).orElseGet(() -> {
			logger.warn("Project not Found with Id: ", projectId);
			throw new ResourceNotFoundException("Project not found");
		});
		//transformar model em response dto
		ProjectResponseDto responseDto = mapper.toDto(project);
		//retornar response dto
		return responseDto;
	}
	@Transactional
	public ProjectResponseDto saveProject(ProjectRequestDto projectModel) {
		logger.info("Saving new project: {}", projectModel.name());
		UserModel currentUser= (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
		ProjectModel project = mapper.toModel(projectModel);
		project.getMembers().add(currentUser); // Adiciona o usuário atual como membro do projeto
		ProjectModel projectSaved =projectRepository.save(project);
		//transformar model em response dto
		return mapper.toDto(projectSaved);
	}
	@Transactional
	public ProjectResponseDto updateProject(UUID projectId, ProjectRequestDto projectModel) {
		
		
		//pegar o model existente
		ProjectModel existingProject = projectRepository.findById(projectId).orElseThrow(() -> {
			logger.warn("Project not Found with Id: ", projectId);
			throw new ResourceNotFoundException("Project not found");
		});
		//atualizar os campos
		mapper.updateModelFromDto(projectModel, existingProject);
		
		
		logger.info("Updating project with ID: {}", projectId);
		//salvar o model atualizado
		ProjectModel projectSaved=projectRepository.save(existingProject);
		return mapper.toDto(projectSaved);
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
	
	
	// Exemplo de como adicionar um membro a um projeto existente
	@Transactional
    public void addMemberToProject(UUID projectId, UUID userIdToAdd) {
        ProjectModel project = projectRepository.findById(projectId)
                                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        
        UserModel user = userRepository.findById(userIdToAdd)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                                
        // Adiciona o novo membro ao Set e salva
        project.getMembers().add(user);
        projectRepository.save(project);
    }
}
