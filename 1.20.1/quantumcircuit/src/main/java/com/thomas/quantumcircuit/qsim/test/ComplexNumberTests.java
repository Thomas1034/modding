package com.thomas.quantumcircuit.qsim.test;

import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class ComplexNumberTests {

	public static void run() {
		testAddition();
		testSubtraction();
		testMultiplication();
		testDivision();
		testExponential();
		testPolarConversion();
		testEquals();
	}

	public static void testAddition() {
		ComplexNumber num1 = new ComplexNumber(2, 3);
		ComplexNumber num2 = new ComplexNumber(4, 5);

		ComplexNumber result = num1.add(num2);

		assert result.equals(new ComplexNumber(6, 8));
	}

	public static void testSubtraction() {
		ComplexNumber num1 = new ComplexNumber(2, 3);
		ComplexNumber num2 = new ComplexNumber(4, 5);

		ComplexNumber result = num1.sub(num2);

		assert result.equals(new ComplexNumber(-2, -2));
	}

	public static void testMultiplication() {
		ComplexNumber num1 = new ComplexNumber(2, 3);
		ComplexNumber num2 = new ComplexNumber(4, 5);

		ComplexNumber result = num1.mult(num2);

		assert result.equals(new ComplexNumber(-7, 22));
	}

	public static void testDivision() {
		ComplexNumber num1 = new ComplexNumber(2, 3);
		ComplexNumber num2 = new ComplexNumber(4, 5);

		ComplexNumber result = num1.div(num2);

		assert result.equals(new ComplexNumber(0.5609756097560976, 0.0487804878048781));
	}

	public static void testExponential() {
		ComplexNumber num = new ComplexNumber(2, 3);

		ComplexNumber result = num.exp();

		assert result.equals(new ComplexNumber(-7.315110094901103, 1.0427436562359045));
	}

	public static void testPolarConversion() {
		ComplexNumber num = new ComplexNumber(2, 3);

		double[] polar = num.toPolar();

		assert Math.abs(polar[0] - 3.605551275463989) < 1e-10;
		assert Math.abs(polar[1] - 0.982793723247329) < 1e-10;
	}

	public static void testEquals() {
		ComplexNumber num1 = new ComplexNumber(2, 3);
		ComplexNumber num2 = new ComplexNumber(2, 3);
		ComplexNumber num3 = new ComplexNumber(4, 5);

		assert num1.equals(num2);
		assert !num1.equals(num3);
	}
}