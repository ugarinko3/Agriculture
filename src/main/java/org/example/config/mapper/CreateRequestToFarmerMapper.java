package org.example.config.mapper;

import lombok.AllArgsConstructor;
import org.example.exception.EntityNotFoundException;
import org.example.model.entity.District;
import org.example.model.entity.Farmer;
import org.example.model.request.CreateFarmerRequest;
import org.example.repository.DistrictRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Кастомный Mapper, из такого объекта
 * {@link UUID}, в {@link District}.
 */
@Configuration
@AllArgsConstructor
public class CreateRequestToFarmerMapper {
    private final ModelMapper modelMapper;
    private final DistrictRepository districtRepository;


    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(CreateFarmerRequest.class, Farmer.class)
                .addMappings(m -> {
                    m.using(toDistrictConverter()).map(CreateFarmerRequest::getRegistrationDistrictId,
                            Farmer::setRegistrationDistrict);
                    m.using(toListDistrictsConverter()).map(CreateFarmerRequest::getFieldDistrictsIds,
                            Farmer::setFieldDistricts);
                });
    }

    private Converter<UUID, District> toDistrictConverter() {
        return context -> {
            UUID districtId = context.getSource();
            return districtId != null ? districtRepository.findById(districtId).orElseThrow(() -> new EntityNotFoundException(String.format("Район с ID %s не найден", districtId))) : null;

        };
    }

    private Converter<List<UUID>, List<District>> toListDistrictsConverter() {
        return context -> {
            List<UUID> districtIds = context.getSource();
            return districtIds == null ? null :
                    districtIds.stream()
                            .map(districtId -> districtRepository.findById(districtId).orElseThrow(() -> new EntityNotFoundException(String.format("Район с ID %s не найден", districtId))))
                            .collect(Collectors.toList());
        };
    }
}
