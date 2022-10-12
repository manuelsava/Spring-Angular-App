package com.manuelsava.demo.university;

import com.manuelsava.demo.university.dto.UniversityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UniversityControllerTest {
    private UniversityController underTest;
    private UniversityService universityService = mock(UniversityService.class);
    private UniversityRepository universityRepository = mock(UniversityRepository.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        underTest = new UniversityController(universityService);
    }

    @Test
    void itShouldSendUniversity() throws Exception {
        University university = new University("UNI", "VIA UNI", "UNIMIB");
        UniversityDTO universityDTO = modelMapper.map(university, UniversityDTO.class);

        ResultActions send = mockMvc.perform(post("/api/v1/university/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(universityDTO)));

        send.andExpect(status().isCreated());
    }

    @Test
    void itShouldNotSaveUniWhenNameIsNull() throws Exception {
        University university = new University(null, "VIA UNI", "UNIMI");
        UniversityDTO universityDTO = modelMapper.map(university, UniversityDTO.class);

        ResultActions send = mockMvc.perform(post("/api/v1/university/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(universityDTO)));

        send.andExpect(status().is(400));
    }

    @Test
    void itShouldNotSaveUniWhenSigIsTooBig() throws Exception {
        University university = new University(null, "VIA UNI", "UNIMIIIIII");
        UniversityDTO universityDTO = modelMapper.map(university, UniversityDTO.class);

        ResultActions send = mockMvc.perform(post("/api/v1/university/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(universityDTO)));

        send.andExpect(status().is(400));
    }

    @Test
    void itShouldNotSaveUniWhenSigIsNull() throws Exception {
        University university = new University(null, "VIA UNI", null);
        UniversityDTO universityDTO = modelMapper.map(university, UniversityDTO.class);

        ResultActions send = mockMvc.perform(post("/api/v1/university/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(universityDTO)));

        send.andExpect(status().is(400));
    }

    private String objectToJson(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}