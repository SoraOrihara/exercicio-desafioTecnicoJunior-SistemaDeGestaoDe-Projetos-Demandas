package com.Estudo.DesafioTecnico.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProjectResponseDto(UUID projectId, String name, String description, LocalDate startDate, LocalDate endDate) {

}
