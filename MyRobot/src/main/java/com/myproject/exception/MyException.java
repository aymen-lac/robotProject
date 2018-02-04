package com.myproject.exception;

public class MyException extends Exception {

	private final String description;

	private final String details;
	
	public String getDescription() {
		return description;
	}
	
	public String getDetails() {
		return details;
	}
	
	public MyException(String description, String details) {
		super();
		this.description = description;
		this.details = details;
	}
	
	
}
