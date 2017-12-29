package org.learn.functional.java;

public class FixedCosts implements QuantityOfInterest {

	// We inject the specific operation via constructor; akin to
	// strategy pattern
	private final FunctionOverTime computation;

	FixedCosts(FunctionOverTime computation) {
		this.computation = computation;
	}

	public String getName() {
		return "FixedCosts";
	}

	@Override
	public double valueAt(int time) {
		return computation.valueAt(time);
	}
}
