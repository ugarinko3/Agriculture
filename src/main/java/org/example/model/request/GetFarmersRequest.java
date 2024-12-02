package org.example.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Фильтры для поиска
 */
@Setter
@Getter
public class GetFarmersRequest {
    @Schema(name = "name", description = "Название организации")
    private String name;

    @Schema(name = "inn", description = "ИНН")
    private Long inn;

    @Schema(name = "registrationDistrictId", description = "Район регистрации")
    private UUID registrationDistrictId;

    @Schema(name = "registrationDate", description = "Дата регистрации")
    private LocalDate registrationDate;

    @Schema(name = "archiveStatus", description = "Статус архивности")
    private boolean archiveStatus;

}
