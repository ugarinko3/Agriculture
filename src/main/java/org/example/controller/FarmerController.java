package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.model.dto.FarmerDto;
import org.example.model.entity.Farmer;
import org.example.model.request.CreateFarmerRequest;
import org.example.model.request.GetFarmersRequest;
import org.example.service.FarmerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/farmers")
@Tag(name = "Farmer API", description = "API для управления фермерами")
public class FarmerController {

    private final FarmerService farmerService;

    /**
     * Поиск Фермера по фильтрам
     *
     * @param getFarmersRequest JSON
     * @return List {@link FarmerDto}
     */
    @Operation(summary = "Получить всех фермеров", description = "Возвращает список фермеров")
    @PostMapping("/find")
    public ResponseEntity<List<FarmerDto>> findFarmer(@RequestBody(required = false) GetFarmersRequest getFarmersRequest) {
        return ResponseEntity.ok(farmerService.findFarmers(getFarmersRequest));
    }


    /**
     * Создание Фермера
     *
     * @param createFarmerRequest
     * @return UUID
     */
    @Operation(summary = "Создание фермера", description = "Возвращает UUID фермера")
    @PostMapping
    public ResponseEntity<UUID> createFarmer(@RequestBody @Valid CreateFarmerRequest createFarmerRequest) {
        return ResponseEntity.ok(farmerService.createFarmer(createFarmerRequest));
    }

    @Operation(summary = "Измененние фермера", description = "Возвращает UUID фермера")
    @PutMapping("{fermerId}")
    public ResponseEntity<UUID> changeFarmer(@PathVariable UUID fermerId, @RequestBody CreateFarmerRequest farmer) {
        farmerService.changeFarmer(fermerId, farmer);
        return ResponseEntity.ok().build();
    }

    /**
     * Добавление Фермера в архив
     *
     * @param farmerId
     */
    @Operation(summary = "Перемещение фермера в архив")
    @DeleteMapping("/{farmerId}")
    public ResponseEntity<Void> sendToArchiveFarmer(@PathVariable UUID farmerId) {
        farmerService.sendToArchiveFarmer(farmerId);
        return ResponseEntity.ok().build();
    }

    /**
     * Поиск вермера по UUID
     *
     * @param farmerId
     * @return {@link FarmerDto}
     */
    @Operation(summary = "Найти по ID", description = "Возвращает FarmerDto")
    @GetMapping("/{farmerId}")
    public ResponseEntity<FarmerDto> farmerById(@PathVariable UUID farmerId) {
        return ResponseEntity.ok(farmerService.farmerById(farmerId));
    }

}
