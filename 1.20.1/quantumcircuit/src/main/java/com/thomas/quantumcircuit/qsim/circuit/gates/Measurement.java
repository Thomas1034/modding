package com.thomas.quantumcircuit.qsim.circuit.gates;

import java.util.Random;

import com.thomas.quantumcircuit.qsim.circuit.CircuitModifier;
import com.thomas.quantumcircuit.qsim.circuit.StateVector;

public class Measurement implements CircuitModifier {

	private int target;
	private Random random;
	
	public Measurement(int numQubits, int target, Random rand) {
		this.target = target;
		this.random = rand;
	}

	@Override
	public StateVector apply(StateVector state) {
		return state.measure(target, random.nextDouble());
	}

}
