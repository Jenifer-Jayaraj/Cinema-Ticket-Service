package uk.gov.dwp.uc.pairtest.util;

import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest.Type;

public class TicketServiceTestUtil {

	public static TicketPurchaseRequest getTicketPurchaseRequest_failiure() {
		return TicketPurchaseRequest.builder().accountId(-123)
				.ticketRequests(new TicketRequest[] { TicketRequest.builder().noOfTickets(10).type(Type.CHILD).build(),
						TicketRequest.builder().noOfTickets(5).type(Type.INFANT).build(),
						TicketRequest.builder().noOfTickets(20).type(Type.ADULT).build() })
				.build();
	}
	
	public static TicketPurchaseRequest getTicketPurchaseRequest_TicketCountExceed_Exception() {
		return TicketPurchaseRequest.builder().accountId(123)
				.ticketRequests(new TicketRequest[] { TicketRequest.builder().noOfTickets(20).type(Type.ADULT).build(),
						TicketRequest.builder().noOfTickets(5).type(Type.INFANT).build() })
				.build();
	}
	
	public static TicketPurchaseRequest getTicketPurchaseRequest_InvalidAccountId_Exception() {
		return TicketPurchaseRequest.builder().accountId(-123)
				.ticketRequests(new TicketRequest[] { TicketRequest.builder().noOfTickets(20).type(Type.ADULT).build(),
						TicketRequest.builder().noOfTickets(5).type(Type.INFANT).build() })
				.build();
	}
	
	public static TicketPurchaseRequest getTicketPurchaseRequest_WithoutAdult_Exception() {
		return TicketPurchaseRequest.builder().accountId(123)
				.ticketRequests(new TicketRequest[] { TicketRequest.builder().noOfTickets(10).type(Type.CHILD).build(),
						TicketRequest.builder().noOfTickets(5).type(Type.INFANT).build() })
				.build();
	}
	
	public static TicketPurchaseRequest getTicketPurchaseRequest_Success() {
		
		return TicketPurchaseRequest.builder().accountId(123)
				.ticketRequests(new TicketRequest[] { TicketRequest.builder().noOfTickets(10).type(Type.ADULT).build(),
						TicketRequest.builder().noOfTickets(5).type(Type.CHILD).build(),
						TicketRequest.builder().noOfTickets(5).type(Type.INFANT).build() })
				.build();
	}
}
