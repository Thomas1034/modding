package com.thomas.verdant.util.modfeature.parameters;

public class MissingFieldException extends Exception {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -5477326785309041343L;
	
	
	public MissingFieldException(String field) {
		super("Missing required field: " + field);
	}
	
}
