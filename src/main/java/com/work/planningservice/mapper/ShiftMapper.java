package com.work.planningservice.mapper;

import com.work.planningservice.dto.ShiftDTO;
import com.work.planningservice.dto.WorkerDTO;
import com.work.planningservice.model.Shift;
import com.work.planningservice.model.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShiftMapper {

    ShiftDTO shiftToShiftDTO(Shift shift);

    @Mapping(target = "shiftStart", dateFormat = "dd-MM-yyyy'T'HH:mm")
    Shift shiftDtoToShift(ShiftDTO shiftDTO);
}
