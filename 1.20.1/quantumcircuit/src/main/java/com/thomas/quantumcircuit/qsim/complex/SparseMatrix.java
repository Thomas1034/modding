package com.thomas.quantumcircuit.qsim.complex;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a sparse matrix of complex numbers, used internally in the
 * ComplexMatrix class. With the gracious assistance of ChatGPT.
 */
public class SparseMatrix {
	private Map<MatrixIndex, ComplexNumber> matrix;

	public SparseMatrix() {
		matrix = new HashMap<>();
	}

	public void set(BigInteger row, BigInteger col, ComplexNumber value) {
		MatrixIndex index = new MatrixIndex(row, col);
		if (value == null) {
			matrix.remove(index);
			return;
		}

		if (value.equals(ComplexNumber.ZERO)) {
			matrix.remove(index);
		} else {
			matrix.put(index, value);
		}
	}

	public ComplexNumber get(BigInteger row, BigInteger col) {
		MatrixIndex index = new MatrixIndex(row, col);
		return matrix.getOrDefault(index, ComplexNumber.ZERO);
	}

	private record MatrixIndex(BigInteger row, BigInteger col) {

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			MatrixIndex that = (MatrixIndex) obj;
			return this.row.equals(that.row) && this.col.equals(that.col);
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.row, this.col);
		}
	}
}
