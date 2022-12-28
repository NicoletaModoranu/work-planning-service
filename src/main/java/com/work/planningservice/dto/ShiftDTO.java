package com.work.planningservice.dto;

import com.work.planningservice.model.ShiftTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDTO {

    private Long shiftId;

    private WorkerDTO worker;

    private String shiftDay;

    private ShiftTime shiftTime;
}

