/**
 * 
 */
package uk.gov.dwp.uc.pairtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.ResponseMessage;
import uk.gov.dwp.uc.pairtest.service.TicketService;
import uk.gov.dwp.uc.pairtest.service.TicketServiceImpl;

/**
 * @author jeni
 *
 */
@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketServiceController {

	private final TicketService ticketService;	

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String bookTicket(@RequestBody TicketPurchaseRequest ticketPurchaseRequest) {
		try {
			ticketService.purchaseTickets(ticketPurchaseRequest);
			
		} catch (InvalidPurchaseException exception) {
			System.out.println("Error: " + exception.getMessage());
			return exception.getMessage();
		}
		/*
		 * catch (HttpMessageNotReadableException exception) { System.out.println();
		 * System.out.println("Error: " + exception.getMessage()); return
		 * ResponseMessage.BAD_REQUEST; }
		 */
		
		return ResponseMessage.SUCCESS_RESPONSE;
	}

}
