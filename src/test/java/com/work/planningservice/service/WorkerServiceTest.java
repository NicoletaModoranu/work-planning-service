package com.work.planningservice.service;

import com.work.planningservice.model.Worker;
import com.work.planningservice.model.WorkerException;
import com.work.planningservice.repository.WorkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class WorkerServiceTest {

    @Mock
    private WorkerRepository workerRepository;

    @Mock
    private WorkerService workerService;

    @BeforeEach
    void initService() {
        workerRepository = Mockito.mock(WorkerRepository.class);
        workerService = new WorkerService(workerRepository);
    }

    @Test
    void test_findAll() {
        //given
        when(workerRepository.findAll()).thenReturn(Arrays.asList(new Worker(1L, "John", null), new Worker(2L, "Mary", null)));

        //when
        List<Worker> resultList = workerService.getAllWorkers();

        //then
        assertEquals(2, resultList.size());
        assertEquals("John", resultList.get(0).getName());
        assertEquals("Mary", resultList.get(1).getName());
    }

    @Test
    void test_getById_success() {
        //given
        when(workerRepository.findByWorkerId(1L)).thenReturn(Optional.of(new Worker(1L, "John", null)));

        //when
        Worker result = workerService.getByID(1L);

        //then
        assertEquals("John", result.getName());
        assertEquals(1L, result.getWorkerId());
    }

    @Test
    void test_getById_workerNotFound() {
        //given
        when(workerRepository.findByWorkerId(1L)).thenReturn(Optional.empty());

        //when
        try {
            workerService.getByID(1L);
        } catch (WorkerException we) {
            //then
            assertEquals("The worker with id 1 was not found", we.getMessage());
        }
    }

    @Test
    void test_saveWorker_success() {
        //given
        Worker worker = new Worker();
        worker.setWorkerId(1L);
        worker.setName("Pippin");

        when(workerRepository.save(worker)).thenReturn(worker);

        //when
        Worker result = workerService.save(worker);

        //then
        assertEquals(1L, result.getWorkerId());
        assertEquals("Pippin", result.getName());
    }

    @Test
    void test_saveWorker_throwsException() {
        //given
        Worker worker = new Worker();
        worker.setWorkerId(1L);

        when(workerRepository.save(worker)).thenReturn(worker);

        //when
        try {
            workerService.save(worker);
        } catch (WorkerException we) {
            //then
            assertEquals("Invalid worker input", we.getMessage());
        }
    }

}