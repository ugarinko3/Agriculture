package org.example.service;


import lombok.AllArgsConstructor;
import org.example.config.specification.DistrictSpecifications;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ValidationException;
import org.example.model.entity.District;
import org.example.model.request.CreateDistrictRequest;
import org.example.repository.DistrictRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;


    /**
     * Получение списка районов, внесенных в реестр.
     * Реализовать фильтрацию возвращаемого списка по названию и коду района.
     *
     * @param name
     * @param code
     * @return Список районов
     */
    public List<District> getFilteredDistricts(String name, Integer code) {
        Specification<District> specification = Specification
                .where(DistrictSpecifications.hasName(name))
                .and(DistrictSpecifications.hasCode(code))
                .and(DistrictSpecifications.isNotArchived());
        List<District> districts = districtRepository.findAll(specification);
        if (districts.isEmpty()) {
            throw new EntityNotFoundException("Список оказался пуст, попробуйте убрать пару фильтров");
        }
        return districts;
    }

    /**
     * Добавление района
     *
     * @param createDistrictRequest
     * @return UUID
     */
    public UUID createDistrict(CreateDistrictRequest createDistrictRequest) {
        District district = modelMapper.map(createDistrictRequest, District.class);
        districtRepository.save(district);
        return district.getId();

    }

    /**
     * Отправить в архив
     *
     * @param districtId
     * @return UUID
     * @throws org.springframework.web.client.HttpClientErrorException.NotFound
     */
    public UUID sendToArchive(UUID districtId) {
        return districtRepository.findById(districtId)
                .map(district -> {
                    district.setStatus(true);
                    districtRepository.save(district);
                    return districtId;
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Район с ID %s не найден", districtId)));
    }

    /**
     * Изменение в Районе
     *
     * @param district
     * @return UUID
     */
    public UUID editDistrict(District district) {
        return districtRepository.findById(district.getId())
                .map(changeDistrict -> {
                    districtRepository.save(changeDistrict);
                    return district.getId();
                        }).orElseThrow(() -> new ValidationException(String.format("Район с ID %s не найден", district.getId())));
    }
}
