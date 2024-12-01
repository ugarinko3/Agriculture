package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

/**
 * DTO для Distruct
 */
@Data
public class DistrictDto {

    @Schema(name = "ID района")
    private UUID id;

    @Schema(name = "name", description = "Название района", example = "Тумановская площадь")
    private String name;

    @Schema(name = "code", description = "Код района", example = "302")
    private Integer code;
}
