package com.work.planningservice.service;

import com.work.planningservice.model.Shift;
import com.work.planningservice.model.ShiftException;
import com.work.planningservice.model.Worker;
import com.work.planningservice.repository.ShiftRepository;
import com.work.planningservice.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShiftService {


    private final ShiftRepository shiftRepository;
    private final WorkerRepository workerRepository;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository, WorkerRepository workerRepository) {
        this.shiftRepository = shiftRepository;
        this.workerRepository = workerRepository;
    }


    public Shift save(Shift shift) {
        attachWorker(shift);
        try {
            return shiftRepository.save(shift);
        } catch (Exception e) {
            throw new ShiftException(e.getMessage(), e.getCause());
        }
    }

    private Shift attachWorker(Shift shift) {
        if (shift.getWorker() == null || (shift.getWorker() != null && shift.getWorker().getWorkerId() == null)) {
            throw new IllegalArgumentException("Cannot save shift. Worker data was not found!");
        }

        long workerId = shift.getWorker().getWorkerId();
        Optional<Worker> worker = workerRepository.findByWorkerId(workerId);
        if (worker.isPresent()) {
            shift.setWorker(worker.get());
        } else {
            throw new ShiftException("A worker with the " + workerId + " does not exist in DB.");
        }

        return shift;
    }
}
