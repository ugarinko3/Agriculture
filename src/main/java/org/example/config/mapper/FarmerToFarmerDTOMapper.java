package org.example.config.mapper;

import lombok.AllArgsConstructor;
import org.example.model.dto.FarmerDto;
import org.example.model.entity.District;
import org.example.model.entity.Farmer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Кастомный Mapper, из такого объекта
 * {@link Farmer}, в {@link FarmerDto}.
 */
@AllArgsConstructor
@Configuration
public class FarmerToFarmerDTOMapper {

    private final ModelMapper modalMapper;

    @PostConstruct
    public void setupMapper() {
        modalMapper.createTypeMap(Farmer.class, FarmerDto.class)
                .addMappings(m -> {
                    m.using(toNameConverter()).map(Farmer::getRegistrationDistrict,
                            FarmerDto::setRegistrationDistrict);
                    m.using(toListStringConverter()).map(Farmer::getFieldDistricts,
                            FarmerDto::setFieldDistricts);
                });
    }

    private Converter<District, String> toNameConverter() {
        return context -> {
            District source = context.getSource();
            return source != null ? source.getName() : null;
        };
    }

    private Converter<List<District>, List<String>> toListStringConverter() {
        return context -> {
            List<District> source = context.getSource();
            return source.stream()
                    .map(District::getName)
                    .collect(Collectors.toList());
        };
    }

}
