package com.thomas.quantumcircuit.qsim.circuit;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

import com.google.common.math.BigIntegerMath;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class StateVector {

	private int numBits;
	private ComplexMatrix amplitudes;

	/**
	 * Private constructor used by the static factory method.
	 */
	private StateVector(int size, ComplexNumber[] amplitudes) {
		this.numBits = size;
		this.amplitudes = convertToColumnVector(Arrays.copyOf(amplitudes, amplitudes.length));
	}

	/**
	 * Private constructor used by the static factory method.
	 */
	private StateVector(int size, ComplexMatrix matrix) {
		this.numBits = size;
		this.amplitudes = matrix.add(new ComplexNumber(0, 0));
	}

	/**
	 * Static Factory Method: Initializes a state vector with the provided
	 * amplitudes.
	 *
	 * @param amps The desired amplitudes to initialize the state vector.
	 * @return A new instance of StateVector initialized with the specified
	 *         amplitudes.
	 */
	public static StateVector initialize(ComplexMatrix amps) {
		BigInteger rows = amps.getRows();
		int size = BigIntegerMath.log2(rows, RoundingMode.UNNECESSARY);
		return new StateVector(size, amps);
	}

	/**
	 * Static Factory Method: Initializes a state vector with the provided
	 * amplitudes.
	 *
	 * @param numBits The number of qubits in the system.
	 * @param amps    The desired amplitudes to initialize the state vector.
	 * @return A new instance of StateVector initialized with the specified
	 *         amplitudes.
	 */
	public static StateVector initialize(int size, ComplexNumber[] amps) {
		return new StateVector(size, amps);
	}

	/**
	 * Constructor: Creates an instance of the StateVector class, initializing it to
	 * represent the state |0⟩.
	 *
	 * @param numBits The number of qubits in the system.
	 * @return A new instance of StateVector representing the state |0⟩.
	 */
	public static StateVector createZeroState(int size) {
		ComplexNumber[] zeroAmplitudes = new ComplexNumber[1 << size];
		zeroAmplitudes[0] = new ComplexNumber(1, 0); // Probability amplitude of |0⟩ is 1, others are 0
		return new StateVector(size, zeroAmplitudes);
	}

	/**
	 * Constructor: Creates an instance of the StateVector class, initializing it to
	 * have amplitudes of zero.
	 *
	 * @param numBits The number of qubits in the system.
	 * @return A new instance of StateVector with amplitudes of zero.
	 */
	public static StateVector empty(int size) {
		ComplexNumber[] zeroAmplitudes = new ComplexNumber[1 << size];
		return new StateVector(size, zeroAmplitudes);
	}

	/**
	 * Converts an array of complex numbers to a column vector.
	 *
	 * @param amps The array of complex numbers.
	 * @return The column vector representing the state.
	 */
	private static ComplexMatrix convertToColumnVector(ComplexNumber[] amps) {
		int size = amps.length;
		ComplexMatrix columnVector = new ComplexMatrix(size, 1);

		for (int i = 0; i < size; i++) {
			columnVector.set(i, 0, amps[i]);
		}

		return columnVector;
	}

	/**
	 * Gets the probability amplitude for a specific basis state.
	 *
	 * @param basisState The binary representation of the basis state.
	 * @return The probability amplitude for the specified basis state.
	 */
	public ComplexNumber getAmplitude(BigInteger basisState) {
		if (basisState.compareTo(BigInteger.ZERO) < 0 || basisState.compareTo(amplitudes.getRows()) >= 0) {
			throw new IllegalArgumentException("Invalid basis state index");
		}
		return amplitudes.get(basisState, BigInteger.ZERO);
	}

	/**
	 * Returns a projection matrix for the specified qubit q (with state s) in an
	 * n-qubit system.
	 * 
	 * @param n The number of qubits in the system.
	 * @param q The qubit to project onto.
	 * @param s The qubit state to project onto.
	 * @return A projection matrix for the desired state.
	 */
	public static ComplexMatrix projector(int n, int q, boolean s) {

		if (q >= n) {
			throw new IllegalArgumentException("Invalid qubit index");
		}

		ComplexMatrix retval = ComplexMatrix.zero(2);
		if (!s) {
			retval.set(0, 0, ComplexNumber.ONE);
		} else {
			retval.set(1, 1, ComplexNumber.ONE);
		}

		if (n == 1) {
			return retval;
		}

		if (n != q - 1) {
			retval = ComplexMatrix.ident(1 << (n - q - 1)).tensor(retval);

		}

		if (q != 0) {
			retval = retval.tensor(ComplexMatrix.ident(1 << q));
		}

		return retval;
	}

	/**
	 * Calculates the inner product with the given state vector.
	 * 
	 * @param that The state vector to take the inner product with.
	 * @return The inner product.
	 */
	public ComplexNumber inner(StateVector that) {

		ComplexNumber prod = ComplexNumber.ZERO;

		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.amplitudes.getRows()) < 0; i = i.add(BigInteger.ONE)) {
			prod = prod
					.add(that.amplitudes.get(i, BigInteger.ZERO).conj().mult(this.amplitudes.get(i, BigInteger.ZERO)));
		}

		return prod;
	}

	/**
	 * Returns a normalized version of this vector.
	 * 
	 * @return A normalized version of this vector.
	 */
	public StateVector norm() {

		StateVector sv = StateVector.empty(this.numBits);

		double amplitude = Math.sqrt(this.inner(this).real());

		for (BigInteger i = BigInteger.ZERO; i.compareTo(sv.amplitudes.getRows()) < 0; i = i.add(BigInteger.ONE)) {
			sv.amplitudes.set(i, BigInteger.ZERO, this.amplitudes.get(i, BigInteger.ZERO).div(amplitude));
		}
		return sv;
	}

	/**
	 * Applies the given matrix to this state, returning a new state with the
	 * resulting value.
	 * 
	 * @param matrix The matrix to apply to this state.
	 * @return The result of the multiplication.
	 */
	public StateVector applyMatrix(ComplexMatrix matrix) {

		return new StateVector(this.numBits, matrix.mult(this.amplitudes));
	}

	/**
	 * Returns the normalized projection of the state vector onto the specified
	 * qubit state. Returns null if the state vector is orthogonal to the specified
	 * state.
	 * 
	 * @param n The number of qubits in the system.
	 * @param q The qubit to project onto.
	 * @param s The qubit state to project onto.
	 * @return The result of the projection.
	 */
	public StateVector project(int q, boolean s) {

		StateVector unnormed = this.projectUnnormed(q, s);
		if (unnormed.inner(unnormed).equals(ComplexNumber.ZERO)) {
			return null;
		}
		return unnormed.norm();
	}

	/**
	 * Returns the normalized projection of the state vector onto the specified
	 * qubit state. Returns null if the state vector is orthogonal to the specified
	 * state.
	 * 
	 * @param n The number of qubits in the system.
	 * @param q The qubit to project onto.
	 * @param s The qubit state to project onto.
	 * @return The result of the projection.
	 */
	private StateVector projectUnnormed(int q, boolean s) {
		return this.applyMatrix(StateVector.projector(this.numBits, q, s));
	}

	/**
	 * Measures the specified qubit q with the number x as the random value,
	 * returning the resulting state. This collapses the state.
	 * 
	 * @return The collapsed state.
	 */
	public StateVector measure(int q, double x) {
		// Get the projections of the state.
		StateVector ifTrue = projectUnnormed(q, true);
		StateVector ifFalse = projectUnnormed(q, false);

		// Get the probabilities of the states.
		double chanceOfTrue = ifTrue.inner(ifTrue).real();

		// If this is larger than x, collapse to the measurement of true.
		// If not, collapse to the measurement of false.
		if (chanceOfTrue > x) {
			return ifTrue.norm();
		} else {
			return ifFalse.norm();
		}
	}

	/**
	 * Checks whether the specified qubit is true, false, or not yet measured.
	 * 
	 * @return true if the specified qubit is true, false if it is false, or null if
	 *         it is unmeasured.
	 */
	public Boolean getMeasurement(int q) {

		// Get the projections of the state.
		StateVector ifTrue = projectUnnormed(q, true);

		// Get the probabilities of the states.
		ComplexNumber chanceOfTrue = ifTrue.inner(ifTrue);

		if (chanceOfTrue.equals(ComplexNumber.ONE)) {
			return true;
		} else if (chanceOfTrue.equals(ComplexNumber.ZERO)) {
			return false;
		} else {
			// It hasn't been measured yet.
			return null;
		}
	}

	/**
	 * Returns a human-readable string representation of the state vector.
	 *
	 * @return The string representation of the state vector.
	 */
	@Override
	public String toString() {
		return this.amplitudes.toString();
	}

}
