package jkutkut.inputPolicy;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * Validator for dates given as strings with the format dd/mm/yyyy.
 *
 * @author jkutkut
 */
public class DatePolicy extends InputPolicy {
    protected static final String POLICY_NAME = "Date";

    protected static final int DAY_IDX = 0;
    protected static final int MONTH_IDX = 1;
    protected static final int YEAR_IDX = 2;

    // ******** Tools ********
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

    // ******** Tests ********
    protected static final Predicate<String> validDateFormat = d -> {
        if (d == null) return false;
        return d.matches("\\d{1,2}/\\d{1,2}/\\d{4}");
    };

    protected static final Predicate<String> validDateIntegers = d -> {
        if (d == null) return false;
        String[] date = d.split("/");
        if (date.length != 3) return false;
        try {
            Integer.parseInt(date[DAY_IDX]);
            Integer.parseInt(date[MONTH_IDX]);
            Integer.parseInt(date[YEAR_IDX]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    };

    protected static final Predicate<String> validDate = d -> {
        if (d == null) return false;
        String[] date = d.split("/");
        if (date.length != 3) return false;
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

    public DatePolicy() {
        super();
        addTests();
    }

    protected void addTests() {
        addTest(Objects::nonNull, String.format("%s cannot be null", getPolicyName()));
        addTest(validDateFormat, String.format("%s must be in the format dd/mm/yyyy (regex not met)", getPolicyName()));
        addTest(validDateIntegers, String.format(
                "%s must be in the format dd/mm/yyyy (values not correct)",
                POLICY_NAME
        ));
        addTest(validDate, String.format("%s is not valid.", getPolicyName()));
    }

    // ******** Getters ********
    protected String getPolicyName() {
        return POLICY_NAME;
    }
}
