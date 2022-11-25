package com.work.planningservice.dto;

import com.work.planningservice.model.Shift;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class WorkerDTO {

    private Long workerId;

    private String name;

    private Set<Shift> shifts;

}
