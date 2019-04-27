package org.learn.functional.java;

import com.jasongoodwin.monads.Try;
import org.jooq.lambda.Unchecked;

import java.io.Writer;

// Demonstrates the Loan pattern - we can pass in an arbitrary piece of code in an otherwise generic piece of code.
public class OrderExportContentWriter {
    static private final OrderRepository orderRepo = new OrderRepository();

    private static void writeContent(Writer writer) {
        Try<Class<Void>> writePassedOrErr = Try.ofFailable(() -> {
            writer.write(getHeader());
            orderRepo.fetchActiveOrders()
                    .forEach(Unchecked.consumer(order -> writer.write(constructLine(order))));
            return Void.TYPE;
        });
        System.out.println("Was order export successful ? " + writePassedOrErr.isSuccess());
    }

    static private String getHeader() {
        String header = String.join(";", "trackingId", "value");
        return header + System.lineSeparator();
    }

    static private String constructLine(Order order) {
        String line = String.join(",", order.getTrackingId(),
                String.valueOf(order.getValue()));
        return line + System.lineSeparator();
    }

    public static void main(String[] args) {
        Exporter orderExporter = new Exporter();
        orderExporter.exportToFile("orders.csv", OrderExportContentWriter::writeContent);
    }
}
