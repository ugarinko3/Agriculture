package org.example.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


/**
 * Реквест для District
 */
@Setter
@Getter
@Component
public class CreateDistrictRequest {
    @Schema(name = "name", description = "Название района", example = "Речной вокзал")
    private String name;

    @Schema(name = "code", description = "Код района", example = "301")
    private Integer code;
}
