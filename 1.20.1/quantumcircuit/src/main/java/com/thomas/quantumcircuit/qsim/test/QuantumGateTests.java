package com.thomas.quantumcircuit.qsim.test;

import com.thomas.quantumcircuit.qsim.circuit.QuantumGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.CNOTGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.HGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.IGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;

public class QuantumGateTests {

	public static void run() {
		System.out.println("Starting quantum gate tests...");
		smallIdentityTest();
		largeIdentityTest();
		smallHadamardTest();
		mediumHadamardTest();
		largeHadamardTest();
		smallCNOTTest();
		smallCNOTTest2();
		mediumCNOTTest();
		mediumCNOTTest2();
		mediumCNOTTest3();
		System.out.println("Finished quantum gate tests!");
	}

	private static void smallIdentityTest() {
		System.out.println("Expected result: 2x2 identity");

		QuantumGate qg = new IGate(1, 0);
		System.out.println(qg.asMatrix());
	}

	private static void largeIdentityTest() {
		System.out.println("Expected result: 4x4 identity");
		ComplexMatrix matrix = ComplexMatrix.ident(2);

		QuantumGate qg = new IGate(2, 0);
		System.out.println(qg.asMatrix());
	}

	private static void smallHadamardTest() {
		System.out.println("Expected result: standard Hadamard");
		QuantumGate qg = new HGate(1, 0);
		System.out.println(qg.asMatrix());
	}

	private static void mediumHadamardTest() {
		System.out.println("Expected result: 4x4, with Hadamard on second qubit");
		QuantumGate qg = new HGate(2, 1);
		System.out.println(qg.asMatrix());
	}

	private static void largeHadamardTest() {
		System.out.println("Expected result: 8x8, with Hadamard on second qubit");
		QuantumGate qg = new HGate(3, 1);
		System.out.println(qg.asMatrix());
	}

	private static void smallCNOTTest() {
		System.out.println("Expected result: 4x4, standard CNOT");
		QuantumGate qg = new CNOTGate(2, 0, 1);
		System.out.println(qg.asMatrix());
	}

	private static void smallCNOTTest2() {
		System.out.println("Expected result: 4x4, standard CNOT with bits switched");
		QuantumGate qg = new CNOTGate(2, 1, 0);
		System.out.println(qg.asMatrix());
	}

	private static void mediumCNOTTest() {
		System.out.println("Expected result: 8x8, I cross standard CNOT");
		QuantumGate qg = new CNOTGate(3, 0, 1);
		System.out.println(qg.asMatrix());
	}

	private static void mediumCNOTTest2() {
		System.out.println("Expected result: 8x8, standard CNOT cross I");
		QuantumGate qg = new CNOTGate(3, 1, 2);
		System.out.println(qg.asMatrix());
	}

	private static void mediumCNOTTest3() {
		System.out.println("Expected result: 8x8, CNOT on bits 2 and 0");
		QuantumGate qg = new CNOTGate(3, 2, 0);
		System.out.println(qg.asMatrix());
	}
}
