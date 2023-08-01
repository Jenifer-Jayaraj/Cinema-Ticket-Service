package uk.gov.dwp.uc.pairtest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Should be an Immutable Object
 */
@AllArgsConstructor
@Data
@Builder
public final class TicketRequest {

    private final int noOfTickets;
    private final Type type;


    public enum Type {
        ADULT, CHILD , INFANT
    }

}
