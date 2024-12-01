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
                .and(FarmerSpecification.isNotArchived());

        List<Farmer> farmers = farmerRepository.findAll(specification);
        if (farmers.isEmpty()) {
            throw new EntityNotFoundException("Список оказался пуст, попробуйте убрать пару фильтров");
        }
        return modelMapper.map(farmers, new TypeToken<List<FarmerDto>>() {
        }.getType());
    }

    /**
     * Добавление Фермера
     *
     * @param createFarmerRequest
     * @return UUID
     */
    public UUID createFarmer(CreateFarmerRequest createFarmerRequest) {
        Farmer farmer = modelMapper.map(createFarmerRequest, Farmer.class);
        validateFarmer(farmer);
        farmerRepository.save(farmer);
        return farmer.getId();
    }

    /**
     * Добавление в архив
     *
     * @param farmerId
     * @return UUID
     */
    public UUID sendToArchiveFarmer(UUID farmerId) {
        return farmerRepository.findById(farmerId)
                .map(farmer -> {
                    farmer.setArchiveStatus(true);
                    validateFarmer(farmer);
                    farmerRepository.save(farmer);
                    return farmerId;
                }).orElseThrow(() -> new EntityNotFoundException(String.format("Ошибка добавления фермера с ID %s в архив", farmerId)));
    }

    /**
     * Изменение Фермера
     *
     * @param farmer
     * @return UUID
     */
    public UUID changeFarmer(Farmer farmer) {
        return farmerRepository.findById(farmer.getId())
                .map(changeFarmer -> {
                    validateFarmer(farmer);
                    farmerRepository.save(changeFarmer);
                    return changeFarmer.getId();
                }).orElseThrow(() -> new EntityNotFoundException(String.format("Ошибка! Фермер с ID %s не обнаружен", farmer.getId())));

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

    private void validateFarmer(Farmer farmer) {
        if (!LegalFormEnum.isExistByCode(farmer.getLegalForm())) {
            throw new ValidationException("Организационно-правовая форма введена не верно, допустимые значения: ИП, ФЗ, ЮЛ");
        }
        if (farmer.getName() == null || farmer.getName().trim().isEmpty()) {
            throw new ValidationException("Имя введено неверно");
        }
        if (farmer.getInn() == null || farmer.getInn() < 0) {
            throw new ValidationException("ИНН введено неверно");
        }
    }
}
