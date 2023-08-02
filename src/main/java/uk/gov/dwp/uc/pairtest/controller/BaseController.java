package uk.gov.dwp.uc.pairtest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import uk.gov.dwp.uc.pairtest.exception.ResponseMessage;

@Component
public class BaseController {
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public @ResponseBody String invalidJSONRequestHandler(Exception ex) {
		
		return ResponseMessage.INVALID_REQUEST;
	}

}
