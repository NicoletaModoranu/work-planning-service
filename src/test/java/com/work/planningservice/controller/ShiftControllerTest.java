package com.work.planningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.planningservice.dto.ShiftDTO;
import com.work.planningservice.dto.WorkerDTO;
import com.work.planningservice.mapper.ShiftMapper;
import com.work.planningservice.mapper.ValidationException;
import com.work.planningservice.mapper.WorkerMapper;
import com.work.planningservice.model.Shift;
import com.work.planningservice.model.ShiftException;
import com.work.planningservice.model.ShiftTime;
import com.work.planningservice.model.Worker;
import com.work.planningservice.service.ShiftService;
import org.apache.el.util.Validation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShiftController.class)
@RunWith(SpringRunner.class)
public class ShiftControllerTest {


    @MockBean
    private ShiftService shiftService;

    @MockBean
    private ShiftMapper shiftMapper;

    @MockBean
    private WorkerMapper workerMapper;


    @Autowired
    private ShiftController shiftController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


//    @Before
//    public void beforeTest() {
//        MockMvc mockMvc = standaloneSetup(shiftController)
//                .setControllerAdvice(new RestResponseEntityExceptionHandler())
//                .build();
//    }


    @Test
    void test_getById_success() throws Exception {
        //given
        long id = 1L;
        Worker expectedWorker = new Worker(id, "John", null);
        WorkerDTO expectedWorkerDto = new WorkerDTO(id, "John");

        Shift expectedShift = new Shift(1L, expectedWorker, LocalDate.of(2022, 11, 28), ShiftTime.SHIFT_0_8);
        ShiftDTO expectedShiftDto = new ShiftDTO(1L, expectedWorkerDto, "28-11-2022", ShiftTime.SHIFT_0_8);

        Set<Shift> expectedSet = new HashSet<>();
        expectedSet.add(expectedShift);

        when(shiftService.getShifts(1L, "01-11-2022", "30-11-2022")).thenReturn(expectedSet);
        when(shiftMapper.shiftToShiftDTO(expectedShift)).thenReturn(expectedShiftDto);
        when(workerMapper.workerToWorkerDTO(expectedWorker)).thenReturn(expectedWorkerDto);

        //when; then
        mockMvc.perform(get("/shift")
                        .param("workerId", "1")
                        .param("dateStart", "01-11-2022")
                        .param("dateEnd", "30-11-2022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shiftId").value(id))
                .andExpect(jsonPath("$[0].shiftDay").value("28-11-2022"))
                .andDo(print());
    }


    @Test
    void test_saveShift() throws Exception {
        //given
        Shift expectedShift = new Shift();
        ShiftDTO expectedShiftDto = new ShiftDTO();
        when(shiftService.save(expectedShift)).thenReturn(expectedShift);
        when(shiftMapper.shiftDtoToShift(expectedShiftDto)).thenReturn(expectedShift);

        //when; then
        mockMvc.perform(post("/shift").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedShiftDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void test_saveShift_throwsException() throws Exception {
        //given
        ShiftDTO expectedShiftDto = new ShiftDTO();
        when(shiftMapper.shiftDtoToShift(any())).thenThrow(ValidationException.class);

        //when; then
        mockMvc.perform(post("/shift").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expectedShiftDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }
}
