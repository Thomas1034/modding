package com.thomas.quantumcircuit.qsim.circuit;

import java.util.Arrays;
import java.util.Random;

import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

import net.minecraft.util.RandomSource;

public class StateVector {

	private int size;
	private ComplexMatrix amplitudes;

	/**
	 * Private constructor used by the static factory method.
	 */
	private StateVector(int size, ComplexNumber[] amplitudes) {
		this.setSize(size);
		this.amplitudes = convertToColumnVector(Arrays.copyOf(amplitudes, amplitudes.length));
	}
	
	/**
	 * Private constructor used by the static factory method.
	 */
	private StateVector(int size, ComplexMatrix matrix) {
		this.setSize(size);
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
		int size = log2(amps.getRows());
		return new StateVector(size, amps);
	}

	/**
	 * Static Factory Method: Initializes a state vector with the provided
	 * amplitudes.
	 *
	 * @param size The number of qubits in the system.
	 * @param amps The desired amplitudes to initialize the state vector.
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
	 * @param size The number of qubits in the system.
	 * @return A new instance of StateVector representing the state |0⟩.
	 */
	public static StateVector createZeroState(int size) {
		ComplexNumber[] zeroAmplitudes = new ComplexNumber[1 << size];
		zeroAmplitudes[0] = new ComplexNumber(1, 0); // Probability amplitude of |0⟩ is 1, others are 0
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
	public ComplexNumber getAmplitude(int basisState) {
		if (basisState < 0 || basisState >= amplitudes.getRows()) {
			throw new IllegalArgumentException("Invalid basis state index");
		}
		return amplitudes.get(basisState, 0);
	}

	/**
	 * Chooses and returns a random state (as an integer) based on a random number
	 * from the specified random source.
	 *
	 * @param rand The random number generator.
	 * @return A randomly chosen state represented as an integer.
	 */
	public int choose(Random rand) {
		double randValue = rand.nextDouble();
		return this.chooseFromValue(randValue);
	}

	/**
	 * Chooses and returns a random state (as an integer) based on a random number
	 * from the specified random source.
	 * 
	 * Uses the Minecraft utility object, RandomSource, for compatibility with the
	 * QuantumCircuit mod.
	 *
	 * @param rand The random number generator.
	 * @return A randomly chosen state represented as an integer.
	 */
	public int choose(RandomSource rand) {
		double randValue = rand.nextDouble();
		return this.chooseFromValue(randValue);
	}

	/**
	 * Chooses and returns a state (as an integer) based on the given number.
	 *
	 * @param val The number to choose the state from.
	 * @return A chosen state represented as an integer.
	 */
	private int chooseFromValue(double val) {
		double cumulativeProbability = 0;
		for (int i = 0; i < amplitudes.getRows(); i++) {
			double mag = amplitudes.get(i, 0).mag();
			cumulativeProbability += mag * mag;
			if (val <= cumulativeProbability) {
				return i;
			}
		}

		// This should not happen if the amplitudes are properly normalized
		throw new IllegalStateException("Unable to choose a random state. Check the state vector normalization.");
	}

	/**
	 * Applies the given matrix to this state, returning a new state with the resulting value.
	 * 
	 * @param matrix The matrix to apply to this state.
	 * @return The result of the multiplication.
	 */
	public StateVector applyMatrix(ComplexMatrix matrix) {
		return new StateVector(this.size, matrix.mult(this.amplitudes));
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public static int log2(int bits) {
		if (bits == 0)
			return 0; // or throw an exception
		return 31 - Integer.numberOfLeadingZeros(bits);
	}
}
