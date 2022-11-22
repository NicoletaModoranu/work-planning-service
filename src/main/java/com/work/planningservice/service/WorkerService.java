package com.work.planningservice.service;

import com.work.planningservice.model.SchedulerException;
import com.work.planningservice.model.Worker;
import com.work.planningservice.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getByID(Long id) {
        Optional<Worker> worker = workerRepository.findByWorkerId(id);
        if (worker.isPresent()) {
            return worker.get();
        } else {
            throw new SchedulerException("THe worker with id " + id + " was not found");
        }
    }

    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }
}
