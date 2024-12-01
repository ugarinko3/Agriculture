package org.example.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.example.model.entity.District;
import org.example.model.request.CreateDistrictRequest;
import org.example.service.DistrictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/districts")
@Tag(name = "District API", description = "API для управления районами")
public class DistrictController {

    private final DistrictService districtService;


    /**
     * Создание района
     *
     * @param createDistrictRequest
     * @return UUID
     */
    @Operation(summary = "Создать район", description = "UUID района")
    @PostMapping
    public ResponseEntity<UUID> createDistrict(@RequestBody CreateDistrictRequest createDistrictRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(districtService.createDistrict(createDistrictRequest));
    }


    /**
     * Добавление района в архив
     *
     * @param districtId
     * @return UUID
     */
    @Operation(summary = "Переводит район в архив", description = "UUID района")
    @DeleteMapping("/{districtId}")
    public ResponseEntity<UUID> sendToArchive(@PathVariable UUID districtId) {
        return ResponseEntity.ok(districtService.sendToArchive(districtId));
    }

    /**
     * Поиск района
     *
     * @param name
     * @param code
     * @return List {@link District}
     */
    @Operation(summary = "Получить все районы", description = "Возвращает список всех районов с возможностью фильтрации")
    @GetMapping
    public ResponseEntity<List<District>> getDistricts(
            @Parameter(description = "Название района для фильтрации") @RequestParam(required = false) String name,
            @Parameter(description = "Код района для фильтрации") @RequestParam(required = false) Integer code) {
        return ResponseEntity.ok(districtService.getFilteredDistricts(name, code));
    }

    /**
     * Изменение района
     *
     * @param district
     * @return UUID
     */
    @Operation(summary = "Изменение района", description = "UUID района")
    @PutMapping
    public ResponseEntity<UUID> editDistrict(@RequestBody District district) {
        return ResponseEntity.ok(districtService.editDistrict(district));
    }


}
