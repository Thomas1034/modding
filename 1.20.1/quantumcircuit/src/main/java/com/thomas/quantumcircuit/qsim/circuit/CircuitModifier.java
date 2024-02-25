package com.thomas.quantumcircuit.qsim.circuit;

public interface CircuitModifier {
	public StateVector apply(StateVector state);
}
