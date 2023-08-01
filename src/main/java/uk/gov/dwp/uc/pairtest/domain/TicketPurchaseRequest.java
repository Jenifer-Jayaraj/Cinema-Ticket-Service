package uk.gov.dwp.uc.pairtest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Should be an Immutable Object
 */
@Data
@Builder
@AllArgsConstructor
public final class TicketPurchaseRequest {

    private final long accountId;
    private final TicketRequest[] ticketRequests;


}
