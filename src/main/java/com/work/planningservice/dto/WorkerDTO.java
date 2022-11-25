package com.work.planningservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDTO {

    private Long workerId;

    private String name;

//    private Set<ShiftDTO> shifts;

}
