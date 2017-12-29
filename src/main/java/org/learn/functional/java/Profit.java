package org.learn.functional.java;

public class Profit implements QuantityOfInterest {
	// Inputs for the business rule, which is embedded inside valueAt
	// function
	private final Sales sales;
	private final FixedCosts fixedCosts;
	private final IncrementalCosts incrementalCosts;


	Profit(Sales sales,
	              FixedCosts fixedCosts,
	              IncrementalCosts incrementalCosts) {
		this.sales = sales;
		this.fixedCosts = fixedCosts;
		this.incrementalCosts = incrementalCosts;
	}

	public String getName() {
		return "Profit";
	}

	public double valueAt(int time) {
		return  sales.valueAt(time) -
				(fixedCosts.valueAt(time) +
				 incrementalCosts.valueAt(time)
				);
	}
}
