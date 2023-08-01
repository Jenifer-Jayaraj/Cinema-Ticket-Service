package uk.gov.dwp.uc.pairtest.service;

import java.util.Arrays;
import java.util.stream.Collectors;

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
	
	private static final int MAX_ALLOWED_TICKET_COUNT=20;
	
	
	/**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException {
    	
    	long accountId=ticketPurchaseRequest.getAccountId();
    	if(accountId>0)
    	{
    		TicketRequest[] ticketRequest=ticketPurchaseRequest.getTicketRequests();
    		int count=Arrays.stream(ticketRequest).mapToInt(t->t.getNoOfTickets()).sum();
    		if(count>MAX_ALLOWED_TICKET_COUNT)
    			throw new InvalidPurchaseException(ResponseMessage.TICKET_COUNT_EXCEED);
    		else {
    			long ticketsWithoutAdult=Arrays.stream(ticketRequest).filter(t->t.getType()==Type.ADULT).count();
    			if(ticketsWithoutAdult==0)
    				throw new InvalidPurchaseException(ResponseMessage.WITHOUT_ADULT_TICKET);
    			else
    			{
    				int adultCount=Arrays.stream(ticketRequest).filter(t->t.getType()==Type.ADULT).collect(Collectors.summingInt(TicketRequest::getNoOfTickets));
    				int childCount=Arrays.stream(ticketRequest).filter(t->t.getType()==Type.CHILD).collect(Collectors.summingInt(TicketRequest::getNoOfTickets));
    				int totalTicketCount=adultCount+childCount;
    				
    				int totalTicketPrice=(adultCount*TicketPrice.AdULT)+(childCount*TicketPrice.CHILD);
    				ticketPaymentService.makePayment(accountId, totalTicketPrice);
    				log.info("Total Ticket price is {}",totalTicketPrice);
    				log.info("payment made for the account id {}",accountId);
    				seatReservationService.reserveSeat(accountId, totalTicketCount);
    				log.info("{} seats allocated for the account id {}",totalTicketCount,accountId);
    			}
    		}
    		
    	}else {
    		throw new InvalidPurchaseException(ResponseMessage.INVALID_ACCOUNT_ID);
    	}

    }


	
}
