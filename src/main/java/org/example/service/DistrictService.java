package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.config.specification.DistrictSpecification;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.model.dto.DistrictDto;
import org.example.model.entity.District;
import org.example.model.request.CreateUpdateDistrictRequest;
import org.example.repository.DistrictRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;

    /**
     * Получение списка районов, внесенных в реестр.
     * Реализовать фильтрацию возвращаемого списка по названию и коду района.
     *
     * @param name название района
     * @param code код района
     * @return Список районов
     */
    public List<DistrictDto> getFilteredDistricts(String name, Integer code) {
        Specification<District> specification = Specification
            .where(DistrictSpecification.hasName(name))
            .and(DistrictSpecification.hasCode(code))
            .and(DistrictSpecification.isNotArchived());
        return modelMapper.map(districtRepository.findAll(specification),
                new TypeToken<List<DistrictDto>>() {}.getType());
    }

    /**
     * Добавление района
     *
     * @param createUpdateDistrictRequest запрос на создание района
     * @return ID района
     */
    public UUID createDistrict(CreateUpdateDistrictRequest createUpdateDistrictRequest) {
        validationDistrict(createUpdateDistrictRequest);
        District district = modelMapper.map(createUpdateDistrictRequest, District.class);
        districtRepository.save(district);
        return district.getId();

    }

    /**
     * Отправить в архив
     *
     * @param districtId ID района
     * @throws EntityNotFoundException Район не найден
     */
    public void sendToArchive(UUID districtId) {
        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Район с ID %s не найден", districtId)));
        district.setStatus(true);
        districtRepository.save(district);
    }

    /**
     * Изменение района
     *
     * @param createUpdateDistrictRequest запрос на изменение района
     * @param districtId ID района
     */
    public void editDistrict(UUID districtId, CreateUpdateDistrictRequest createUpdateDistrictRequest) {
        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new ValidationException(String.format("Район с ID %s не найден", districtId)));
        validationDistrict(createUpdateDistrictRequest);
        modelMapper.map(createUpdateDistrictRequest, district);
        districtRepository.save(district);
    }

    private void validationDistrict(CreateUpdateDistrictRequest district) {
        if (district.getName() == null || district.getName().trim().isEmpty()) {
            throw new ValidationException("Имя является обязательным полем");
        }
    }
}
