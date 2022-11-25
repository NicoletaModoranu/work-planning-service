package com.work.planningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.planningservice.dto.WorkerDTO;
import com.work.planningservice.mapper.WorkerMapper;
import com.work.planningservice.model.Worker;
import com.work.planningservice.service.WorkerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkerController.class)
class WorkerControllerTest {

    @MockBean
    private WorkerService workerService;

    @MockBean
    private WorkerMapper workerMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_findAll() throws Exception {
        List<Worker> workers = Arrays.asList(new Worker(1L, "John", null), new Worker(2L, "Mary", null));

        when(workerService.getAllWorkers()).thenReturn(workers);
        mockMvc.perform(get("/worker"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(workers.size()))
                .andDo(print());
    }


    @Test
    void test_getById_success() throws Exception {
        //given
        long id = 1L;
        Worker expectedWorker = new Worker(id, "John", null);
        WorkerDTO expectedWorkerDto = new WorkerDTO(id, "John");
        when(workerService.getByID(1L)).thenReturn(expectedWorker);
        when(workerMapper.workerToWorkerDTO(expectedWorker)).thenReturn(expectedWorkerDto);

        //when; then
        mockMvc.perform(get("/worker/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.workerId").value(id))
                .andExpect(jsonPath("$.name").value(expectedWorker.getName()))
                .andDo(print());
    }


    @Test
    void test_save() throws Exception {
        //given
        long id = 1L;
        Worker expectedWorker = new Worker(id, "John", null);
        WorkerDTO expectedWorkerDto = new WorkerDTO(id, "John");
        when(workerService.save(expectedWorker)).thenReturn(expectedWorker);
        when(workerMapper.workerDtoTOWorker(expectedWorkerDto)).thenReturn(expectedWorker);

        //when; then
        mockMvc.perform(post("/worker").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedWorkerDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

}
