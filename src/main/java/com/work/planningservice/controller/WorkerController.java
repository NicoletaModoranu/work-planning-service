package com.work.planningservice.controller;

import com.work.planningservice.dto.WorkerDTO;
import com.work.planningservice.mapper.WorkerMapper;
import com.work.planningservice.model.Worker;
import com.work.planningservice.service.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/worker")
@RestController
public class WorkerController {

    Logger logger = LoggerFactory.getLogger(WorkerController.class);

    WorkerService workerService;

    WorkerMapper workerMapper;

    @Autowired
    public WorkerController(WorkerService workerService, WorkerMapper workerMapper) {
        this.workerService = workerService;
        this.workerMapper = workerMapper;
    }

    @GetMapping
    public ResponseEntity<List<WorkerDTO>> all() {
        logger.info("Entered get all workers.");

        return new ResponseEntity<>(workerService.getAllWorkers().stream().map(worker -> workerMapper.workerToWorkerDTO(worker)).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerDTO> getById(@PathVariable Long id) {
        logger.info("Entered getById with id = {}", id);

        return new ResponseEntity<>(workerMapper.workerToWorkerDTO(workerService.getByID(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WorkerDTO> createNewWorker(@RequestBody WorkerDTO workerDTO) {
        logger.info("Entered create newWorker with name = {}", workerDTO.getName());

        Worker savedUser = workerService.save(workerMapper.workerDtoTOWorker(workerDTO));
        return new ResponseEntity<>(workerMapper.workerToWorkerDTO(savedUser), HttpStatus.CREATED);
    }
}
