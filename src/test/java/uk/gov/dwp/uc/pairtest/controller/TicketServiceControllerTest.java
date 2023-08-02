package uk.gov.dwp.uc.pairtest.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.ResponseMessage;
import uk.gov.dwp.uc.pairtest.util.TicketServiceTestUtil;

@WebMvcTest(TicketServiceController.class)
@Slf4j
public class TicketServiceControllerTest {
	@Autowired
	private MockMvc mockMcv;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void bookTicketTest_Success() throws Exception {
		TicketPurchaseRequest ticketPurchaseRequest = TicketServiceTestUtil.getTicketPurchaseRequest_Success();
		String ticketPurchaseRequestString = objectMapper.writeValueAsString(ticketPurchaseRequest);
		MvcResult mvcresult = mockMcv.perform(MockMvcRequestBuilders.post("/api/ticket")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ticketPurchaseRequestString)).andExpect(status().isCreated()).andReturn();
		log.info("--------------------"+mvcresult);
		assertTrue(mvcresult.getResponse().getContentAsString().contains(ResponseMessage.SUCCESS_RESPONSE));
	}

	
	@Test
	void bookTicketTest_failiure() throws Exception {
		TicketPurchaseRequest ticketPurchaseRequest = TicketServiceTestUtil.getTicketPurchaseRequest_failiure();
		String ticketPurchaseRequestString = objectMapper.writeValueAsString(ticketPurchaseRequest);
		MvcResult mvcresult = mockMcv.perform(MockMvcRequestBuilders.post("/api/ticket")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ticketPurchaseRequestString)).andExpect(status().is4xxClientError()).andReturn();
		assertFalse(mvcresult.getResponse().getContentAsString().contains(ResponseMessage.SUCCESS_RESPONSE));
	}

}
