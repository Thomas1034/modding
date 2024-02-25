package com.thomas.quantumcircuit.qsim.complex;

import java.math.BigInteger;

import com.thomas.quantumcircuit.qsim.circuit.CircuitModifier;
import com.thomas.quantumcircuit.qsim.circuit.StateVector;

/**
 * Represents a matrix of complex numbers. With the gracious assistance of
 * ChatGPT.
 */
public class ComplexMatrix implements CircuitModifier {

	private final BigInteger rows;
	private final BigInteger cols;
	private final SparseMatrix matrix;

	/**
	 * Constructs a complex matrix with the given number of rows and columns.
	 *
	 * @param rows The number of rows in the matrix.
	 * @param cols The number of columns in the matrix.
	 */
	public ComplexMatrix(int rows, int cols) {
		this.rows = BigInteger.valueOf(rows);
		this.cols = BigInteger.valueOf(cols);
		this.matrix = new SparseMatrix();
	}

	/**
	 * Constructs a complex matrix with the given number of rows and columns.
	 *
	 * @param rows The number of rows in the matrix.
	 * @param cols The number of columns in the matrix.
	 */
	public ComplexMatrix(BigInteger rows, BigInteger cols) {
		this.rows = rows;
		this.cols = cols;
		this.matrix = new SparseMatrix();
	}

	/**
	 * Creates an identity matrix of the specified size.
	 *
	 * @param size The size of the identity matrix (number of rows and columns).
	 * @return The identity matrix.
	 */
	public static ComplexMatrix ident(int size) {
		ComplexMatrix identityMatrix = new ComplexMatrix(size, size);
		BigInteger bsize = BigInteger.valueOf(size);
		for (BigInteger i = BigInteger.ZERO; i.compareTo(bsize) < 0; i = i.add(BigInteger.ONE)) {
			identityMatrix.set(i, i, ComplexNumber.ONE);
		}
		return identityMatrix;
	}

	/**
	 * Creates a zero matrix of the specified size.
	 *
	 * @param size The size of the zero matrix (number of rows and columns).
	 * @return The zero matrix.
	 */
	public static ComplexMatrix zero(int size) {
		return new ComplexMatrix(size, size);
	}

	public static ComplexMatrix fromArray(BigInteger[][] arr) {

		ComplexMatrix result = new ComplexMatrix(arr.length, arr[0].length);

		for (BigInteger i = BigInteger.ZERO; i.compareTo(result.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(result.cols) < 0; j = j.add(BigInteger.ONE)) {
				result.set(i, j, result.get(i, j).add(arr[i.intValue()][j.intValue()].intValue()));
			}
		}

		return result;
	}

	/**
	 * Gets the number of rows in the matrix.
	 *
	 * @return The number of rows.
	 */
	public BigInteger getRows() {
		return this.rows;
	}

	/**
	 * Gets the number of columns in the matrix.
	 *
	 * @return The number of columns.
	 */
	public BigInteger getCols() {
		return this.cols;
	}

	/**
	 * Gets the complex number at the specified row and column.
	 *
	 * @param row The row index.
	 * @param col The column index.
	 * @return The complex number at the specified position.
	 */
	public ComplexNumber get(BigInteger row, BigInteger col) {
		return this.matrix.get(row, col);
	}

	/**
	 * Sets the complex number at the specified row and column.
	 *
	 * @param row   The row index.
	 * @param col   The column index.
	 * @param value The complex number to set.
	 */
	public void set(BigInteger row, BigInteger col, ComplexNumber value) {
		this.matrix.set(row, col, value);
	}

	/**
	 * Sets the complex number at the specified row and column.
	 *
	 * @param row   The row index.
	 * @param col   The column index.
	 * @param value The complex number to set.
	 */
	public ComplexMatrix set(int row, int col, ComplexNumber value) {
		this.set(BigInteger.valueOf(row), BigInteger.valueOf(col), value);
		return this;
	}

