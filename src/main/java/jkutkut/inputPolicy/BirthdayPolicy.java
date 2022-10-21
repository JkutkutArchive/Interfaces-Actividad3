package jkutkut.inputPolicy;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class BirthdayPolicy extends InputPolicy {
    protected static final int DAY_IDX = 0;
    protected static final int MONTH_IDX = 1;
    protected static final int YEAR_IDX = 2;

    // Tools

    protected static final BiFunction<Integer, Integer, Integer> daysOfMonth = (month, year) -> {
        if (month == 2) {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
                return 29;
            return 28;
        }
        else if (month == 4 || month == 6 || month == 9 || month == 11)
            return 30;
        return 31;
    };

    // Tests

    protected static final Predicate<String> validDateFormat = d -> {
        if (d == null) return false;
        return "\\d{2}/\\d{2}/\\d{4}".matches(d);
    };

    protected static final Predicate<String> validDateIntegers = d -> {
        if (d == null) return false;
        String[] date = d.split("/");
        try {
            Integer.parseInt(date[0]);
            Integer.parseInt(date[1]);
            Integer.parseInt(date[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    };

    protected static final Predicate<String> validDate = d -> {
        if (d == null) return false;
        String[] date = d.split("/");
        try {
            int day = Integer.parseInt(date[DAY_IDX]);
            int month = Integer.parseInt(date[MONTH_IDX]);
            int year = Integer.parseInt(date[YEAR_IDX]);

            if (month < 1 || month > 12) return false;
            if (day < 1 || day > daysOfMonth.apply(month, year)) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    };

    public BirthdayPolicy() {
        super();
        addTests();
    }

    private void addTests() {
        addTest(Objects::nonNull, "Date cannot be null");
        addTest(validDateFormat, "Date must be in the format dd/mm/yyyy");
        addTest(validDateIntegers, "Date must be in the format dd/mm/yyyy"); // Redundant
        addTest(validDate, "Date is not valid.");
    }
}
