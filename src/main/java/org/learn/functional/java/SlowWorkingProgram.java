package org.learn.functional.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.learn.functional.java.Timing.timed;

public class SlowWorkingProgram {
	public static void main(String[] args) {
		final Consumer<String> doNotLog = str -> {};
		// We need to log only profit calculation
		final Logger logger = LoggerFactory.getLogger("SlowWorkingProgram");

		final Double costs   = timed("cost calculation", doNotLog, SlowWorkingProgram::calculateCosts);
		final Double revenue = timed("revenue calculation", doNotLog, SlowWorkingProgram::calculateRevenue);
		final Double profit  = timed("profit calculation", logger::info, () -> calculateProfit(costs,revenue));

		System.out.printf("Profit = $ %2f %n",profit);
	}

	private static Double calculateProfit(Double costs, Double revenue) {
		pretendToWorkHard();
		return revenue - costs;
	}

	private static Double calculateRevenue() {
		pretendToWorkHard();
		return 23413.2;
	}

	private static Double calculateCosts() {
		pretendToWorkHard();
		return 4567.3;
	}

	private static void pretendToWorkHard() {
		try {
			Thread.sleep(TimeUnit.SECONDS.toMillis(5));
		} catch (InterruptedException e) {
			// ignore for now
		}
	}
}
