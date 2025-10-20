package com.Estudo.DesafioTecnico.dtos;

import java.time.LocalDate;
import java.util.UUID;

import com.Estudo.DesafioTecnico.model.enums.EnumPriority;
import com.Estudo.DesafioTecnico.model.enums.EnumStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequestDto(@NotBlank String title, String description,@NotNull EnumStatus status,@NotNull EnumPriority priority,@FutureOrPresent LocalDate dueDate,@NotNull UUID projectId) {

}
