package com.aivle.mini7.mapper;

import com.aivle.mini7.dto.InputDto;
import com.aivle.mini7.model.Input;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InputMapper {

    @Mapping(target="inputId",ignore=true)
    Input inputDtoToInput(InputDto.Get get);
}
