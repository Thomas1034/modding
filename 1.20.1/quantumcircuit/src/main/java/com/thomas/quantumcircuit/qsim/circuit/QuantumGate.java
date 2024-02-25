package com.thomas.quantumcircuit.qsim.circuit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;

import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public abstract class QuantumGate implements CircuitModifier {

	private ComplexMatrix matrix;
	private ComplexMatrix originalMatrix;
	
	/**
	 * Constructs a quantum gate with the specified complex matrix, number of
	 * qubits, and target qubits.
	 *
	 * @param matrix     The complex matrix defining the quantum gate.
	 * @param numQubits  The total number of qubits in the quantum system.
	 * @param targetBits The indices of the qubits to which the gate is applied.
	 * @throws IllegalArgumentException If the matrix is not square or the number of
	 *                                  target qubits is invalid.
	 */
	public QuantumGate(ComplexMatrix matrix, int numQubits, int... targetBits) {

		// Check if the matrix is square and of the same size as the
		// number of bits it's being applied to.
		if (matrix.getRows().compareTo(matrix.getCols()) != 0) {
			throw new IllegalArgumentException("The matrix for a quantum gate must be square.");
		}
		if (matrix.getRows().compareTo(BigInteger.valueOf(1 << targetBits.length)) != 0) {
			throw new IllegalArgumentException("The matrix cannot be applied to that number of bits.");
		}
		// Copy over the data.
		this.originalMatrix = matrix;

		// Generate a matrix that applies the original matrix to the specified bits of a
		// quantum register of length numQubits.
		// The number of qubits that need to be added to get to the full size.
		int sizeDiff = numQubits - targetBits.length;

		if (sizeDiff < 0) {
			throw new IllegalArgumentException(
					"The matrix for a quantum gate cannot have more rows than the system has qubits.");
		}

		// Now, create a matrix that brings the target bits to the top of the matrix.
		ComplexMatrix rearrangeBits = ComplexMatrix.ident(1 << numQubits);
		ComplexMatrix restoreBits = ComplexMatrix.ident(1 << numQubits);
		// Will store the matrices used to rearrange the wires so the gate is simpler,
		// in order.
		ArrayList<ComplexMatrix> swapMatrices = new ArrayList<>();
		HashSet<UnorderedPair> swaps = new HashSet<>();
		for (int i = 0; i < targetBits.length; i++) {
			// Forms a set of unordered pairs for the swaps.
			// This prevents duplicates.
			if (i != targetBits[i]) {
				swaps.add(new UnorderedPair(i, targetBits[i]));				
			}
		}

		// Applies the swaps.
		for (UnorderedPair pair : swaps) {			
			swapMatrices.add(swapQubitsMatrix(numQubits, pair.x(), pair.y()));
		}

		// Now, multiply them in order to get the initial matrix, and multiply them in
		// reverse order to get the inverse matrix.
		// This works since the bit swapping matrices should (theoretically) be their
		// own inverses.
		for (int i = 0; i < swaps.size(); i++) {
			rearrangeBits = rearrangeBits.mult(swapMatrices.get(i));
			restoreBits = restoreBits.mult(swapMatrices.get(swaps.size() - i - 1));
		}

		// Now, construct the matrix when applied to the first few bits.
		ComplexMatrix appliedGate = this.originalMatrix;
		if (sizeDiff > 0) {
			appliedGate = ComplexMatrix.ident(1 << (sizeDiff));
			appliedGate = appliedGate.tensor(this.originalMatrix);
		}

		// Rearrange the bits so that the targets are in the correct order, apply the
		// matrix, then reorganize back to the original order.
		// This is the overall matrix, at long last.
//		System.out.println("Rearrange matrix:");
//		System.out.println(rearrangeBits);
//		System.out.println("Gate matrix:");
//		System.out.println(appliedGate);
//		System.out.println("Restore matrix:");
//		System.out.println(rearrangeBits);
		this.matrix = rearrangeBits.mult(appliedGate).mult(restoreBits);

	}

	/**
	 * Internal method to generate a matrix that swaps the positions of two qubits
	 * in a quantum system.
	 *
	 * @param numQubits The total number of qubits in the quantum system.
	 * @param bit1      The index of the first qubit to be swapped.
	 * @param bit2      The index of the second qubit to be swapped.
	 * @return The complex matrix representing the qubit swap operation.
	 */
	private ComplexMatrix swapQubitsMatrix(int numQubits, int bit1, int bit2) {

		// Check if the bits are out of bounds.
		if (bit1 >= numQubits || bit2 >= numQubits) {
			throw new IllegalArgumentException(
					"Cannot access row " + Math.max(bit1, bit2) + " in matrix of size " + numQubits);
		}

		if (bit1 < 0 || bit2 < 0) {
			throw new IllegalArgumentException(
					"Cannot access row " + Math.min(bit1, bit2) + " in matrix of size " + numQubits);
		}

		// If the bits aren't being switched, return the identity.
		if (bit1 == bit2) {
			return ComplexMatrix.ident(1 << numQubits);
		}

		// Create a zero matrix.
		ComplexMatrix retval = ComplexMatrix.zero(1 << numQubits);

		// Loop over every row
		for (BigInteger i = BigInteger.ZERO; i.compareTo(retval.getRows()) < 0; i = i.add(BigInteger.ONE)) {
			boolean temp1, temp2;
			// First, find out what row to swap with.
			BigInteger r1 = i;
			BigInteger r2 = i;

			temp1 = r1.testBit(bit1);
			temp2 = r1.testBit(bit2);

			if (temp1) {
				r2 = r2.setBit(bit2);
			} else {
				r2 = r2.clearBit(bit2);
			}

			if (temp2) {
				r2 = r2.setBit(bit1);
			} else {
				r2 = r2.clearBit(bit1);
			}

			// Now set r1, r2 here and r2, r1 there.
			retval.set(r1, r2, ComplexNumber.ONE);
			retval.set(r2, r1, ComplexNumber.ONE);

		}
		return retval;
	}

	/**
	 * Applies the quantum gate to a given quantum state vector.
	 *
	 * @param state The quantum state vector to which the gate is applied.
	 * @return The resulting quantum state vector after applying the gate.
	 */
	public StateVector apply(StateVector state) {
		return state.applyMatrix(this.matrix);
	}

	/**
	 * Retrieves the complex matrix representation of the quantum gate.
	 *
	 * @return The complex matrix of the quantum gate.
	 */
	public ComplexMatrix asMatrix() {
		return this.matrix;
	}
}
