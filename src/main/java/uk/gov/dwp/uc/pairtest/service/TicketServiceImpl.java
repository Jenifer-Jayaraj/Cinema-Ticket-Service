package uk.gov.dwp.uc.pairtest.service;

import java.util.Arrays;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.constants.TicketPrice;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.ResponseMessage;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

	private final TicketPaymentService ticketPaymentService;

	private final SeatReservationService seatReservationService;

	private static final int MAX_ALLOWED_TICKET_COUNT = 20;

	/**
	 * 
	 * purchaseTicket method will validate and book the tickets
	 */
	@Override
	public void purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException {

		long accountId = ticketPurchaseRequest.getAccountId();
		if (accountId > 0) {
			TicketRequest[] ticketRequest = ticketPurchaseRequest.getTicketRequests();
			int count = Arrays.stream(ticketRequest).mapToInt(t -> t.getNoOfTickets()).sum();
			if (count > MAX_ALLOWED_TICKET_COUNT)
				throw new InvalidPurchaseException(ResponseMessage.TICKET_COUNT_EXCEED);
			else {
				long ticketsWithoutAdult = Arrays.stream(ticketRequest).filter(t -> t.getType() == Type.ADULT).count();

				if (ticketsWithoutAdult == 0)
					throw new InvalidPurchaseException(ResponseMessage.WITHOUT_ADULT_TICKET);
				else {
					int[] ticketcounts = new int[3]; // Index 0: adultCount, Index 1: childCount, Index 2: infantCount
					Arrays.stream(ticketRequest).forEach(t -> {
						if (t.getType() == Type.ADULT)
							ticketcounts[0]=ticketcounts[0]+t.getNoOfTickets();
						else if (t.getType() == Type.CHILD)
							ticketcounts[1]=ticketcounts[1]+t.getNoOfTickets();
						else if (t.getType() == Type.INFANT)
							ticketcounts[2]=ticketcounts[2]+t.getNoOfTickets();

					});

					int adultCount = ticketcounts[0];
					int childCount = ticketcounts[1];
					int infantCount = ticketcounts[2];
					log.info("Total Ticket Count is {}", adultCount+childCount+infantCount);
					int totalTicketPayCount = adultCount + childCount;
					int totalTicketPrice = (adultCount * TicketPrice.AdULT) + (childCount * TicketPrice.CHILD);
					log.info("Total Ticket Price is {}", totalTicketPrice);
					
					//Make the payment for only adult and child tickets calling third party payment resource
					ticketPaymentService.makePayment(accountId, totalTicketPrice);
					log.info("payment made for the account id {}", accountId);
					
					//Reserve the seats calling third party reservation service
					seatReservationService.reserveSeat(accountId, totalTicketPayCount);
					log.info("{} seats allocated for the account id {}", totalTicketPayCount, accountId);
				}
			}

		} else {
			throw new InvalidPurchaseException(ResponseMessage.INVALID_ACCOUNT_ID);
		}

	}

}
