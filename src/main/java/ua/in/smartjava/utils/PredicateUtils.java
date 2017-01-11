package ua.in.smartjava.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.function.Predicate;

import ua.in.smartjava.domain.flight.Flight;

public final class PredicateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredicateUtils.class);

    private PredicateUtils() throws Exception {
        throw new Exception("Constructor not allowed");
    }

    public static final Predicate<Flight> notEmptyHexPredicate = (flight -> !StringUtils.isEmpty(flight.getHex()));
    public static final Predicate<Flight> notEmptyLatPredicate = (flight -> !StringUtils.isEmpty(flight.getLat()));
    public static final Predicate<Flight> notEmptyLonPredicate = (flight -> !StringUtils.isEmpty(flight.getLon()));
    public static final Predicate<Flight> isNumberLatPredicate = (flight -> (Float.parseFloat(flight.getLat()) > 0));
    public static final Predicate<Flight> isNumberLonPredicate = (flight -> (Float.parseFloat(flight.getLon()) > 0));

    public static final Predicate<Flight> correctlyFilledFlightPredicate = (flight -> {
        boolean result;
        try {
            result = notEmptyHexPredicate
                    .and(notEmptyLatPredicate)
                    .and(notEmptyLonPredicate)
                    .and(isNumberLatPredicate)
                    .and(isNumberLonPredicate).test(flight);
        } catch (NumberFormatException ex) {
            result = false;
        }
        if (!result) {
            LOGGER.error("Not well formed flight {}", flight);
        }
        return result;
    });
}