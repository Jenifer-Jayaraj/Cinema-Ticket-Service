package uk.gov.dwp.uc.pairtest.controller;

import static org.junit.Assert.assertEquals;
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
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
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
		log.info("start of controller success method ");
		TicketPurchaseRequest ticketPurchaseRequest = TicketServiceTestUtil.getTicketPurchaseRequest_Success();
		String ticketPurchaseRequestString = objectMapper.writeValueAsString(ticketPurchaseRequest);
		MvcResult mvcresult = mockMcv.perform(MockMvcRequestBuilders.post("/api/ticket")
				.contentType(MediaType.APPLICATION_JSON)
				.content(ticketPurchaseRequestString)).andExpect(status().isCreated()).andReturn();
		assertTrue(mvcresult.getResponse().getContentAsString().contains(ResponseMessage.SUCCESS_RESPONSE));
		log.info("end of controller success metho");
	}

	
	@Test
	void bookTicketTest_failiure() throws Exception {
		log.info("start of controller exception method ");
		TicketPurchaseRequest ticketPurchaseRequest = TicketServiceTestUtil.getTicketPurchaseRequest_failiure();
		String ticketPurchaseRequestString = objectMapper.writeValueAsString(ticketPurchaseRequest);
		MvcResult mvcresult=mockMcv.perform(MockMvcRequestBuilders.post("/api/ticket")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content(ticketPurchaseRequestString))
		.andReturn();
		assertTrue(mvcresult.getResponse().getContentAsString().contains(ResponseMessage.INVALID_ACCOUNT_ID));
		log.info("end of controller exception method ");	      
	}

}
