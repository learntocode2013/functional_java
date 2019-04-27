package org.learn.functional.java;

import java.util.function.BiFunction;

public class Movie {
    public enum Type {
        REGULAR(PriceService::computeRegularPrice),
        NEW_RELEASE(PriceService::computeNewReleasePrice),
        CHILDREN(PriceService::computeChildrenPrice),
        ELDER(PriceService::computeElderPrice);

        public final BiFunction<PriceService, Integer, Integer> priceAlgo;

        Type(BiFunction<PriceService, Integer, Integer> priceAlgo) {
            this.priceAlgo = priceAlgo;
        }
    }

    private final Type type;

    public Movie(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}

class PriceRepository {
    // In production, this will typically be fetched from the database
    // so that business can adjust the price.
    int getPriceFactor() { return 2; }
}

// Clients will invoke this service to compute the rent for a movie type.
class PriceService {
    // candidate for injection
    private final PriceRepository priceRepo;

    private PriceService(PriceRepository priceRepo) {
        this.priceRepo = priceRepo;
    }

    int computeRegularPrice(int days) {
        return days + 1;
    }

    int computeNewReleasePrice(int days) {
        return priceRepo.getPriceFactor() * days;
    }

    int computeChildrenPrice(int days) {
        return days;
    }

    int computeElderPrice(int daysOnRent) {
        return (priceRepo.getPriceFactor()/2) * daysOnRent;
    }

    private int computePrice(Movie.Type type, int daysOnRent) {
        return type.priceAlgo.apply(this, daysOnRent);
    }

    public static void main(String[] args) {
        PriceService service = new PriceService(new PriceRepository());
        System.out.println("10 days rent for new release: " +
                service.computePrice(Movie.Type.NEW_RELEASE, 10));
        System.out.println("10 days rent for elderly movies: "
                + service.computePrice(Movie.Type.ELDER, 10));
    }
}
