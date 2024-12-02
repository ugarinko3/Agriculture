package org.example.service;

import lombok.AllArgsConstructor;
import org.example.config.specification.FarmerSpecification;
import org.example.exception.*;
import org.example.model.dto.FarmerDto;
import org.example.model.entity.Farmer;
import org.example.model.enums.LegalFormEnum;
import org.example.model.request.CreateFarmerRequest;
import org.example.model.request.GetFarmersRequest;
import org.example.repository.FarmerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final ModelMapper modelMapper;

    /**
     * Получить всех фермеров.
     *
     * @param getFarmersRequest
     * @return Список фермеров.
     */
    public List<FarmerDto> findFarmers(GetFarmersRequest getFarmersRequest) {
        if (getFarmersRequest == null) {
            getFarmersRequest = new GetFarmersRequest();
        }
        Specification<Farmer> specification = Specification
                .where(FarmerSpecification.hasName(getFarmersRequest.getName()))
                .and(FarmerSpecification.hasInn(getFarmersRequest.getInn()))
                .and(FarmerSpecification.hasDistrict(getFarmersRequest.getRegistrationDistrictId()))
                .and(FarmerSpecification.hasDate(getFarmersRequest.getRegistrationDate()))
                .and(FarmerSpecification.isNotArchived(getFarmersRequest.isArchiveStatus()));

        return modelMapper
                .map(farmerRepository.findAll(specification), new TypeToken<List<FarmerDto>>() {
        }.getType());
    }

    /**
     * Добавление Фермера
     *
     * @param createFarmerRequest
     * @return UUID
     */
    public UUID createFarmer(CreateFarmerRequest createFarmerRequest) {
        validateFarmer(createFarmerRequest);
        Farmer farmer = modelMapper.map(createFarmerRequest, Farmer.class);
        farmerRepository.save(farmer);
        return farmer.getId();
    }

    /**
     * Добавление в архив
     *
     * @param farmerId
     */
    public void sendToArchiveFarmer(UUID farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId).orElseThrow(() -> new EntityNotFoundException(String.format("Ошибка добавления фермера с ID %s в архив", farmerId)));
        farmer.setArchiveStatus(true);
        farmerRepository.save(farmer);
    }

    /**
     * Изменение Фермера
     *
     * @param createFarmerRequest
     * @param farmerId
     */
    public void changeFarmer(UUID farmerId ,CreateFarmerRequest createFarmerRequest) {
        validateFarmer(createFarmerRequest);
        Farmer farmer = farmerRepository.findById(farmerId).orElseThrow(() -> new EntityNotFoundException(String.format("Ошибка! Фермер с ID %s не обнаружен", farmerId)));
        modelMapper.map(createFarmerRequest, farmer);
        farmerRepository.save(farmer);
    }

    /**
     * Поиск по UUID
     *
     * @param id
     * @return {@link FarmerDto}
     */
    public FarmerDto farmerById(UUID id) {
        Farmer farmer = farmerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Фермер с ID %s не найден", id)));
        return modelMapper.map(farmer, FarmerDto.class);
    }

    private void validateFarmer(CreateFarmerRequest farmer) {
        if (!LegalFormEnum.isExistByCode(farmer.getLegalForm())) {
            throw new ValidationException("Организационно-правовая форма введена не верно, допустимые значения: ИП, ФЛ, ЮЛ");
        }
        if (farmer.getName() == null || farmer.getName().trim().isEmpty()) {
            throw new ValidationException("Имя является обязательным полем");
        }
        if (farmer.getInn() == null || farmer.getInn() < 0) {
            throw new ValidationException("ИНН не заполено или введено неверно");
        }
    }
}
