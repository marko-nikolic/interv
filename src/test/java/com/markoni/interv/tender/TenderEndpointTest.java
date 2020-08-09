package com.markoni.interv.tender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markoni.interv.api.tender.CreateTenderCommand;
import com.markoni.interv.api.tender.Tender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
class TenderEndpointTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Test creation of new tender")
    @Test
    void testCreate() throws Exception {
        CreateTenderCommand command = new CreateTenderCommand("Tender for new project", LocalDate.of(2020, 9, 1));
        MvcResult res = mockMvc
            .perform(post("/tenders").content(objectMapper.writeValueAsString(command)).contentType("application/json"))
            .andExpect(status().isOk())
            .andReturn();

        Tender result = objectMapper.readValue(res.getResponse().getContentAsString(), Tender.class);
        assertNotNull(result);
        assertNotNull(result.getReferenceNumber());
        assertEquals(result.getDescription(), "Tender for new project");
        assertEquals(result.getCreationDate(), LocalDate.now());
    }
}