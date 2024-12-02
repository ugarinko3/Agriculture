package org.example.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

/**
 * Сущность, представляющая район с атрибутами.
 */
@Setter
@Getter
@Entity
@Table(name = "districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(name = "name", description = "Название района", example = " Московский район")
    @Column(name = "name")
    private String name;

    @Schema(name = "code", description = "Код района", example = "777")
    @Column(name = "code")
    private Integer code;

    @Schema(name = "status", description = "Архивность", example = "false")
    @Column(name = "status")
    private boolean status;
}
