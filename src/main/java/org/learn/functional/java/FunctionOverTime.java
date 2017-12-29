package org.learn.functional.java;

@FunctionalInterface
public interface FunctionOverTime {
	double valueAt(int time);

	static FunctionOverTime monthByMonth(final double[] values) { // values is the data source
		return (time) -> values[time -1];
	}

	static FunctionOverTime constant(final double value) {
		return polynomial(new double[]{value});
	}

	static FunctionOverTime line(final double intercept, final double slope) {
		return polynomial(new double[] {intercept,slope});
	}

	static FunctionOverTime polynomial(final double[] coefficients) {
		return (time) -> {
			double result = 0.0;
			for (int i = 0; i < coefficients.length; i++) {
				result += coefficients[i] * Math.pow(time,i);
			}
			return result;
		};
	}

	@FunctionalInterface
	interface FunctionOf3 {
		double apply(double a, double b, double c);
	}

	static FunctionOverTime combinationOf3(FunctionOverTime sales,
	                                      FunctionOverTime fixedCosts,
	                                      FunctionOverTime incrementalCosts,
	                                      FunctionOf3 operation) {
		return (time) -> operation
				.apply(sales.valueAt(time),
						fixedCosts.valueAt(time),
						incrementalCosts.valueAt(time));

	}
}
