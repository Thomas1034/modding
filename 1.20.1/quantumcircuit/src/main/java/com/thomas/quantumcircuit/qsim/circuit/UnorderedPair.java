package com.thomas.quantumcircuit.qsim.circuit;

import java.util.Objects;

public record UnorderedPair(int x, int y) {
	
	// Order does not matter.
	@Override
	public int hashCode() {
		return Objects.hash(Integer.hashCode(Math.max(y, x)), Integer.hashCode(Math.min(y, x)));
	}
	
	// Override equality.
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof UnorderedPair that) {
			
			return Math.max(this.y, this.x) == Math.max(that.y, that.x) && Math.min(this.y, this.x) == Math.min(that.y, that.x);
		}
		return false;
		
	}
	
}
