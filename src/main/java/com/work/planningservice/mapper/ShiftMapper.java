package com.work.planningservice.mapper;

import com.work.planningservice.dto.ShiftDTO;
import com.work.planningservice.model.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShiftMapper {

    ShiftDTO shiftToShiftDTO(Shift shift);

    @Mapping(target = "shiftStart", dateFormat = "dd-MM-yyyy")
    Shift shiftDtoToShift(ShiftDTO shiftDTO);
}
