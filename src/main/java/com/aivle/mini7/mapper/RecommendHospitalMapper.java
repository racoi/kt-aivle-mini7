package com.aivle.mini7.mapper;

import com.aivle.mini7.client.dto.HospitalResponse;
import com.aivle.mini7.model.RecommendHospital;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecommendHospitalMapper {
    @Mapping(target="id",ignore=true)
    @Mapping(target="institutionCode",ignore=true)
    @Mapping(target="inputId",ignore=true)
    RecommendHospital hospitalResponseToRecommendHospital(HospitalResponse response);
}
