package com.example.demo.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyCustomErrorController implements ErrorController  {
 
	@RequestMapping(path = "/error", method = RequestMethod.GET)
	public Map<String,String> handleError(HttpServletRequest request) {
		Map<String, String> errorDetails = new HashMap<>();
		errorDetails.put("time", new Date().toString());
		errorDetails.put("path", request.getRequestURI().toString());
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	errorDetails.put("message", "error-404");
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	errorDetails.put("message", "error-500");
	        }
	    }else {
	    	errorDetails.put("message", "error");
	    }
	   return errorDetails;
	}
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
