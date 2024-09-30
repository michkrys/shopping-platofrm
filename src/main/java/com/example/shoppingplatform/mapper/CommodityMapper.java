package com.example.shoppingplatform.mapper;

import com.example.shoppingplatform.domain.Commodity;
import com.example.shoppingplatform.dto.CommodityDto;
import com.example.shoppingplatform.dto.CreateCommodityRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommodityMapper {

    @Mapping(target = "id", ignore = true)
    Commodity toEntity(CreateCommodityRequestDto createCommodityRequestDto);

    CommodityDto toDto(Commodity commodity);
}
