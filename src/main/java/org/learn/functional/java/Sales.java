package org.learn.functional.java;

public class Sales implements QuantityOfInterest {

	// We inject the specific operation via constructor; akin to
	// strategy pattern
	private final FunctionOverTime computation;

	public Sales(FunctionOverTime computation) {
		this.computation = computation;
	}

	@Override
	public String getName() {
		return "Sales";
	}

	@Override
	public double valueAt(int time) {
		return computation.valueAt(time);
	}
}
