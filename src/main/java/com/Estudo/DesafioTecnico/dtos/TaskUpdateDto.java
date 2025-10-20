package com.Estudo.DesafioTecnico.dtos;

import com.Estudo.DesafioTecnico.model.enums.EnumStatus;

import jakarta.validation.constraints.NotNull;

public record TaskUpdateDto(@NotNull EnumStatus status) {

}
