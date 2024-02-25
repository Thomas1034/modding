package com.thomas.quantumcircuit.qsim.test;

import java.math.BigInteger;

import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class ComplexMatrixTests {

    public static void run() {
    	System.out.println("Running matrix tests!");
        testIdentityMatrix();
        testZeroMatrix();
        testMatrixAddition();
        testMatrixSubtraction();
        testMatrixMultiplication();
        testScalarMultiplication();
        testScalarAddition();
        testScalarSubtraction();
        testTensorProduct();
        testDeterminant();
        System.out.println("Done with matrix tests!");
    }

    private static void testIdentityMatrix() {
        System.out.println("Testing Identity Matrix:");

        ComplexMatrix identityMatrix = ComplexMatrix.ident(3);
        System.out.println("Expected Identity Matrix:");
        System.out.println("1\t0\t0");
        System.out.println("0\t1\t0");
        System.out.println("0\t0\t1");

        System.out.println("Actual Matrix:");
        System.out.println(identityMatrix);
        System.out.println();
    }

    private static void testZeroMatrix() {
        System.out.println("Testing Zero Matrix:");

        ComplexMatrix zeroMatrix = ComplexMatrix.zero(2);
        System.out.println("Expected Zero Matrix:");
        System.out.println("0\t0");
        System.out.println("0\t0");

        System.out.println("Actual Matrix:");
        System.out.println(zeroMatrix);
        System.out.println();
    }

    private static void testMatrixAddition() {
        System.out.println("Testing Matrix Addition:");

        ComplexMatrix matrixA = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("1"), new BigInteger("2")},
                                                                    {new BigInteger("3"), new BigInteger("4")}});
        ComplexMatrix matrixB = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("5"), new BigInteger("6")},
                                                                    {new BigInteger("7"), new BigInteger("8")}});
        ComplexMatrix result = matrixA.add(matrixB);

        System.out.println("Expected Result:");
        System.out.println("6\t8");
        System.out.println("10\t12");

        System.out.println("Actual Result:");
        System.out.println(result);
        System.out.println();
    }

    private static void testMatrixSubtraction() {
        System.out.println("Testing Matrix Subtraction:");

        ComplexMatrix matrixA = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("5"), new BigInteger("8")},
                                                                    {new BigInteger("3"), new BigInteger("2")}});
        ComplexMatrix matrixB = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("1"), new BigInteger("6")},
                                                                    {new BigInteger("7"), new BigInteger("4")}});
        ComplexMatrix result = matrixA.sub(matrixB);

        System.out.println("Expected Result:");
        System.out.println("4\t2");
        System.out.println("-4\t-2");

        System.out.println("Actual Result:");
        System.out.println(result);
        System.out.println();
    }

    private static void testMatrixMultiplication() {
        System.out.println("Testing Matrix Multiplication:");

        ComplexMatrix matrixA = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("1"), new BigInteger("2")},
                                                                    {new BigInteger("3"), new BigInteger("4")}});
        ComplexMatrix matrixB = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("5"), new BigInteger("6")},
                                                                    {new BigInteger("7"), new BigInteger("8")}});
        ComplexMatrix result = matrixA.mult(matrixB);

        System.out.println("Expected Result:");
        System.out.println("19\t22");
        System.out.println("43\t50");

        System.out.println("Actual Result:");
        System.out.println(result);
        System.out.println();
    }

    private static void testScalarMultiplication() {
        System.out.println("Testing Scalar Multiplication:");

        ComplexMatrix matrix = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("1"), new BigInteger("2")},
                                                                    {new BigInteger("3"), new BigInteger("4")}});
        ComplexNumber scalar = new ComplexNumber(2, 0);
        ComplexMatrix result = matrix.mult(scalar);

        System.out.println("Expected Result:");
        System.out.println("2\t4");
        System.out.println("6\t8");

        System.out.println("Actual Result:");
        System.out.println(result);
        System.out.println();
    }

    private static void testScalarAddition() {
        System.out.println("Testing Scalar Addition:");

        ComplexMatrix matrix = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("1"), new BigInteger("2")},
                                                                    {new BigInteger("3"), new BigInteger("4")}});
        ComplexNumber scalar = new ComplexNumber(2, 0);
        ComplexMatrix result = matrix.add(scalar);

        System.out.println("Expected Result:");
        System.out.println("3\t4");
        System.out.println("5\t6");

        System.out.println("Actual Result:");
        System.out.println(result);
        System.out.println();
    }

    private static void testScalarSubtraction() {
        System.out.println("Testing Scalar Subtraction:");

        ComplexMatrix matrix = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("5"), new BigInteger("8")},
                                                                    {new BigInteger("3"), new BigInteger("2")}});
        ComplexNumber scalar = new ComplexNumber(2, 0);
        ComplexMatrix result = matrix.sub(scalar);

        System.out.println("Expected Result:");
        System.out.println("3\t6");
        System.out.println("1\t0");

        System.out.println("Actual Result:");
        System.out.println(result);
        System.out.println();
    }

    private static void testTensorProduct() {
        System.out.println("Testing Tensor Product:");

        ComplexMatrix matrixA = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("1"), new BigInteger("2")},
                                                                    {new BigInteger("3"), new BigInteger("4")}});
        ComplexMatrix matrixB = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("5"), new BigInteger("6")},
                                                                    {new BigInteger("7"), new BigInteger("8")}});
        ComplexMatrix result = matrixA.tensor(matrixB);

        System.out.println("Expected Result:");
        System.out.println("5\t6\t10\t12");
        System.out.println("7\t8\t14\t16");
        System.out.println("15\t18\t20\t24");
        System.out.println("21\t24\t28\t32");

        System.out.println("Actual Result:");
        System.out.println(result);
        System.out.println();
    }

    private static void testDeterminant() {
        System.out.println("Testing Determinant:");

        ComplexMatrix matrix = ComplexMatrix.fromArray(new BigInteger[][]{{new BigInteger("1"), new BigInteger("2")},
                                                                    {new BigInteger("3"), new BigInteger("4")}});
        ComplexNumber determinant = matrix.det();

        System.out.println("Expected Determinant: -2");
        System.out.println("Actual Determinant: " + determinant);
        System.out.println();
    }
}
