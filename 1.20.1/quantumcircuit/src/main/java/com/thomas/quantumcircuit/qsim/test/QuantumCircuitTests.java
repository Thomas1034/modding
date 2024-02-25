package com.thomas.quantumcircuit.qsim.test;

import java.util.Random;

import com.thomas.quantumcircuit.qsim.circuit.QuantumCircuitSimulator;

public class QuantumCircuitTests {

	public static void run() {
		smallHadamardTest(100);
		largeHadamardTest(100);
		smallEntanglingTest(100);
		largeEntanglingTest(100);
		teleportationTest(100);
	}

	private static void smallHadamardTest(int tries) {
		System.out.println("Running Hadamard test on a single qubit:");
		QuantumCircuitSimulator qc = new QuantumCircuitSimulator(1, new Random());
		qc.h(0);
		testCircuit(qc, tries);
	}

	private static void largeHadamardTest(int tries) {
		System.out.println("Running Hadamard test on a 4 qubit system, expect equal probabilities:");
		QuantumCircuitSimulator qc = new QuantumCircuitSimulator(4, new Random());
		qc.h(0);
		qc.h(1);
		qc.h(2);
		qc.h(3);
		testCircuit(qc, tries);
	}

	private static void smallEntanglingTest(int tries) {
		System.out.println("Running entangling test on a pair of qubits:");
		QuantumCircuitSimulator qc = new QuantumCircuitSimulator(2, new Random());
		qc.h(0);
		qc.cx(1, 0);
		testCircuit(qc, tries);
	}

	private static void largeEntanglingTest(int tries) {
		System.out.println("Running entangling test on 4 qubits:");
		QuantumCircuitSimulator qc = new QuantumCircuitSimulator(4, new Random());
		qc.h(0);
		qc.cx(1, 0);
		qc.cx(2, 0);
		qc.cx(3, 0);
		testCircuit(qc, tries);
	}

	private static void teleportationTest(int tries) {
		System.out.println("Running teleportation test on three qubits:");
		QuantumCircuitSimulator qc = new QuantumCircuitSimulator(3, new Random());
		// Alice has qubit 0 and 1
		// Bob has qubit 2

		// We'll use 1 and 2 as the communication pair, and set the initial state as
		// follows:
		double chance_of_one = 0.25;
		qc.ry(0, Math.acos(Math.sqrt(chance_of_one)));

		// Entangle 1 and 2 into the Hadamard-0 bell state
		qc.h(1);
		qc.cx(2, 1);
		// Now CNOT 0 onto 1.
		qc.cx(1, 0);
		// Hadamard 0.
		qc.h(0);
		// Now measure 0 and 1, making them classical.
		qc.measure(0);
		qc.measure(1);
		// CNOT 1 onto 2
		qc.cx(2, 1);
		// CZ 0 onto 2
		qc.cz(2, 0);

		// And, hopefully, 0 is now sent to 2.

		testCircuit(qc, tries);
	}

	private static void testCircuit(QuantumCircuitSimulator qc, int tries) {

		int[] successes = new int[qc.countQubits()];
		for (int i = 0; i < tries; i++) {
			if (i % 10 == 0) {
			}
			boolean[] temp = qc.run();
			// System.out.println("Test " + i + " produced " + Arrays.toString(temp));
			for (int j = 0; j < temp.length; j++) {
				successes[j] += temp[j] ? 1 : 0;
			}
		}

		for (int i = 0; i < successes.length; i++) {
			System.out.println("Qubit[" + i + "] was true: " + successes[i] + "/" + tries);
		}
	}

}
