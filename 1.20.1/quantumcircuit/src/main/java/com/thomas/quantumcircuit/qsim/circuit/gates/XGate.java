package com.thomas.quantumcircuit.qsim.circuit.gates;

import com.thomas.quantumcircuit.qsim.circuit.QuantumGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class XGate extends QuantumGate {

	public XGate(int numQubits, int target) {
		super(ComplexMatrix.zero(2).set(1, 0, ComplexNumber.ONE).set(0, 1, ComplexNumber.ONE), numQubits, target);
	}
}
