package org.example.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Реквест для District
 */
@Data
public class CreateUpdateDistrictRequest {
    @Schema(name = "name", description = "Название района", example = "Речной вокзал")
    private String name;

    @Schema(name = "code", description = "Код района", example = "301")
    private Integer code;
}
