package org.learn.functional.java;

public class Example {
	private final static int MONTHS = 12;
	private final static double[] JAN_TO_DECEMBER_SALES = {
				1000.1, 2000.2, 3000.3, 4000.4, 5000.5,
				6000.6, 7000.7, 8000.8, 9000.9, 10000.10,
				11000.11, 12000.12
	};

	public static void main(String[] args) {
		double totalProfit = 0.0;

		final Sales sales = new Sales(FunctionOverTime.monthByMonth(JAN_TO_DECEMBER_SALES));
		final IncrementalCosts incrementalCosts = new IncrementalCosts(FunctionOverTime.line(51.0,21.1));
		final FixedCosts fixedCosts = new FixedCosts(FunctionOverTime.constant(0.15));
		final Profit profit = new Profit(sales, fixedCosts, incrementalCosts);

		for (int i = 1; i < MONTHS; i++) {
			totalProfit += profit.valueAt(i);
		}

		System.out.printf("Total profit for the year is: %2f %n",totalProfit);
	}
}
