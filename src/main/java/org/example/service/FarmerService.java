package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.config.specification.FarmerSpecification;
import org.example.exception.*;
import org.example.model.dto.FarmerDto;
import org.example.model.entity.Farmer;
import org.example.model.enums.LegalFormEnum;
import org.example.model.request.CreateUpdateFarmerRequest;
import org.example.model.request.GetFarmersRequest;
import org.example.repository.FarmerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final ModelMapper modelMapper;

    /**
     * Получить всех фермеров
     *
     * @param getFarmersRequest запрос на фильтрацию фермеров
     * @return Список фермеров
     */
    public List<FarmerDto> findFarmers(GetFarmersRequest getFarmersRequest) {
        if (getFarmersRequest == null) getFarmersRequest = new GetFarmersRequest();

        Specification<Farmer> specification = Specification
                .where(FarmerSpecification.hasName(getFarmersRequest.getName()))
                .and(FarmerSpecification.hasInn(getFarmersRequest.getInn()))
                .and(FarmerSpecification.hasDistrict(getFarmersRequest.getRegistrationDistrictId()))
                .and(FarmerSpecification.hasDate(getFarmersRequest.getRegistrationDate()))
                .and(FarmerSpecification.isNotArchived(getFarmersRequest.isArchiveStatus()));

        return modelMapper.map(farmerRepository.findAll(specification), new TypeToken<List<FarmerDto>>() {}.getType());
    }

    /**
     * Создание фермера
     *
     * @param createUpdateFarmerRequest запрос на создание фермера
     * @return ID фермера
     */
    public UUID createFarmer(CreateUpdateFarmerRequest createUpdateFarmerRequest) {
        validateFarmer(createUpdateFarmerRequest);
        Farmer farmer = modelMapper.map(createUpdateFarmerRequest, Farmer.class);
        farmerRepository.save(farmer);
        return farmer.getId();
    }

    /**
     * Добавление фермера в архив
     *
     * @param farmerId ID фермера
     */
    public void sendToArchiveFarmer(UUID farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ошибка добавления фермера с ID %s в архив", farmerId)));
        farmer.setArchiveStatus(true);
        farmerRepository.save(farmer);
    }

    /**
     * Изменение фермера по ID
     *
     * @param createUpdateFarmerRequest запрос на изменение фермера
     * @param farmerId ID фермера
     */
    public void changeFarmer(UUID farmerId, CreateUpdateFarmerRequest createUpdateFarmerRequest) {
        validateFarmer(createUpdateFarmerRequest);
        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ошибка! Фермер с ID %s не обнаружен", farmerId)));
        modelMapper.map(createUpdateFarmerRequest, farmer);
        farmerRepository.save(farmer);
    }

    /**
     * Поиск фермера по ID
     *
     * @param farmerId ID фермера
     * @return {@link FarmerDto}
     */
    public FarmerDto getFarmerById(UUID farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId).
                orElseThrow(() -> new EntityNotFoundException(String.format("Фермер с ID %s не найден", farmerId)));
        return modelMapper.map(farmer, FarmerDto.class);
    }

    private void validateFarmer(CreateUpdateFarmerRequest farmer) {
        if (!LegalFormEnum.isExistByCode(farmer.getLegalForm())) {
            throw new ValidationException("Организационно-правовая форма введена не верно, допустимые значения: ИП, ФЛ, ЮЛ");
        }
        if (farmer.getName() == null || farmer.getName().trim().isEmpty()) {
            throw new ValidationException("Имя является обязательным полем");
        }
        if (farmer.getInn() == null || farmer.getInn() < 0) {
            throw new ValidationException("ИНН не заполено или введено неверно");
        }
        if (farmer.getRegistrationDistrictId() == null ||
                !farmerRepository.findById(farmer.getRegistrationDistrictId()).isPresent()) {
            throw new EntityNotFoundException(String.format("Район с ID %s не найден",
                    farmer.getRegistrationDistrictId()));
        }
    }
}
