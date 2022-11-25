package com.work.planningservice.mapper;

import com.work.planningservice.dto.WorkerDTO;
import com.work.planningservice.model.Worker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkerMapper {

    WorkerDTO workerToWorkerDTO(Worker shift);

    Worker workerDtoTOWorker(WorkerDTO shiftDto);
}
