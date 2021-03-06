package com.markoni.interv.tender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markoni.interv.api.tender.command.CreateTenderCommand;
import com.markoni.interv.api.tender.model.Tender;
import com.markoni.interv.api.tender.model.TenderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
class TenderEndpointTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    private String refNo;

    @DisplayName("Test creation of new tender")
    @Test
    @Order(1)
    void testCreate() throws Exception {
        CreateTenderCommand command = CreateTenderCommand
            .builder()
            .issuerIdNumber("CIN112233")
            .description("Tender for new project")
            .deadline(LocalDate.of(2020, 9, 1))
            .build();

        MvcResult res = mockMvc
            .perform(post("/api/v1/tenders").content(objectMapper.writeValueAsString(command)).contentType("application/json"))
            .andExpect(status().isOk())
            .andDo(document("create-tender", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .andReturn();

        Tender result = objectMapper.readValue(res.getResponse().getContentAsString(), Tender.class);
        assertNotNull(result.getIssuer());
        assertEquals(result.getIssuer().getIdentificationNumber(), "CIN112233");
        assertNotNull(result.getReferenceNumber());
        assertEquals(result.getDescription(), "Tender for new project");
        assertEquals(result.getCreationDate(), LocalDate.now());
        assertEquals(result.getStatus(), TenderStatus.OPENED);

        refNo = result.getReferenceNumber();
    }

    @DisplayName("Test retrieval of existing tender")
    @Test
    @Order(2)
    void testGet() throws Exception {
        MvcResult res = mockMvc
            .perform(get("/api/v1/tenders/"+refNo))
            .andExpect(status().isOk())
            .andDo(document("get-tender", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .andReturn();

        Tender result = objectMapper.readValue(res.getResponse().getContentAsString(), Tender.class);
        assertNotNull(result);
        assertEquals(result.getReferenceNumber(), refNo);
        assertNotNull(result.getIssuer());
        assertNotNull(result.getDeadline());
        assertNotNull(result.getDescription());
        assertEquals(result.getCreationDate(), LocalDate.now());
    }

    @DisplayName("Test search of tenders")
    @Test
    @Order(3)
    void testSearch() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/api/v1/tenders/search")
            .param("pageNumber", "1")
            .param("pageSize", "5")
            .param("issuerIdNo", "CIN112233");

        mockMvc
            .perform(getRequest)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].issuer.identificationNumber").value("CIN112233"))
            .andDo(document("search-tenders", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .andReturn();
    }
}