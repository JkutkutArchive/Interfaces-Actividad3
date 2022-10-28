package dam.interfaces.uf2act3_agenda.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling dates.
 *
 * @author JKutkut
 */
public class DateUtil {
    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Formats the given date into a String.
     *
     * Note: null values are converted to an empty String.
     * Note: The format used is defined in the DATE_PATTERN constant.
     * @param date The date to be returned as a String
     * @return formatted String.
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Converts a String in the format of the DATE_PATTERN to a LocalDate object.
     *
     * @param dateString The date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
