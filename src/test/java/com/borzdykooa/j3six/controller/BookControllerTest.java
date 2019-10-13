package com.borzdykooa.j3six.controller;

import com.borzdykooa.j3six.dto.BookRequestDto;
import com.borzdykooa.j3six.dto.PeriodDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BookRequestDto bookRequestDto;

    @Before
    public void init() {
        bookRequestDto = new BookRequestDto();
    }

    @Test
    public void testGetCsv() throws Exception {
        bookRequestDto.setAuthorNames(Collections.singletonList("Arthur Conan Doyle"));
        String json = objectMapper.writeValueAsString(bookRequestDto);

        mockMvc.perform(post("/get-csv")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCsvWithParamException() throws Exception {
        String json = objectMapper.writeValueAsString(bookRequestDto);

        mockMvc.perform(post("/get-csv")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("BookRequestDto is invalid"));
    }

    @Test
    public void testGetCsvWithInvalidPeriodException() throws Exception {
        PeriodDto periodDto = new PeriodDto();
        periodDto.setFrom(1234);
        bookRequestDto.setPeriodDto(periodDto);
        String json = objectMapper.writeValueAsString(bookRequestDto);

        mockMvc.perform(post("/get-csv")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Spring validation failed"));
    }
}
