package com.work.planningservice.controller;

import com.work.planningservice.dto.WorkerDTO;
import com.work.planningservice.mapper.WorkerMapper;
import com.work.planningservice.model.Worker;
import com.work.planningservice.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/worker")
@RestController
public class WorkerController {

    WorkerService workerService;

    WorkerMapper workerMapper;

    @Autowired
    public WorkerController(WorkerService workerService, WorkerMapper workerMapper) {
        this.workerService = workerService;
        this.workerMapper = workerMapper;
    }

    @GetMapping
    public ResponseEntity<List<WorkerDTO>> all() {
        return new ResponseEntity<>(workerService.getAllWorkers().stream().map(worker -> workerMapper.workerToWorkerDTO(worker)).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(workerMapper.workerToWorkerDTO(workerService.getByID(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WorkerDTO> newWorker(@RequestBody WorkerDTO workerDTO) {
        Worker savedUser = workerService.save(workerMapper.workerDtoTOWorker(workerDTO));
        return new ResponseEntity<>(workerMapper.workerToWorkerDTO(savedUser), HttpStatus.CREATED);
    }
}
