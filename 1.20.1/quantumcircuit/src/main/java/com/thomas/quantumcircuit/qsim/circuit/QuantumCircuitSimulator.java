package com.thomas.quantumcircuit.qsim.circuit;

import java.util.ArrayList;
import java.util.Random;

import com.thomas.quantumcircuit.qsim.circuit.gates.CNOTGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.CZGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.HGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.IGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.Measurement;
import com.thomas.quantumcircuit.qsim.circuit.gates.RXGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.RYGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.RZGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.SGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.SwapGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.TGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.XGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.YGate;
import com.thomas.quantumcircuit.qsim.circuit.gates.ZGate;
import com.thomas.quantumcircuit.qsim.complex.ComplexMatrix;

public class QuantumCircuitSimulator {

	private ArrayList<CircuitModifier> gates;
	private int numQubits;
	private Random random;

	/**
	 * Initializes a quantum circuit with n qubits to the zero-state.
	 * 
	 * @param n      The number of qubits.
	 * @param random The random source to use.
	 */
	public QuantumCircuitSimulator(int n, Random random) {
		this.numQubits = n;
		this.gates = new ArrayList<>();
		this.gates.add(new IGate(this.numQubits, 0));
		this.random = random;
	}
	
	/**
	 * Initializes a quantum circuit with n qubits to the zero-state.
	 * 
	 * @param n      The number of qubits.
	 */
	public QuantumCircuitSimulator(int n) {
		this.numQubits = n;
		this.gates = new ArrayList<>();
		this.gates.add(new IGate(this.numQubits, 0));
		this.random = new Random();
	}


	/**
	 * Applies a Hadamard gate to qubit q.
	 * 
	 * @param q The qubit to apply the gate to.
	 */
	public void h(int q) {
		QuantumGate g = new HGate(this.numQubits, q);
		this.addGate(g);
	}

	/**
	 * Applies a CNOT gate to qubit q, with control qubit c.
	 * 
	 * @param q The qubit to apply the gate to.
	 * @param c The control qubit.
	 */
	public void cx(int q, int c) {
		QuantumGate g = (new CNOTGate(this.numQubits, q, c));
		this.addGate(g);
	}

	
	/**
	 * Applies an X gate to qubit q.
	 * 
	 * @param q The qubit to apply the gate to.
	 */
	public void x(int q) {
		QuantumGate g = (new XGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Applies a Y gate to qubit q.
	 * 
	 * @param q The qubit to apply the gate to.
	 */
	public void y(int q) {
		QuantumGate g = (new YGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Applies a Z gate to qubit q.
	 * 
	 * @param q The qubit to apply the gate to.
	 */
	public void z(int q) {
		QuantumGate g = (new ZGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Applies an identity gate to qubit q. Included for the sake of completion.
	 * 
	 * @param q The qubit to apply the gate to.
	 */
	public void i(int q) {
		QuantumGate g = (new IGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Applies an RX gate to qubit q.
	 * 
	 * @param q The qubit to apply the gate to.
	 * @param theta The angle of the RX gate.
	 */
	public void rx(int q, double theta) {
		QuantumGate g = (new RXGate(this.numQubits, q, theta));
		this.addGate(g);
	}
	
	/**
	 * Applies an RY gate to qubit q.
	 * 
	 * @param q The qubit to apply the gate to.
	 * @param theta The angle of the RX gate.
	 */
	public void ry(int q, double theta) {
		QuantumGate g = (new RYGate(this.numQubits, q, theta));
		this.addGate(g);
	}
	
	/**
	 * Applies an RZ gate to qubit q.
	 * 
	 * @param q The qubit to apply the gate to.
	 * @param theta The angle of the RX gate.
	 */
	public void rz(int q, double theta) {
		QuantumGate g = (new RZGate(this.numQubits, q, theta));
		this.addGate(g);
	}
	
	/**
	 * Applies an S gate to qubit q.
	 * 
	 * @param q The qubit to apply the gate to.
	 */
	public void s(int q) {
		QuantumGate g = (new SGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Applies a T gate to qubit q.
	 * 
	 * @param q The qubit to apply the gate to.
	 */
	public void t(int q) {
		QuantumGate g = (new TGate(this.numQubits, q));
		this.addGate(g);
	}

	/**
	 * Applies a controlled-Z gate to qubit q, with control qubit c.
	 * 
	 * @param q The qubit to apply the gate to.
	 * @param c The control qubit.
	 */
	public void cz(int q, int c) {
		QuantumGate g = (new CZGate(this.numQubits, q, c));
		this.addGate(g);
	}

	/**
	 * Applies a swap gate to qubits q and c.
	 * 
	 * @param q The first qubit to swap.
	 * @param c The second qubit to swap.
	 */
	public void swap(int q, int c) {
		QuantumGate g = (new SwapGate(this.numQubits, q, c));
		this.addGate(g);
	}

	/**
	 * Applies a measurement to the specified qubit, causing a collapse of the
	 * state.
	 * 
	 * @param q The qubit to measure.
	 * @param r The random source to use.
	 */
	public void measure(int q, Random r) {
		this.gates.add(new Measurement(this.numQubits, q, r));
	}

	/**
	 * Applies a measurement to the specified qubit, causing a collapse of the
	 * state. Uses this circuit's saved random source.
	 * 
	 * @param q The qubit to measure.
	 */
	public void measure(int q) {
		this.gates.add(new Measurement(this.numQubits, q, this.random));
	}

	/**
	 * Runs the quantum circuit, and returns an array of the final values of the
	 * qubits.
	 * 
	 * @return An array of the final values of the qubits.
	 */
	public boolean[] run() {

		StateVector sv = StateVector.createZeroState(this.numQubits);

		// Add measurements.
		for (int i = 0; i < this.numQubits; i++) {
			this.measure(i);
		}

		// Run the circuit.
		for (CircuitModifier cm : this.gates) {
			sv = cm.apply(sv);
		}

		boolean[] vals = new boolean[this.numQubits];

		// Take measurements.
		for (int i = 0; i < this.numQubits; i++) {
			vals[i] = sv.getMeasurement(i);
		}

		return vals;
	}

	private void addGate(QuantumGate g) {

		// Check if the top gate is a gate, not a measurement.
		if (this.gates.get(this.gates.size() - 1) instanceof QuantumGate top) {
			this.gates.set(this.gates.size() - 1, g.asMatrix().mult(top.asMatrix()));
		}
		// Or if it's a matrix, not a measurement.
		else if (this.gates.get(this.gates.size() - 1) instanceof ComplexMatrix top) {
			this.gates.set(this.gates.size() - 1, g.asMatrix().mult(top));
		} else {
			this.gates.add(g);
		}

	}

	/**
	 * Returns the number of qubits that this circuit has.
	 * 
	 * @return The number of qubits that this circuit has.
	 */
	public int countQubits() {
		return this.numQubits;
	}
}
