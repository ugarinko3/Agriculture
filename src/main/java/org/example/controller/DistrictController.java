package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.DistrictDto;
import org.example.model.request.CreateUpdateDistrictRequest;
import org.example.service.DistrictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "District API", description = "API для управления районами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/districts")
public class DistrictController {

    private final DistrictService districtService;

    /**
     * Создание района
     *
     * @param createUpdateDistrictRequest запрос на создание района
     * @return UUID района
     */
    @Operation(summary = "Создать район", description = "UUID района")
    @PostMapping
    public ResponseEntity<UUID> createDistrict(@RequestBody CreateUpdateDistrictRequest createUpdateDistrictRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(districtService.createDistrict(createUpdateDistrictRequest));
    }

    /**
     * Добавление района в архив
     *
     * @param districtId ID района
     */
    @Operation(summary = "Переводит район в архив", description = "UUID района")
    @DeleteMapping("/{districtId}")
    public ResponseEntity<Void> sendToArchive(@PathVariable UUID districtId) {
        districtService.sendToArchive(districtId);
        return ResponseEntity.ok().build();
    }

    /**
     * Поиск района
     *
     * @param name название района
     * @param code код района
     * @return List {@link DistrictDto}
     */
    @Operation(summary = "Получить все районы", description = "Возвращает список всех районов с возможностью фильтрации")
    @GetMapping
    public ResponseEntity<List<DistrictDto>> getDistricts(
            @Parameter(description = "Название района для фильтрации") @RequestParam(required = false) String name,
            @Parameter(description = "Код района для фильтрации") @RequestParam(required = false) Integer code) {
        return ResponseEntity.ok(districtService.getFilteredDistricts(name, code));
    }

    /**
     * Изменение района
     *
     * @param createUpdateDistrictRequest запрос на обновление района
     * @return UUID
     */
    @Operation(summary = "Изменение района", description = "UUID района")
    @PutMapping("{districtId}")
    public ResponseEntity<Void> editDistrict(@PathVariable UUID districtId,
                                             @RequestBody CreateUpdateDistrictRequest createUpdateDistrictRequest) {
        districtService.editDistrict(districtId , createUpdateDistrictRequest);
        return ResponseEntity.ok().build();
    }
}
