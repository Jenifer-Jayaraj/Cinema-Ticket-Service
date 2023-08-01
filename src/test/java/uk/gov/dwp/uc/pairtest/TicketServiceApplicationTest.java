/**
 * 
 */
package uk.gov.dwp.uc.pairtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.gov.dwp.uc.pairtest.controller.TicketServiceController;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest.Type;
import uk.gov.dwp.uc.pairtest.util.TicketServiceTestUtil;

/**
 * @author jenifer
 *
 */
@WebMvcTest(TicketServiceController.class)
class TicketServiceApplicationTest {

	@Autowired
	private MockMvc mockMcv;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	private void bookTicketTest_Success() throws Exception {
		TicketPurchaseRequest ticketPurchaseRequest = TicketServiceTestUtil.getTicketPurchaseRequest_Success();
		String ticketPurchaseRequestString = objectMapper.writeValueAsString(ticketPurchaseRequest);
		MvcResult mvcresult = mockMcv.perform(MockMvcRequestBuilders.post("/api/ticket").header("X-Trace-Id", "123456")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(ticketPurchaseRequestString)).andExpect(status().isCreated()).andReturn();
		assertTrue(mvcresult.getResponse().getContentAsString().contains("booked successfully"));
	}

	
	@Test
	private void bookTicketTest_failiure() throws Exception {
		TicketPurchaseRequest ticketPurchaseRequest = TicketServiceTestUtil.getTicketPurchaseRequest_failiure();
		String ticketPurchaseRequestString = objectMapper.writeValueAsString(ticketPurchaseRequest);
		MvcResult mvcresult = mockMcv.perform(MockMvcRequestBuilders.post("/api/ticket").header("X-Trace-Id", "123456")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(ticketPurchaseRequestString)).andExpect(status().isCreated()).andReturn();
		assertFalse(mvcresult.getResponse().getContentAsString().contains("booked successfully"));
	}

	
	
	

}
