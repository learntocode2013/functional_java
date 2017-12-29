package org.learn.functional.java;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class RandomSale {

	static Stream<Sale> saleStream(int maxSize) {
		return Stream.generate(saleSupplier()).limit(maxSize);
	}

	private static Supplier<Sale> saleSupplier() {
		return () -> new Sale(
				pickAStore(),
				LocalDateTime.now(),
				pickACustomer(),
				randomListOfItems()
		);
	}

	private static Store pickAStore() {
		return stores.get(random.nextInt(stores.size()));
	}

	private static Optional<String> pickACustomer() {
		return Optional.ofNullable(customers.get(random.nextInt(customers.size())));
	}

	private static List<Item> randomListOfItems() {
		return items.stream()
				.limit(random.nextInt(items.size()))
				.collect(toList());
	}

	private static final Random random = new Random();
	private static final List<Store> stores = Arrays.asList(Store.values());
	private static final List<String> customers = Arrays.asList(
			"Jessica",
			//"Disen",
			//"Neal",
			//"Kyle Simpson",
			null
	);
	private static final List<Item> items = Arrays.asList(
			new Item("carrot", 12.0),
			new Item("honey", 250.21),
			new Item("horlicks", 299.99),
			new Item("apples", 190.0),
			new Item("corn flakes", 250.21),
			new Item("olives", 599.99),
			new Item("Keyboard", 11921.1),
			new Item("Watch", 2999.1),
			new Item("honey", 299.99),
			new Item("UPS", 11921.1),
			new Item("Monitor", 2999.1),
			new Item("Computer", 45000.200),
			new Item("Mouse", 2999.1)
	);
}
