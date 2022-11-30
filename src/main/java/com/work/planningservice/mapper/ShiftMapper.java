package com.work.planningservice.mapper;

import com.work.planningservice.dto.ShiftDTO;
import com.work.planningservice.model.Shift;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShiftMapper {

    @BeforeMapping
    default void validate(ShiftDTO shiftDTO) {
        if (shiftDTO.getWorker() == null) {
            throw new ValidationException("Worker is missing!");
        }

        if (shiftDTO.getWorker().getWorkerId() == null) {
            throw new ValidationException("WorkerId is missing!");
        }

        if (shiftDTO.getShiftDay() == null) {
            throw new ValidationException("ShiftDay is missing!");
        }

        if (shiftDTO.getShiftTime() == null) {
            throw new ValidationException("ShiftTime is missing!");
        }
    }

    ShiftDTO shiftToShiftDTO(Shift shift);

    @Mapping(target = "shiftDay", dateFormat = "dd-MM-yyyy")
    Shift shiftDtoToShift(ShiftDTO shiftDTO) throws ValidationException;
}
