package org.learn.functional.java;

abstract class PolynomialQuantity implements QuantityOfInterest {
	private final double[] coefficients;

	PolynomialQuantity(double[] coefficients) {
		this.coefficients = coefficients;
	}

	public double valueAt(int time) {
		double result = 0.0;
		for (int i = 0; i < coefficients.length; i++) {
			result += coefficients[i] * Math.pow(time,i);
		}
		return result;
	}
}
