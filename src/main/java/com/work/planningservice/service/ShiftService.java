package com.work.planningservice.service;

import com.work.planningservice.model.Shift;
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

        //check shift starts at 0, 8, 16
        //check no shift on that day
        //date validation and format
        //what if the worker from shift is missing, or the id is missing - check
        Optional<Worker> worker = workerRepository.findByWorkerId(shift.getWorker().getWorkerId());
        if (worker.isPresent()){
            shift.setWorker(worker.get());
        }
        else throw new RuntimeException("invalid worker");

        return shiftRepository.save(shift);
    }
}