	/**
	 * Adds another complex matrix to this matrix.
	 *
	 * @param other The complex matrix to be added.
	 * @return The result of the addition.
	 */
	public ComplexMatrix add(ComplexMatrix other) {
		if (!this.rows.equals(other.rows) || !this.cols.equals(other.cols)) {
			throw new IllegalArgumentException("Matrix dimensions must be the same for addition.");
		}

		ComplexMatrix result = new ComplexMatrix(this.rows, this.cols);

		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
				result.set(i, j, this.get(i, j).add(other.get(i, j)));
			}
		}

		return result;
	}

	/**
	 * Subtracts another complex matrix from this matrix.
	 *
	 * @param other The complex matrix to be subtracted.
	 * @return The result of the subtraction.
	 */
	public ComplexMatrix sub(ComplexMatrix other) {
		if (!this.rows.equals(other.rows) || !this.cols.equals(other.cols)) {
			throw new IllegalArgumentException("Matrix dimensions must be the same for subtraction.");
		}

		ComplexMatrix result = new ComplexMatrix(this.rows, this.cols);

		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
				result.set(i, j, this.get(i, j).sub(other.get(i, j)));
			}
		}

		return result;
	}

	/**
	 * Multiplies this matrix by another complex matrix.
	 *
	 * @param that The complex matrix to multiply by.
	 * @return The result of the multiplication.
	 */
	public ComplexMatrix mult(ComplexMatrix that) {
		
		
		if (!this.cols.equals(that.rows)) {
			throw new IllegalArgumentException(
					"Number of columns in the first matrix (" + this.cols + ") must be equal to the number of rows in the second matrix (" + that.rows+ ") for multiplication.");
		}

		ComplexMatrix result = new ComplexMatrix(this.rows, that.cols);

		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(that.cols) < 0; j = j.add(BigInteger.ONE)) {
				ComplexNumber sum = new ComplexNumber(0, 0);
				for (BigInteger k = BigInteger.ZERO; k.compareTo(this.cols) < 0; k = k.add(BigInteger.ONE)) {
					sum = sum.add(this.get(i, k).mult(that.get(k, j)));
				}
				result.set(i, j, sum);
			}
		}

		return result;
	}

	/**
	 * Multiplies this matrix by a scalar (a complex number).
	 *
	 * @param scalar The complex number to multiply each element by.
	 * @return The result of the scalar multiplication.
	 */
	public ComplexMatrix mult(ComplexNumber scalar) {
		ComplexMatrix result = new ComplexMatrix(rows, cols);

		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
				result.set(i, j, this.get(i, j).mult(scalar));
			}
		}
		return result;
	}

	/**
	 * Adds a scalar multiple of the identity matrix to this matrix.
	 *
	 * @param scalar The complex number to add to each element.
	 * @return The result of the addition.
	 */
	public ComplexMatrix add(ComplexNumber scalar) {
		ComplexMatrix result = new ComplexMatrix(rows, cols);

		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
				result.set(i, j, this.get(i, j).add(scalar));
			}
		}
		return result;
	}

	/**
	 * Subtracts a scalar multiple of the identity matrix from this matrix.
	 *
	 * @param scalar The complex number to subtract from each element.
	 * @return The result of the subtraction.
	 */
	public ComplexMatrix sub(ComplexNumber scalar) {
		ComplexMatrix result = new ComplexMatrix(rows, cols);

		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
				result.set(i, j, this.get(i, j).sub(scalar));
			}
		}
		return result;
	}

	/**
	 * Computes the tensor product of this matrix with another matrix.
	 *
	 * @param other The matrix to tensor product with.
	 * @return The result of the tensor product.
	 */
	public ComplexMatrix tensor(ComplexMatrix other) {
		ComplexMatrix result = new ComplexMatrix(this.rows.multiply(other.rows), this.cols.multiply(other.cols));
		
		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
				for (BigInteger k = BigInteger.ZERO; k.compareTo(other.rows) < 0; k = k.add(BigInteger.ONE)) {
					for (BigInteger l = BigInteger.ZERO; l.compareTo(other.cols) < 0; l = l.add(BigInteger.ONE)) {
						ComplexNumber element = this.get(i, j).mult(other.get(k, l));
						result.set(i.multiply(other.rows).add(k), j.multiply(other.cols).add(l), element);
					}
				}
			}
		}

		return result;
	}

	/**
	 * Calculates the determinant of the matrix using LU decomposition.
	 *
	 * @return The determinant of the matrix.
	 */
	public ComplexNumber det() {
		// Check if the matrix is square
		if (!this.rows.equals(this.cols)) {
			throw new UnsupportedOperationException("Determinant is only defined for square matrices, this matrix is " + this.rows + "x" + this.cols);
		}

		// Create a copy of the matrix to avoid modifying the original
		ComplexMatrix copyMatrix = new ComplexMatrix(this.rows, this.cols);
		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
				copyMatrix.set(i, j, this.get(i, j));
			}
		}

		// Initialize variables
		ComplexNumber determinant = ComplexNumber.ONE;
		BigInteger sign = BigInteger.ONE;

		// Perform LU decomposition with partial pivoting
		for (BigInteger k = BigInteger.ZERO; k.compareTo(this.rows.subtract(BigInteger.ONE)) < 0; k = k
				.add(BigInteger.ONE)) {
			// Find pivot element and swap rows if necessary
			BigInteger pivotRow = findPivotRow(copyMatrix, k);
			if (!pivotRow.equals(k)) {
				copyMatrix = swapRows(copyMatrix, k, pivotRow);
				sign = sign.negate();
			}

			// Update the determinant with the pivot element
			determinant = determinant.mult(copyMatrix.get(k, k));

			// Update the lower and upper triangular matrices
			for (BigInteger i = k.add(BigInteger.ONE); i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
				ComplexNumber factor = copyMatrix.get(i, k).div(copyMatrix.get(k, k));
				copyMatrix.set(i, k, factor);
				for (BigInteger j = k.add(BigInteger.ONE); j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
					copyMatrix.set(i, j, copyMatrix.get(i, j).sub(factor.mult(copyMatrix.get(k, j))));
				}
			}
		}

		// Multiply by the last pivot element
		determinant = determinant
				.mult(copyMatrix.get(this.rows.subtract(BigInteger.ONE), this.cols.subtract(BigInteger.ONE)));

		return determinant.mult(sign.intValueExact());
	}

	/**
	 * Finds the pivot row for LU decomposition with partial pivoting.
	 *
	 * @param matrix The matrix.
	 * @param k      The current column index.
	 * @return The index of the pivot row.
	 */
	private BigInteger findPivotRow(ComplexMatrix matrix, BigInteger k) {
		BigInteger pivotRow = k;
		double pivotValue = matrix.get(k, k).mag();

		for (BigInteger i = k.add(BigInteger.ONE); i.compareTo(matrix.rows) < 0; i = i.add(BigInteger.ONE)) {
			double currentValue = matrix.get(i, k).mag();
			if (currentValue > pivotValue) {
				pivotValue = currentValue;
				pivotRow = i;
			}
		}

		return pivotRow;
	}

	/**
	 * Swaps two rows in the matrix.
	 *
	 * @param matrix The matrix.
	 * @param row1   The index of the first row.
	 * @param row2   The index of the second row.
	 * @return The matrix with swapped rows.
	 */
	private ComplexMatrix swapRows(ComplexMatrix matrix, BigInteger row1, BigInteger row2) {
		ComplexMatrix swappedMatrix = new ComplexMatrix(matrix.rows, matrix.cols);

		for (BigInteger i = BigInteger.ZERO; i.compareTo(matrix.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(matrix.cols) < 0; j = j.add(BigInteger.ONE)) {
				ComplexNumber value = (i.equals(row1)) ? matrix.get(row2, j)
						: ((i.equals(row2)) ? matrix.get(row1, j) : matrix.get(i, j));
				swappedMatrix.set(i, j, value);
			}
		}

		return swappedMatrix;
	}

	/**
	 * Checks if two matrices are equal.
	 *
	 * @param other The matrix to compare with.
	 * @return True if the matrices are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof ComplexMatrix other) {
			if (!this.rows.equals(other.rows) || !this.cols.equals(other.cols)) {
				return false;
			}

			for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
				for (BigInteger j = BigInteger.ZERO; j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
					if (!this.get(i, j).equals(other.get(i, j))) {
						return false;
					}
				}
			}
			return true;
		}

		return false;
	}

	/**
	 * Returns a human-readable string representation of the complex matrix.
	 *
	 * @return The string representation of the complex matrix.
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (BigInteger i = BigInteger.ZERO; i.compareTo(this.rows) < 0; i = i.add(BigInteger.ONE)) {
			for (BigInteger j = BigInteger.ZERO; j.compareTo(this.cols) < 0; j = j.add(BigInteger.ONE)) {
				result.append(this.get(i, j)).append("\t");
			}
			result.append("\n");
		}
		return result.toString();
	}

	@Override
	public StateVector apply(StateVector state) {
		return state.applyMatrix(this);
	}

}