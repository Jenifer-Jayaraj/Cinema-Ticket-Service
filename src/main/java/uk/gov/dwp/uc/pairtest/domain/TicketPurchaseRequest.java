package uk.gov.dwp.uc.pairtest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
/**
 * Should be an Immutable Object
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public final class TicketPurchaseRequest {

	private final long accountId;
	
	@Valid
	@NotNull(message = "Atleast one adult ticket needed")
    private final TicketRequest[] ticketRequests;


}
