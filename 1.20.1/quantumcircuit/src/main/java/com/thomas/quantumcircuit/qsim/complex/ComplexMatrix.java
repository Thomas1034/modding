package com.thomas.quantumcircuit.qsim.complex;

/**
 * Represents a matrix of complex numbers. With the gracious assistance of
 * ChatGPT.
 */
public class ComplexMatrix {

	private final int rows;
	private final int cols;
	private final ComplexNumber[][] matrix;

	/**
	 * Constructs a complex matrix with the given number of rows and columns.
	 *
	 * @param rows The number of rows in the matrix.
	 * @param cols The number of columns in the matrix.
	 */
	public ComplexMatrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.matrix = new ComplexNumber[rows][cols];
		this.init();
	}

	/**
	 * Creates an identity matrix of the specified size.
	 *
	 * @param size The size of the identity matrix (number of rows and columns).
	 * @return The identity matrix.
	 */
	public static ComplexMatrix ident(int size) {
		ComplexMatrix identityMatrix = new ComplexMatrix(size, size);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j) {
					identityMatrix.set(i, j, new ComplexNumber(1, 0));
				}
			}
		}

		return identityMatrix;
	}

	/**
	 * Initializes the matrix with zero complex numbers.
	 */
	private void init() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.matrix[i][j] = new ComplexNumber(0, 0);
			}
		}
	}

	/**
	 * Gets the number of rows in the matrix.
	 *
	 * @return The number of rows.
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * Gets the number of columns in the matrix.
	 *
	 * @return The number of columns.
	 */
	public int getCols() {
		return this.cols;
	}

	/**
	 * Gets the complex number at the specified row and column.
	 *
	 * @param row The row index.
	 * @param col The column index.
	 * @return The complex number at the specified position.
	 */
	public ComplexNumber get(int row, int col) {
		return this.matrix[row][col];
	}

	/**
	 * Sets the complex number at the specified row and column.
	 *
	 * @param row   The row index.
	 * @param col   The column index.
	 * @param value The complex number to set.
	 */
	public void set(int row, int col, ComplexNumber value) {
		this.matrix[row][col] = value;
	}

	/**
	 * Adds another complex matrix to this matrix.
	 *
	 * @param other The complex matrix to be added.
	 * @return The result of the addition.
	 */
	public ComplexMatrix add(ComplexMatrix other) {
		if (this.rows != other.rows || this.cols != other.cols) {
			throw new IllegalArgumentException("Matrix dimensions must be the same for addition.");
		}

		ComplexMatrix result = new ComplexMatrix(this.rows, this.cols);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
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
		if (this.rows != other.rows || this.cols != other.cols) {
			throw new IllegalArgumentException("Matrix dimensions must be the same for subtraction.");
		}

		ComplexMatrix result = new ComplexMatrix(this.rows, this.cols);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				result.set(i, j, this.get(i, j).sub(other.get(i, j)));
			}
		}

		return result;
	}

	/**
	 * Multiplies this matrix by another complex matrix.
	 *
	 * @param other The complex matrix to multiply by.
	 * @return The result of the multiplication.
	 */
	public ComplexMatrix mult(ComplexMatrix other) {
		if (this.cols != other.rows) {
			throw new IllegalArgumentException(
					"Number of columns in the first matrix must be equal to the number of rows in the second matrix for multiplication.");
		}

		ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < other.cols; j++) {
				ComplexNumber sum = new ComplexNumber(0, 0);
				for (int k = 0; k < this.cols; k++) {
					sum = sum.add(this.get(i, k).mult(other.get(k, j)));
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

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
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

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
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

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
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
		ComplexMatrix result = new ComplexMatrix(this.rows * other.rows, this.cols * other.cols);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				for (int k = 0; k < other.rows; k++) {
					for (int l = 0; l < other.cols; l++) {
						ComplexNumber element = this.get(i, j).mult(other.get(k, l));
						result.set(i * other.rows + k, j * other.cols + l, element);
					}
				}
			}
		}

		return result;
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
			if (this.rows != other.rows || this.cols != other.cols) {
				return false;
			}

			for (int i = 0; i < this.rows; i++) {
				for (int j = 0; j < this.cols; j++) {
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
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				result.append(this.matrix[i][j]).append("\t");
			}
			result.append("\n");
		}
		return result.toString();
	}

}