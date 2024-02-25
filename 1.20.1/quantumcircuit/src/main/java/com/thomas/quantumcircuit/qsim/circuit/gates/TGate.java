package com.thomas.quantumcircuit.qsim.circuit.gates;

import com.thomas.quantumcircuit.qsim.circuit.QuantumGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class TGate extends QuantumGate {

	public TGate(int numQubits, int target) {
		super(ComplexMatrix.zero(2).set(0, 0, ComplexNumber.ONE).set(1, 1, new ComplexNumber(Math.sqrt(2), Math.sqrt(2))), numQubits, target);
	}
}
