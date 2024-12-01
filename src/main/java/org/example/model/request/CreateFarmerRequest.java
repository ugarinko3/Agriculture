package org.example.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * Реквест для Farmer
 */
@Getter
@Setter
@Component
public class CreateFarmerRequest {
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

    @Schema(name = "registrationDistrict",
            description = "Район регистрации",
            example = "701684f7-c875-4bdc-8dac-3057dfc2c93e")
    private UUID registrationDistrictId;

    @Schema(name = "farmar_district",
            description = "Районы посевных полей",
            example = "[\"701684f7-c875-4bdc-8dac-3057dfc2c932\", \"0dafaed3-8b26-427c-83bb-a006270a76b3\"]")
    private List<UUID> fieldDistrictsIds;

    @Schema(name = "registrationDate",
            description = "Дата регистрации",
            example = "2024-12-01")
    private LocalDate registrationDate;
}
