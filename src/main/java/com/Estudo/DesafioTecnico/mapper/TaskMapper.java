package com.Estudo.DesafioTecnico.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.Estudo.DesafioTecnico.dtos.TaskRequestDto;
import com.Estudo.DesafioTecnico.dtos.TaskResponseDto;
import com.Estudo.DesafioTecnico.dtos.TaskUpdateDto;
import com.Estudo.DesafioTecnico.model.TaskModel;

@Mapper(componentModel = "spring")
public interface TaskMapper {

	@Mapping(target = "project", ignore = true)
	TaskModel toModel(TaskRequestDto dto);
	@Mapping(target = "projectId", source = "project.projectId")
	TaskResponseDto toDto(TaskModel model);
	List<TaskResponseDto> toListTaskDto(List<TaskModel> dtoList);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateStatus(TaskUpdateDto dto,@MappingTarget TaskModel model);
}
