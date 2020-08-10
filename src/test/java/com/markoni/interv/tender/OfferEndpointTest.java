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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markoni.interv.api.tender.command.CreateOfferCommand;
import com.markoni.interv.api.tender.command.UpdateOfferCommand;
import com.markoni.interv.api.tender.model.Offer;
import com.markoni.interv.api.tender.model.OfferStatus;
import com.markoni.interv.core.tender.offer.OfferEntity;
import com.markoni.interv.core.tender.offer.OfferRepository;
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

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
public class OfferEndpointTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OfferRepository offerRepository;

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

    @DisplayName("Test creation of new offer")
    @Test
    @Order(1)
    void testCreate() throws Exception {
        CreateOfferCommand command = CreateOfferCommand
            .builder()
            .tenderReferenceNumber("0001")
            .bidderIdNumber("SB4435566")
            .note("We provide best prices for the quality")
            .build();

        MvcResult res = mockMvc
            .perform(post("/api/v1/offers").content(objectMapper.writeValueAsString(command)).contentType("application/json"))
            .andExpect(status().isOk())
            .andDo(document("create-offer", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .andReturn();

        Offer result = objectMapper.readValue(res.getResponse().getContentAsString(), Offer.class);
        assertNotNull(result.getBidder());
        assertEquals(result.getBidder().getIdentificationNumber(), "SB4435566");
        assertNotNull(result.getTender());
        assertEquals(result.getTender().getReferenceNumber(), "0001");
        assertEquals(result.getNote(), "We provide best prices for the quality");
        assertEquals(result.getStatus(), OfferStatus.SUBMITTED);
    }

    @DisplayName("Test update of the existing offer")
    @Test
    @Order(2)
    void testUpdate() throws Exception {
        UpdateOfferCommand command = new UpdateOfferCommand(OfferStatus.ACCEPTED);

        MvcResult res = mockMvc
            .perform(put("/api/v1/offers/{refNumber}", "123").content(objectMapper.writeValueAsString(command)).contentType("application/json"))
            .andExpect(status().isOk())
            .andDo(document("update-offer", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .andReturn();

        Offer result = objectMapper.readValue(res.getResponse().getContentAsString(), Offer.class);
        assertNotNull(result.getBidder());
        assertEquals(result.getBidder().getIdentificationNumber(), "RE32333");
        assertNotNull(result.getTender());
        assertEquals(result.getTender().getReferenceNumber(), "0001");
        assertEquals(result.getStatus(), OfferStatus.ACCEPTED);

        List<OfferEntity> tenderOffers = offerRepository.findByTenderReferenceNumber("0001");
        assertEquals(tenderOffers.size(), 3);
        tenderOffers
            .stream()
            .filter(o -> !o.getReferenceNumber().equals("123"))
            .forEach(s -> assertEquals(s.getStatus(), OfferStatus.REJECTED));
    }

    @DisplayName("Test search of offers")
    @Test
    @Order(3)
    void testSearch() throws Exception {
        MockHttpServletRequestBuilder getRequest = get("/api/v1/offers/search")
            .param("pageNumber", "1")
            .param("pageSize", "5")
            .param("bidderIdNo", "RE32333");

        mockMvc
            .perform(getRequest)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].bidder.identificationNumber").value("RE32333"))
            .andDo(document("search-offers", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .andReturn();
    }
}
