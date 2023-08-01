/**
 * 
 */
package uk.gov.dwp.uc.pairtest.exception;

/**
 * @author jeni
 *
 */
public class ResponseMessage {

	public static final String TICKET_COUNT_EXCEED = "You can book maximum 20 ticket per transaction.Please try with proper request... ";
	public static final String WITHOUT_ADULT_TICKET = "Child or Infant tickets cannot be purchased without purchasing an Adult ticket..  ";
	public static final String INVALID_ACCOUNT_ID = "Invalid account id.Please enter the valid id";
	public static final String SUCCESS_RESPONSE = "Your tickets are booked successfully.Thank You!";
	
	public static final String BAD_REQUEST = """ 
			Please Enter valid request like below
	        {
			"accountId":123,
			"ticketRequests":[
			{
            "noOfTickets":5,
            "type":"CHILD"
			},
			{
            "noOfTickets":5,
            "type":"ADULT"
			},
        {
            "noOfTickets":5,
            "type":"INFANT"
        }
    ]
}
	        """;

}
