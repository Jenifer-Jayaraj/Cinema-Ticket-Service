package uk.gov.dwp.uc.pairtest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.ResponseMessage;
import uk.gov.dwp.uc.pairtest.util.TicketServiceTestUtil;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
	
	@InjectMocks
	private TicketServiceImpl ticketService;
	
	@Mock
	private final TicketPaymentService ticketPaymentService;
	
	@Mock
	private final SeatReservationService seatReservationService;
	
	@Test
	private void purchaseTickets_success()throws Exception
	{
		ticketPaymentService.makePayment(Mockito.anyLong(), Mockito.anyInt());
		seatReservationService.reserveSeat(Mockito.anyLong(), Mockito.anyInt());
		ticketService.purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_Success());
	}
	
	@Test
	private void purchaseTickets_InvalidPurchaseException_TicketCountExceed() throws Exception
	{
		ticketPaymentService.makePayment(Mockito.anyLong(), Mockito.anyInt());
		seatReservationService.reserveSeat(Mockito.anyLong(), Mockito.anyInt());
		InvalidPurchaseException invalidPurchaseException=new InvalidPurchaseException(ResponseMessage.TICKET_COUNT_EXCEED);
		when(ticketService.purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_TicketCountExceed_Exception()))
		.thenThrow(invalidPurchaseException);
		assertEquals(invalidPurchaseException.getMessage(), ResponseMessage.TICKET_COUNT_EXCEED);
	}
	
	@Test
	private void purchaseTickets_InvalidPurchaseException_InvalidAccountid() throws Exception
	{
		ticketPaymentService.makePayment(Mockito.anyLong(), Mockito.anyInt());
		seatReservationService.reserveSeat(Mockito.anyLong(), Mockito.anyInt());
		InvalidPurchaseException invalidPurchaseException=new InvalidPurchaseException(ResponseMessage.INVALID_ACCOUNT_ID);
		when(ticketService.purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_InvalidAccountId_Exception()))
		.thenThrow(invalidPurchaseException);
		assertEquals(invalidPurchaseException.getMessage(), ResponseMessage.INVALID_ACCOUNT_ID);
	}
	
	@Test
	private void purchaseTickets_InvalidPurchaseException_withoutAdult() throws Exception
	{
		ticketPaymentService.makePayment(Mockito.anyLong(), Mockito.anyInt());
		seatReservationService.reserveSeat(Mockito.anyLong(), Mockito.anyInt());
		InvalidPurchaseException invalidPurchaseException=new InvalidPurchaseException(ResponseMessage.WITHOUT_ADULT_TICKET);
		when(ticketService.purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_WithoutAdult_Exception()))
		.thenThrow(invalidPurchaseException);
		assertEquals(invalidPurchaseException.getMessage(), ResponseMessage.WITHOUT_ADULT_TICKET);
	}

}
