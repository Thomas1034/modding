package com.thomas.quantumcircuit.qsim.circuit.gates;

import com.thomas.quantumcircuit.qsim.circuit.QuantumGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;
import com.thomas.quantumcircuit.qsim.complex.ComplexNumber;

public class SwapGate extends QuantumGate {
	public SwapGate(int numQubits, int target, int control) {
		super(ComplexMatrix.zero(4).set(0, 0, ComplexNumber.ONE).set(1, 2, ComplexNumber.ONE).set(2, 1, ComplexNumber.ONE).set(3, 3, ComplexNumber.ONE), numQubits, target, control);
	}
}