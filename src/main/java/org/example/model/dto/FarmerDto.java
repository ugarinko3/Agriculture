package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO для Farmer
 */
@Data
public class FarmerDto {
    @Schema(name = "ID фермера")
    private UUID id;

    @Schema(name = "name",
            description = "Название организации",
            example = "ИП Иванов")
    private String name;

    @Schema(name = "legalForm",
            description = "ИП, ЮР, ФЗ",
            example = "ИП")
    private String legalForm;

    @Schema(name = "inn", description = "ИНН")
    private Long inn;

    @Schema(name = "kpp", description = "КПП")
    private String kpp;

    @Schema(name = "ogrn", description = "ОГРН")
    private String ogrn;

    @Schema(name = "registrationDistrict", description = "Район регистрации")
    private String registrationDistrict;

    @Schema(name = "farmer_district", description = "Районы посевных полей")
    private List<String> fieldDistricts;

    @Schema(name = "registrationDate", description = "Дата регистрации")
    private LocalDate registrationDate;
}
