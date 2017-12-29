package org.learn.functional.java;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Sale {
	private final Store sourceStore;
	private final LocalDateTime soldOn;
	private final Optional<String> customer;
	private final List<Item> itemsSold;

	public Sale(Store sourceStore,
	            LocalDateTime soldOn,
	            Optional<String> customer,
	            List<Item> itemsSold) {
		this.sourceStore = sourceStore;
		this.soldOn = soldOn;
		this.customer = customer;
		this.itemsSold = itemsSold;
	}

	public Store getSourceStore() {
		return sourceStore;
	}

	public LocalDateTime getSoldOn() {
		return soldOn;
	}

	public Optional<String> getCustomer() {
		return customer;
	}

	public List<Item> getItemsSold() {
		return itemsSold;
	}

	public double getTotal() {
		return getItemsSold()
				.stream()
				.mapToDouble(item -> item.getSellingPrice())
				.sum();
	}
}
