package org.learn.functional.java;

public class Item {
	private final String itemName;
	private final double sellingPrice;

	public Item(String itemName, double sellingPrice) {
		this.itemName = itemName;
		this.sellingPrice = sellingPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}
}
