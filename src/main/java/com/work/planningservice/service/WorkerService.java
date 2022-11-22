package com.work.planningservice.service;

import com.work.planningservice.model.Worker;
import com.work.planningservice.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    private WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getByID(Long id){
      return  workerRepository.findByWorkerId(id).get();
    }

    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }
}
