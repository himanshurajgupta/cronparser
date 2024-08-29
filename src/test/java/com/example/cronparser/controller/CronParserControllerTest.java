package com.example.cronparser.controller;

import com.example.cronparser.factory.CronFieldParserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@WebMvcTest(CronParserController.class)
class CronParserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CronFieldParserManager cronFieldParserManager;

    @BeforeEach
    void setUp() {
        when(cronFieldParserManager.parse(anyString())).thenReturn("Parsed output");
    }

    @Test
    void parseCronExpressionReturnsParsedOutput() throws Exception {
        mockMvc.perform(get("/parse")
                        .param("expression", "*/15 0 1,15 * 1-5 /usr/bin/find"))
                .andExpect(status().isOk())
                .andExpect(content().string("Parsed output"));
    }

    @Test
    void parseCronExpressionHandlesException() throws Exception {
        when(cronFieldParserManager.parse(anyString())).thenThrow(new RuntimeException("Error message"));

        mockMvc.perform(get("/parse")
                        .param("expression", "bad expression"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Error message"));
    }
}
