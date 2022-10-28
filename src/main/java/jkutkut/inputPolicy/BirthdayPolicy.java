package jkutkut.inputPolicy;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Policy to validate a person.
 *
 * @author jkutkut
 */
public class BirthdayPolicy extends DatePolicy {

    private static final String POLICY_NAME = "Birthday";
    private static final int MAX_AGE = 150;

    // ******** Tests ********
    protected static final Predicate<String> notFuture = d -> {
        if (d == null) return false;
        String[] date = d.split("/");
        if (date.length != 3) return false;
        try {
            int day = Integer.parseInt(date[DAY_IDX]);
            int month = Integer.parseInt(date[MONTH_IDX]);
            int year = Integer.parseInt(date[YEAR_IDX]);

            // Current date
            LocalDate now = LocalDate.now();
            int nowDay = now.getDayOfMonth();
            int nowMonth = now.getMonthValue();
            int nowYear = now.getYear();

            // Check if the date is in the future
            if (year > nowYear) return false;
            if (year < nowYear) return true;
            if (month > nowMonth) return false;
            if (month < nowMonth) return true;
            return day <= nowDay;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    protected static final Predicate<String> notTooOld = d -> {
        if (d == null) return false;
        String[] date = d.split("/");
        if (date.length != 3) return false;
        try {
            int year = Integer.parseInt(date[YEAR_IDX]);

            // Current date
            LocalDate now = LocalDate.now();
            int nowYear = now.getYear();

            // Check if the date is too old
            return nowYear - year <= MAX_AGE;
        } catch (NumberFormatException e) {
            return false;
        }
    };

    @Override
    protected void addTests() {
        super.addTests();
        addTest(notFuture, String.format("%s cannot be in the future", getPolicyName()));
        addTest(notTooOld, String.format("%s cannot be older than %d years", getPolicyName(), getMaxAge()));
    }

    // ******** GETTERS ********
    protected String getPolicyName() {
        return POLICY_NAME;
    }

    protected int getMaxAge() {
        return MAX_AGE;
    }
}
