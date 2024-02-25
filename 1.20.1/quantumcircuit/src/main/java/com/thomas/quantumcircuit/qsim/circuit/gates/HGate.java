package com.thomas.quantumcircuit.qsim.circuit.gates;

import com.thomas.quantumcircuit.qsim.circuit.QuantumGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class HGate extends QuantumGate {

	public HGate(int numQubits, int target) {
		super(ComplexMatrix.zero(2).add(ComplexNumber.ONE).set(1, 1, ComplexNumber.ONE.neg())
				.mult(new ComplexNumber(1 / Math.sqrt(2), 0)), numQubits, target);
	}
}
