package com.thomas.quantumcircuit.qsim.test;

import com.thomas.quantumcircuit.qsim.circuit.StateVector;
import com.thomas.quantumcircuit.qsim.circuit.gates.HGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.XGate;

public class StateVectorTests {

	public static void run() {
		System.out.println("Starting state vector tests...");
		test1();
		test2();
		test3();
		test4();
		test5();
		System.out.println("Finished state vector tests!");
	}

	private static void test1() {

		System.out.println("Checking state vector creation.");

		StateVector sv = StateVector.createZeroState(2);

		System.out.println(sv);

	}

	private static void test2() {

		System.out.println("Checking hadamard on |0>.");

		StateVector sv = StateVector.createZeroState(1);
		sv = new HGate(1, 0).apply(sv);

		System.out.println(sv);

	}
	
	private static void test3() {

		System.out.println("Checking hadamard on |1>.");

		StateVector sv = StateVector.createZeroState(1);
		sv = new XGate(1, 0).apply(sv);
		sv = new HGate(1, 0).apply(sv);

		System.out.println(sv);

	}
	
	private static void test4() {

		System.out.println("Checking hadamard on bit 1 of |00>.");

		StateVector sv = StateVector.createZeroState(2);
		sv = new HGate(2, 0).apply(sv);

		System.out.println(sv);

	}
	
	private static void test5() {
		
		System.out.println("Finding the projections of (IxH)|00>");

		StateVector sv = StateVector.createZeroState(2);
		sv = new HGate(2, 0).apply(sv);
		
		StateVector sv0F = sv.project(0, false);
		StateVector sv0T = sv.project(0, true);
		StateVector sv1F = sv.project(1, false);
		StateVector sv1T = sv.project(1, true);
		
		System.out.println("0F\n" + sv0F);
		System.out.println("0T\n" + sv0T);
		System.out.println("1F\n" + sv1F);
		System.out.println("1T\n" + sv1T);
	}
	
}
