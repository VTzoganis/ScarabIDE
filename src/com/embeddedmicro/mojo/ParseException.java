package com.embeddedmicro.mojo;

public class ParseException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ParseException() {}
	
	public ParseException(String message){
		super(message);
	}

}
