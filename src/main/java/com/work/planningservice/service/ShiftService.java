package com.work.planningservice.service;

import com.work.planningservice.model.Shift;
import com.work.planningservice.model.ShiftException;
import com.work.planningservice.model.Worker;
import com.work.planningservice.repository.ShiftRepository;
import com.work.planningservice.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;

@Service
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final WorkerRepository workerRepository;

    @Autowired
    public ShiftService(ShiftRepository shiftRepository, WorkerRepository workerRepository) {
        this.shiftRepository = shiftRepository;
        this.workerRepository = workerRepository;
    }

    public Set<Shift> getShifts(Long workerId, String dateStart, String dateEnd) {

        if (workerId == null || dateStart == null || dateEnd == null) {
            throw new ShiftException("WorkerId, dateStart and dateEnd are mandatory fields!");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate formattedDateStart;
        LocalDate formattedDateEnd;

        try {
            formattedDateStart = LocalDate.parse(dateStart, dateTimeFormatter);
            formattedDateEnd = LocalDate.parse(dateEnd, dateTimeFormatter);
        } catch (Exception e) {
            throw new ShiftException("Could not parse provided dates!");
        }

        return shiftRepository.findShifts(workerId, formattedDateStart, formattedDateEnd);
    }

    public Shift save(Shift shift) {
        attachWorker(shift);
        try {
            return shiftRepository.save(shift);
        } catch (Exception e) {
            throw new ShiftException(e.getMessage(), e.getCause());
        }
    }

    private void attachWorker(Shift shift) {
        long workerId = shift.getWorker().getWorkerId();

        Optional<Worker> worker = workerRepository.findByWorkerId(workerId);
        if (worker.isPresent()) {
            shift.setWorker(worker.get());
        } else {
            throw new ShiftException("A worker with the " + workerId + " does not exist in DB.");
        }
    }
}
