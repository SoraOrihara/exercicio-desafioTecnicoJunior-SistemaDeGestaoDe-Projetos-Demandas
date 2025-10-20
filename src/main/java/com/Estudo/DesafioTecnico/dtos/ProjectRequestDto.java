package com.Estudo.DesafioTecnico.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record ProjectRequestDto(@NotNull String name, String description, @NotNull LocalDate startDate, LocalDate endDate) {

}
