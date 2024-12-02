package org.example.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Сущность, представляющая организацию с атрибутами
 */
@Setter
@Getter
@Entity
@Table(name = "farmers")
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id")
    private District registrationDistrict;

    @Schema(name = "farmar_district", description = "Районы посевных полей")
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "farmer_district",
            joinColumns = @JoinColumn(name = "farmer_id"),
            inverseJoinColumns = @JoinColumn(name = "district_id")
    )
    private List<District> fieldDistricts;

    @Schema(name = "registrationDate", description = "Дата регистрации")
    private LocalDate registrationDate;

    @Schema(name = "archiveStatus", description = "Статус архивности")
    private boolean archiveStatus;
}
