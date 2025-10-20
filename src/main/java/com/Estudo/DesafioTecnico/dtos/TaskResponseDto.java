package com.Estudo.DesafioTecnico.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.Estudo.DesafioTecnico.model.enums.EnumPriority;
import com.Estudo.DesafioTecnico.model.enums.EnumStatus;

public record TaskResponseDto(UUID taskId, String title, String description, EnumStatus status, EnumPriority priority, LocalDate dueDate, UUID projectId) {

}
