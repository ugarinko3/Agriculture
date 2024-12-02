package org.example.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Реквест для Farmer
 */
@Data
public class CreateUpdateFarmerRequest {
    @Schema(name = "name",
            description = "Название организации",
            example = "ИП Иванов")
    private String name;

    @Schema(name = "legalForm",
            description = "ИП, ЮР, ФЛ",
            example = "ИП",
            allowableValues = {"ИП", "ЮР", "ФЛ"}
    )
    private String legalForm;

    @Schema(name = "inn",
            description = "ИНН",
            example = "2321123412313")
    private Long inn;

    @Schema(name = "kpp",
            description = "КПП",
            example = "2131231241")
    private String kpp;

    @Schema(name = "ogrn",
            description = "ОГРН",
            example = "4425345435")
    private String ogrn;

    @Schema(name = "registrationDistrictId",
            description = "Район регистрации")
    private UUID registrationDistrictId;

    @Schema(name = "fieldDistrictsIds",
            description = "Районы посевных полей",
            example = "null")
    private List<UUID> fieldDistrictsIds;

    @Schema(name = "registrationDate",
            description = "Дата регистрации",
            example = "2024-12-01")
    private LocalDate registrationDate;
}
