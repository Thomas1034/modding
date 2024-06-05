package com.thomas.verdant.util.modfeature.parameters;

import com.google.gson.JsonElement;

public abstract class Parameter<T> {

	protected final String field;

	public Parameter(String field) {
		this.field = field;
	}

	public abstract T readFrom(JsonElement parameters) throws MissingFieldException, FieldMismatchException;

	protected MissingFieldException missingField() {
		return new MissingFieldException(this.field);
	}

	protected FieldMismatchException fieldMismatch() {
		return new FieldMismatchException(this.field);
	}
}
