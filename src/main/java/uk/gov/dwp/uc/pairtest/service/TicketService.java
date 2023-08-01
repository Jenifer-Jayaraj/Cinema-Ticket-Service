package uk.gov.dwp.uc.pairtest.service;

import org.springframework.stereotype.Service;

import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

@Service
public interface TicketService {

    void purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException;

}
