package com.thomas.quantumcircuit.qsim.circuit.gates;

import com.thomas.quantumcircuit.qsim.circuit.QuantumGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class RXGate extends QuantumGate {

	public RXGate(int numQubits, int target, double theta) {
		super(ComplexMatrix.zero(2).set(0, 0, new ComplexNumber(Math.cos(theta / 2), 0))
				.set(1, 1, new ComplexNumber(Math.cos(theta / 2), 0))
				.set(1, 0, new ComplexNumber(0, -Math.sin(theta / 2)))
				.set(0, 1, new ComplexNumber(0, -Math.sin(theta / 2))), numQubits, target);
	}
}
