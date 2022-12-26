package com.work.planningservice.service;

import com.work.planningservice.model.Shift;
import com.work.planningservice.model.ShiftException;
import com.work.planningservice.model.ShiftTime;
import com.work.planningservice.model.Worker;
import com.work.planningservice.repository.ShiftRepository;
import com.work.planningservice.repository.WorkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShiftServiceTest {

    @Mock
    private WorkerRepository workerRepository;

    @Mock
    private ShiftRepository shiftRepository;

    @Mock
    private ShiftService shiftService;

    @BeforeEach
    void initService() {
        workerRepository = Mockito.mock(WorkerRepository.class);
        shiftRepository = Mockito.mock(ShiftRepository.class);
        shiftService = new ShiftService(shiftRepository, workerRepository);
    }

    @Test
    void test_getShifts_success() {
        //given
        long workerId = 1L;
        LocalDate startDate = LocalDate.of(2022, 11, 01);
        LocalDate endDate = LocalDate.of(2022, 11, 30);

        Mockito.when(shiftRepository.findShifts(workerId, startDate, endDate)).thenReturn(getShifts());

        //when
        Set<Shift> resultList = shiftService.getShifts(1L, "01-11-2022", "30-11-2022");

        //then
        List<Shift> convertedResultList = new ArrayList<>(resultList);
        assertEquals(2, convertedResultList.size());
    }

    @Test
    void test_getShifts_badData_throwsShiftException() {
        //given

        //when
        Exception resultedException = null;
        try {
            Set<Shift> resultList = shiftService.getShifts(null, "01-11-2022", "30-11-2022");
        } catch (Exception e) {
            resultedException = e;
        }

        //then
        assertTrue(resultedException instanceof ShiftException);
        assertEquals("WorkerId, dateStart and dateEnd are mandatory fields!", resultedException.getMessage());
    }

    @Test
    void test_saveShift_workerNotPresentInDB() {
        //given
        Mockito.when(workerRepository.findByWorkerId(1L)).thenReturn(Optional.empty());

        //when
        Exception resultedException = null;
        try {
            shiftService.save(getShift());
        } catch (Exception e) {
            resultedException = e;
        }

        //then
        assertTrue(resultedException instanceof ShiftException);
        assertEquals("A worker with the 1 does not exist in DB.", resultedException.getMessage());

    }


    @Test
    void test_saveShift_throwsException() {
        //given
        Mockito.when(workerRepository.findByWorkerId(1L)).thenReturn(Optional.of(new Worker()));
        Mockito.when(shiftRepository.save(Mockito.any(Shift.class))).thenThrow(new ShiftException("Existing date"));

        //when
        Exception resultedException = null;
        try {
            shiftService.save(getShift());
        } catch (Exception e) {
            resultedException = e;
        }

        //then
        assertEquals("Existing date", resultedException.getMessage());
    }

    @Test
    void test_saveShift_success() {
        //given
        Shift expectedShift = getShift();
        Mockito.when(workerRepository.findByWorkerId(1L)).thenReturn(Optional.of(expectedShift.getWorker()));
        Mockito.when(shiftRepository.save(Mockito.any(Shift.class))).thenReturn(expectedShift);

        //when
        Shift savedShift = shiftService.save(expectedShift);

        //then
        assertEquals(expectedShift.getShiftId(), savedShift.getShiftId());
        assertEquals(expectedShift.getWorker().getWorkerId(), savedShift.getWorker().getWorkerId());
        assertEquals(expectedShift.getShiftDay(), savedShift.getShiftDay());
        assertEquals(expectedShift.getShiftTime(), savedShift.getShiftTime());
    }

    @Test
    void test_saveShift_nullWorker_throwsIllegalArgEx() {
        //given
        Shift shift = getShift();
        shift.setWorker(null);

        //when
        Exception resultedException = null;
        try {
            shiftService.save(shift);
        } catch (Exception e) {
            resultedException = e;
        }

        //then
        assertEquals("Cannot save shift. Worker data was not found!", resultedException.getMessage());
        assertTrue(resultedException instanceof IllegalArgumentException);
    }

    private Shift getShift() {
        Worker w = new Worker();
        w.setWorkerId(1L);

        Shift s1 = new Shift();
        s1.setWorker(w);
        s1.setShiftId(1L);
        s1.setShiftDay(LocalDate.of(2022, 11, 03));
        s1.setShiftTime(ShiftTime.SHIFT_0_8);

        return s1;
    }

    private Set<Shift> getShifts() {
        Set<Shift> shifts = new HashSet<>();

        Worker w = new Worker();
        w.setWorkerId(1L);

        Shift s1 = new Shift();
        s1.setWorker(w);
        s1.setShiftId(1L);
        s1.setShiftDay(LocalDate.of(2022, 11, 03));
        s1.setShiftTime(ShiftTime.SHIFT_0_8);

        Shift s2 = new Shift();
        s2.setWorker(w);
        s2.setShiftId(2L);
        s2.setShiftDay(LocalDate.of(2022, 11, 05));
        s2.setShiftTime(ShiftTime.SHIFT_8_16);

        shifts.add(s1);
        shifts.add(s2);

        return shifts;
    }
}
