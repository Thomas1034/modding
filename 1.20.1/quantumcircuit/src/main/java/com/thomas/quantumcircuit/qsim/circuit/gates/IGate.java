package com.thomas.quantumcircuit.qsim.circuit.gates;

import com.thomas.quantumcircuit.qsim.circuit.QuantumGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class IGate extends QuantumGate {

	public IGate(int numQubits, int target) {
		super(ComplexMatrix.zero(2).set(0, 0, ComplexNumber.ONE).set(1, 1, ComplexNumber.ONE), numQubits, target);
	}
}
