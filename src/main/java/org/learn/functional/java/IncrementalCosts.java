package org.learn.functional.java;

public class IncrementalCosts implements QuantityOfInterest {

	private final FunctionOverTime computation;

	public IncrementalCosts(FunctionOverTime computation) {
		this.computation = computation;
	}

	public String getName() {
		return "IncrementalCosts!";
	}

	@Override
	public double valueAt(int time) {
		return computation.valueAt(time);
	}
}
