package com.work.planningservice.service;

import com.work.planningservice.model.Worker;
import com.work.planningservice.model.WorkerException;
import com.work.planningservice.repository.WorkerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkerServiceTest {

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
    public void test_findAll() {
        //given
        Mockito.when(workerRepository.findAll()).thenReturn(Arrays.asList(new Worker(1L, "John", null), new Worker(2L, "Mary", null)));

        //when
        List<Worker> resultList = workerService.getAllWorkers();

        //then
        assertEquals(resultList.size(), 2);
        assertTrue(resultList.get(0).getName().equals("John"));
        assertTrue(resultList.get(1).getName().equals("Mary"));
    }

    @Test
    public void test_getById_success() {
        //given
        Mockito.when(workerRepository.findByWorkerId(1L)).thenReturn(Optional.of(new Worker(1L, "John", null)));

        //when
        Worker result = workerService.getByID(1L);

        //then
        assertEquals("John", result.getName());
        assertEquals(1L, result.getWorkerId());
    }

    @Test
    public void test_getById_workerNotFound() {
        //given
        Mockito.when(workerRepository.findByWorkerId(1L)).thenReturn(Optional.empty());

        //when
        try {
            workerService.getByID(1L);
        } catch (WorkerException we) {
            //then
            assertEquals("The worker with id 1 was not found", we.getMessage());
        }
    }

}