package com.work.planningservice.mapper;

import com.work.planningservice.dto.WorkerDTO;
import com.work.planningservice.model.Worker;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkerMapper {

    @BeforeMapping
    default void validate(WorkerDTO workerDTO) {
        if (workerDTO.getName() == null) {
            throw new ValidationException("Worker name is missing!");
        }
    }

    WorkerDTO workerToWorkerDTO(Worker worker);

    Worker workerDtoTOWorker(WorkerDTO workerDto);
}
