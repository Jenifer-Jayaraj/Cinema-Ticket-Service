/**
 * 
 */
package uk.gov.dwp.uc.pairtest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.ResponseMessage;
import uk.gov.dwp.uc.pairtest.service.TicketService;

/**
 * @author jenifer
 *
 */
@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor
public class TicketServiceController extends BaseController{

	private final TicketService ticketService;	

	/**
	 * This controller method is used for book the cinema tickets
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String bookTicket(@Valid @RequestBody TicketPurchaseRequest ticketPurchaseRequest) {
		try {
			ticketService.purchaseTickets(ticketPurchaseRequest);
			
		} catch (InvalidPurchaseException exception) {
			return exception.getMessage();
		}
		 
		
		return ResponseMessage.SUCCESS_RESPONSE;
	}
	
	

}
