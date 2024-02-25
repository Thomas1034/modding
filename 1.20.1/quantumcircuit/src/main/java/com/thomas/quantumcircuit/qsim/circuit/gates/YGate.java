package com.thomas.quantumcircuit.qsim.circuit.gates;

import com.thomas.quantumcircuit.qsim.circuit.QuantumGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class YGate extends QuantumGate {

	public YGate(int numQubits, int target) {
		super(ComplexMatrix.zero(2).set(0, 1, ComplexNumber.I.neg()).set(1, 0, ComplexNumber.I), numQubits, target);
	}
}
