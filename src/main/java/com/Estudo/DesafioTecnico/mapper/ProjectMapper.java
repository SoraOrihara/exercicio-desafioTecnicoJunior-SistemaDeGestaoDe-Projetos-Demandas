package com.Estudo.DesafioTecnico.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.Estudo.DesafioTecnico.dtos.ProjectRequestDto;
import com.Estudo.DesafioTecnico.dtos.ProjectResponseDto;
import com.Estudo.DesafioTecnico.model.ProjectModel;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface ProjectMapper {

	// Ignora o ID (projectId) na Entidade, pois será gerado pelo JPA
    @Mapping(target = "projectId", ignore = true)
	@Mapping(target = "tasks", ignore = true)
	ProjectModel toModel(ProjectRequestDto dto);
	ProjectResponseDto toDto(ProjectModel model);
	List<ProjectResponseDto> toListProjectDto(List<ProjectModel> dtoList);
	// Configuração para ignorar campos nulos no DTO, preservando os valores antigos.
    // Ignora a lista de Tasks e o ID.
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "projectId", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    void updateModelFromDto(ProjectRequestDto dto, @MappingTarget ProjectModel projectModel);
}
