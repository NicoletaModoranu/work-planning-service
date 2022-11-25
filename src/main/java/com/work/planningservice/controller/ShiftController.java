package com.work.planningservice.controller;

import com.work.planningservice.dto.ShiftDTO;
import com.work.planningservice.mapper.ShiftMapper;
import com.work.planningservice.mapper.WorkerMapper;
import com.work.planningservice.model.Shift;
import com.work.planningservice.model.Worker;
import com.work.planningservice.service.ShiftService;
import com.work.planningservice.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Set;

@RequestMapping("/shift")
@RestController
public class ShiftController {

    ShiftService shiftService;

    ShiftMapper shiftMapper;

    @Autowired
    public ShiftController(ShiftService shiftService, ShiftMapper shiftMapper) {
        this.shiftService = shiftService;
        this.shiftMapper = shiftMapper;
    }

    //get worker's shift in time interval, by id
    public ResponseEntity<Set<ShiftDTO>> getWorkersShifts(Long workerId, Date start, Date end) {

        return null;
    }

    @PostMapping
    public ResponseEntity<ShiftDTO> saveShift(@RequestBody ShiftDTO shiftDto) {
        Shift shift = shiftService.save(shiftMapper.shiftDtoToShift(shiftDto));
        return new ResponseEntity<>(shiftMapper.shiftToShiftDTO(shift), HttpStatus.CREATED);
    }
    //
}
