package com.thomas.quantumcircuit.qsim.circuit.gates;

import com.thomas.quantumcircuit.qsim.circuit.QuantumGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class CZGate extends QuantumGate {
	public CZGate(int numQubits, int target, int control) {
		super(ComplexMatrix.zero(4).set(0, 0, ComplexNumber.ONE).set(1, 1, ComplexNumber.ONE).set(2, 2, ComplexNumber.ONE).set(3, 3, ComplexNumber.ONE.neg()), numQubits, target, control);
	}
}