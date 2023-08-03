package uk.gov.dwp.uc.pairtest.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.ResponseMessage;
import uk.gov.dwp.uc.pairtest.util.TicketServiceTestUtil;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

	@Mock
	private TicketServiceImpl ticketService;

	@InjectMocks
	private TicketPaymentServiceImpl ticketPaymentService;

	@InjectMocks
	private SeatReservationServiceImpl seatReservationService;

	@Test
	void purchaseTickets_success() throws Exception {
		ticketPaymentService.makePayment(Mockito.anyLong(), 10);
		seatReservationService.reserveSeat(123456, 10);
		assertDoesNotThrow(
				() -> ticketService.purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_Success()));
		Mockito.verify(ticketService, Mockito.times(1)).purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_Success());
	}

	@Test
	void purchaseTickets_InvalidPurchaseException_TicketCountExceed() throws InvalidPurchaseException {
		ticketPaymentService.makePayment(1212, 100);
		seatReservationService.reserveSeat(1212, 10);
		doThrow(InvalidPurchaseException.class).when(ticketService).purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_TicketCountExceed_Exception());
		assertThrows(InvalidPurchaseException.class, () ->ticketService
				.purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_TicketCountExceed_Exception()));
	}

	@Test
	void purchaseTickets_InvalidPurchaseException_InvalidAccountid() throws InvalidPurchaseException {
		ticketPaymentService.makePayment(1212, 100);
		seatReservationService.reserveSeat(1212, 10);
		doThrow(InvalidPurchaseException.class).when(ticketService).purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_InvalidAccountId_Exception());
		assertThrows(InvalidPurchaseException.class, () ->ticketService
				.purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_InvalidAccountId_Exception()));
		
	}

	@Test
	void purchaseTickets_InvalidPurchaseException_withoutAdult() throws InvalidPurchaseException {
		ticketPaymentService.makePayment(1212, 100);
		seatReservationService.reserveSeat(1212, 10);
		doThrow(InvalidPurchaseException.class).when(ticketService).purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_WithoutAdult_Exception());
		assertThrows(InvalidPurchaseException.class, () ->ticketService
				.purchaseTickets(TicketServiceTestUtil.getTicketPurchaseRequest_WithoutAdult_Exception()));
	}

}
