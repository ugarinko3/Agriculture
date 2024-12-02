package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.FarmerDto;
import org.example.model.request.CreateUpdateFarmerRequest;
import org.example.model.request.GetFarmersRequest;
import org.example.service.FarmerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/farmers")
@Tag(name = "Farmer API", description = "API для управления фермерами")
public class FarmerController {

    private final FarmerService farmerService;

    /**
     * Поиск фермеров с фильтрацией
     *
     * @param getFarmersRequest запрос на фильтрацию фермеров
     * @return List {@link FarmerDto}
     */
    @Operation(summary = "Получить всех фермеров", description = "Возвращает список фермеров")
    @PostMapping("/find")
    public ResponseEntity<List<FarmerDto>> findFarmer(@RequestBody(required = false) GetFarmersRequest getFarmersRequest) {
        return ResponseEntity.ok(farmerService.findFarmers(getFarmersRequest));
    }

    /**
     * Создание фермера
     *
     * @param createUpdateFarmerRequest запрос на создание фермера
     * @return UUID
     */
    @Operation(summary = "Создание фермера", description = "Возвращает UUID фермера")
    @PostMapping
    public ResponseEntity<UUID> createFarmer(@RequestBody @Valid CreateUpdateFarmerRequest createUpdateFarmerRequest) {
        return ResponseEntity.ok(farmerService.createFarmer(createUpdateFarmerRequest));
    }

    /**
     * Изменение фермера по ID
     *
     * @param farmerId ID фермера
     * @param request запрос на изменение фермера
     */
    @Operation(summary = "Измененние фермера")
    @PutMapping("{farmerId}")
    public ResponseEntity<Void> changeFarmer(@PathVariable UUID farmerId,
                                             @RequestBody CreateUpdateFarmerRequest request) {
        farmerService.changeFarmer(farmerId, request);
        return ResponseEntity.ok().build();
    }

    /**
     * Добавление фермера в архив
     *
     * @param farmerId ID фермера
     */
    @Operation(summary = "Перемещение фермера в архив")
    @DeleteMapping("/{farmerId}")
    public ResponseEntity<Void> sendToArchiveFarmer(@PathVariable UUID farmerId) {
        farmerService.sendToArchiveFarmer(farmerId);
        return ResponseEntity.ok().build();
    }

    /**
     * Поиск фермера по UUID
     *
     * @param farmerId ID фермера
     * @return {@link FarmerDto}
     */
    @Operation(summary = "Найти по ID", description = "Возвращает FarmerDto")
    @GetMapping("/{farmerId}")
    public ResponseEntity<FarmerDto> farmerById(@PathVariable UUID farmerId) {
        return ResponseEntity.ok(farmerService.getFarmerById(farmerId));
    }

}
