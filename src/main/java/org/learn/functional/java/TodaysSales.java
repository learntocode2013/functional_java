package org.learn.functional.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingDouble;

public class TodaysSales {
	static final Logger logger = LoggerFactory.getLogger("TodaysSales");
	static final List<Sale> sales = Arrays.asList(
		new Sale(Store.BANGALORE, LocalDateTime.now(), Optional.of("Jessica"),
				 Arrays.asList(
				 		new Item("carrot", 12.0),
						new Item("honey", 250.21),
						new Item("horlicks", 299.99)
				 )),
		new Sale(Store.BANGALORE, LocalDateTime.now(), Optional.of("Kyle Simpson"),
				Arrays.asList(
						new Item("apples", 190.0),
						new Item("corn flakes", 250.21),
						new Item("olives", 599.99)
				)),
		new Sale(Store.MUMBAI, LocalDateTime.now(), Optional.of("Disen"),
				Arrays.asList(
						new Item("Keyboard", 11921.1),
						new Item("Watch", 2999.1),
						new Item("honey", 299.99)
				)),
		new Sale(Store.CHENNAI, LocalDateTime.now(), Optional.of("Venkat"),
				Arrays.asList(
					new Item("UPS", 11921.1),
					new Item("Monitor", 2999.1)
				)),
		new Sale(Store.CHENNAI, LocalDateTime.now(), Optional.of("Neal"),
				Arrays.asList(
					new Item("Computer", 45000.200),
					new Item("Mouse", 2999.1)
				))
	);

	static private Stream<Sale> saleStream() {
		return RandomSale.saleStream(100000);
	}

	public static void main(String[] args) {
		// How many sales were there today ?
		final Predicate<Sale> soldToday = sale -> sale.getSoldOn().toLocalDate().isEqual(LocalDate.now());
		final long totalNumberOfSales = saleStream()
				.filter(soldToday)
				.count();
		logger.info(String.format("There were %d sales today",totalNumberOfSales));

		/*
		// Retrieve the list of customers who spent above 4000 rupees
		logger.info("--- The following customers shopped for 4000 or more today");
		saleStream()
				.filter(sale -> sale.getTotal() >= 4000)
				.map(sale -> sale.getCustomer())
				.forEach(customer -> logger.info(customer.orElse("")));

		// Print a summary of the Sales data...
		logger.info("---- Sales summary --- ");
		logger.info(
				saleStream()
				.mapToDouble(sale -> sale.getTotal())
				.summaryStatistics()
				.toString());

		// How many items were sold today ?
		Supplier<Stream<Item>> itemStreamSupplier = () -> saleStream().flatMap(sale -> sale.getItemsSold().stream());
		logger.info("--- Number of items sold today: " + itemStreamSupplier.get().count());

		// How many different items were sold today ?
		logger.info("--- Number of distinct items sold: " +
				itemStreamSupplier.get()
						.map(item -> item.getItemName())
						.distinct()
						.count());
		// List the names of all the items sold
		final String distinctItems = itemStreamSupplier.get()
				.map(item -> item.getItemName())
				.distinct()
				.collect(Collectors.joining(" , "));

		logger.info("---- Distinct items sold today: " + distinctItems);
		*/

		// Summarize the sales by store location
		final Map<Store, DoubleSummaryStatistics> summary = saleStream()
				.collect(groupingBy(Sale::getSourceStore, summarizingDouble(Sale::getTotal)));
		summary.entrySet()
				.stream()
				.forEach(entry -> logger.info(entry.getKey() + " : " + entry.getValue()));

		// Summarize by processing thread
		final Map<String, DoubleSummaryStatistics> statsByThread = saleStream()
				.parallel()
				.collect(groupingBy(sale -> Thread.currentThread().getName(),
									summarizingDouble(Sale::getTotal)));

		statsByThread.keySet()
				.stream()
				.sorted()
				.forEach(thread -> logger.info(thread + " - " + statsByThread.get(thread)));

	}
}
