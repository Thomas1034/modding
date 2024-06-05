package com.thomas.verdant.util.modfeature.parameters;

public class FieldMismatchException extends Exception {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 891003129672251792L;

	public FieldMismatchException(String field) {
		super("Parameter type mismatch in field: " + field);
	}

}
