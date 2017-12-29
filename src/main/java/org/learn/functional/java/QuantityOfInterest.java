package org.learn.functional.java;

public interface QuantityOfInterest {
	String getName();

	/**
	 * @param time month between 1-12
	 */
	double valueAt(final int time);
}
