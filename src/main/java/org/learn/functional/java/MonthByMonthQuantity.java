package org.learn.functional.java;

public abstract class MonthByMonthQuantity implements QuantityOfInterest {
	private final double[] values;

	MonthByMonthQuantity(double[] values) {
		this.values = values;
	}

	public double valueAt(int time) {
		return values[time -1];
	}
}
