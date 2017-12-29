package org.learn.functional.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.stream.Stream;

class OptionalExample {
	static final private Logger logger = LoggerFactory.getLogger( "OptionalExample" );

	private static Stream <Sale> saleStream() {
		return RandomSale.saleStream( 3 );
	}

	// find the first sale entry which sold a monitor
	private static Stream <Sale> findSaleOf(String itemName) {
		return saleStream()
				.filter( sale -> sale.getItemsSold()
						.stream()
						.anyMatch( item -> item.getItemName().equals( itemName ) ) );
	}

	private static Optional <Sale> findFirstSaleOf() {
		return findSaleOf( "carrot" ).findFirst();
	}

	// find the store that sold a carrot
	private static Optional <Store> carrotStore() {
		return findFirstSaleOf().map( Sale::getSourceStore );
	}

	// Customer who bought a carrot
	private static Optional <String> carrotCustomer() {
		// We call a function on the value inside of an optional if it exists.
		// That way, we avoid null checks. The optional does it for us.
		return findFirstSaleOf()
				.flatMap( Sale::getCustomer );
	}

	public static void main(String[] args) {
		final String customerName = carrotCustomer().orElse( "I don't know who" );
		logger.info( customerName + " bought carrots" );
	}

}
