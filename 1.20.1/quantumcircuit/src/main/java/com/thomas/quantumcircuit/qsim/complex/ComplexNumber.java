package com.thomas.quantumcircuit.qsim.complex;

/**
 * Represents a complex number with real and imaginary parts. With the gracious
 * assistance of ChatGPT.
 */
public class ComplexNumber {

	private final double real;
	private final double imag;

	/**
	 * Constructs a complex number with given real and imaginary parts.
	 *
	 * @param real The real part of the complex number.
	 * @param imag The imaginary part of the complex number.
	 */
	public ComplexNumber(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	/**
	 * Constructs a duplicate complex number.
	 * 
	 * @param comp The complex number to duplicate.
	 */
	public ComplexNumber(ComplexNumber comp) {
		this(comp.real, comp.imag);
	}
	

	/**
	 * Gets the real part of the complex number.
	 *
	 * @return The real part.
	 */
	public double real() {
		return this.real;
	}

	/**
	 * Gets the imaginary part of the complex number.
	 *
	 * @return The imaginary part.
	 */
	public double imag() {
		return this.imag;
	}

	/**
	 * Calculates the magnitude (modulus) of the complex number.
	 *
	 * @return The magnitude of the complex number.
	 */
	public double mag() {
		return Math.sqrt(this.real * this.real + this.imag * this.imag);
	}

	/**
	 * Adds another complex number to this complex number.
	 *
	 * @param that The complex number to be added.
	 * @return The result of the addition.
	 */
	public ComplexNumber add(ComplexNumber that) {
		return new ComplexNumber(this.real + that.real, this.imag + that.imag);
	}

	/**
	 * Adds a real value to the real part of this complex number.
	 *
	 * @param val The real value to be added.
	 * @return The result of the addition.
	 */
	public ComplexNumber add(double val) {
		return new ComplexNumber(this.real + val, this.imag);
	}

	/**
	 * Adds an imaginary value to the imaginary part of this complex number.
	 *
	 * @param val The imaginary value to be added.
	 * @return The result of the addition.
	 */
	public ComplexNumber iadd(double val) {
		return new ComplexNumber(this.real, this.imag + val);
	}

	/**
	 * Subtracts another complex number from this complex number.
	 *
	 * @param that The complex number to be subtracted.
	 * @return The result of the subtraction.
	 */
	public ComplexNumber sub(ComplexNumber that) {
		return new ComplexNumber(this.real - that.real, this.imag - that.imag);
	}

	/**
	 * Subtracts a real value from the real part of this complex number.
	 *
	 * @param val The real value to be subtracted.
	 * @return The result of the subtraction.
	 */
	public ComplexNumber sub(double val) {
		return new ComplexNumber(this.real - val, this.imag);
	}

	/**
	 * Subtracts an imaginary value from the imaginary part of this complex number.
	 *
	 * @param val The imaginary value to be subtracted.
	 * @return The result of the subtraction.
	 */
	public ComplexNumber isub(double val) {
		return new ComplexNumber(this.real, this.imag - val);
	}

	/**
	 * Returns the conjugate of this complex number.
	 *
	 * @return The conjugate of the complex number.
	 */
	public ComplexNumber conj() {
		return new ComplexNumber(this.real, -this.imag);
	}

	/**
	 * Divides this complex number by another complex number.
	 *
	 * @param that The complex number to divide by.
	 * @return The result of the division.
	 */
	public ComplexNumber div(ComplexNumber that) {
		double mag = this.mag();
		return that.mult(this.conj()).div(mag * mag);
	}

	/**
	 * Divides this complex number by a real value.
	 *
	 * @param val The real value to divide by.
	 * @return The result of the division.
	 */
	public ComplexNumber div(double val) {
		return new ComplexNumber(this.real / val, this.imag / val);
	}

	/**
	 * Multiplies this complex number by another complex number (helper method).
	 *
	 * @param that The complex number to multiply by.
	 * @return The result of the multiplication.
	 */
	public ComplexNumber mult(ComplexNumber that) {
		double newReal = this.real * that.real - this.imag * that.imag;
		double newImag = this.real * that.imag + this.imag * that.real;
		return new ComplexNumber(newReal, newImag);
	}

	/**
	 * Multiplies this complex number by a real value.
	 *
	 * @param val The real value to multiply by.
	 * @return The result of the multiplication.
	 */
	public ComplexNumber mult(double val) {
		return new ComplexNumber(this.real * val, this.imag * val);
	}

	/**
	 * Returns e raised to this complex number, using Euler's formula.
	 *
	 * @return The result of the exponentiation.
	 */
	public ComplexNumber exp() {
		double[] polar = this.toPolar();
		return new ComplexNumber(polar[0] * Math.cos(polar[1]), polar[0] * Math.sin(polar[1]));
	}

	/**
	 * Converts the complex number to polar form.
	 *
	 * @return An array [magnitude, phase] representing the polar form.
	 */
	public double[] toPolar() {
		double magnitude = this.mag();
		double phase = Math.atan2(this.imag, this.real);
		return new double[] { magnitude, phase };
	}

	/**
	 * Creates a complex number from polar coordinates.
	 *
	 * @param magnitude The magnitude of the complex number.
	 * @param phase     The phase of the complex number.
	 * @return The complex number in rectangular form.
	 */
	public static ComplexNumber fromPolar(double magnitude, double phase) {
		double real = magnitude * Math.cos(phase);
		double imag = magnitude * Math.sin(phase);
		return new ComplexNumber(real, imag);
	}

	/**
	 * Checks if two complex numbers are approximately equal.
	 *
	 * @param that The complex number to compare with.
	 * @return True if approximately equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof ComplexNumber that) {
			double epsilon = 1e-10; // Adjust as needed
			return Math.abs(this.real - that.real) < epsilon && Math.abs(this.imag - that.imag) < epsilon;
		}
		return false;
	}

	/**
	 * Returns a human-readable string representation of the complex number.
	 *
	 * @return The string representation of the complex number.
	 */
	@Override
	public String toString() {
		return String.format("%f + %fi", this.real, this.imag);
	}

}