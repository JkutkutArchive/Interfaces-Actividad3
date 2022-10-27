package jkutkut.inputPolicy;


import java.util.ArrayList;
import java.util.function.Predicate;

import jkutkut.exception.InvalidDataException;

/**
 * @author Jkutkut
 */
public class InputPolicy {

    /**
     * The maximum percent of similarity between 2 strings.
     */
    private static final double SIMILARITY_THRESHOLD = 0.35;

    /**
     * Numbers.
     */
    public static final String NUMBERS = "0123456789";

    /**
     * Lowercase letters.
     */
    public static final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyzáéíóúäëïöü";

    /**
     * Uppercase letters.
     */
    public static final String UPPER_LETTERS = LOWER_LETTERS.toUpperCase();

    /**
     * Arraylist with the different rules to check.
     */
    protected final ArrayList<Predicate<String>> tests;
    /**
     * Messages to show when the test on the same index fails.
     */
    protected final ArrayList<String> testsMsgs;

    /**
     * Strings that can not be similar to the password.
     *
     * @see #SIMILARITY_THRESHOLD
     */
    protected final ArrayList<String> distinctStrings;
    /**
     * Messages to show when the password is too similar to the one at the same index in distinctStrings.
     *
     * @see #distinctStrings
     */
    protected final ArrayList<String> distinctStringsMsgs;

    /**
     * ArrayList of strings that at least one of the characters inside must be in.
     * <br>
     * Example password "123456%" is valid if the array contains "%@", but not if password is "123456".
     */
    protected final ArrayList<String> containsAtLeast;
    /**
     * Messages to show when the password does not contain any character from the set at the same index in containsAtLeast.
     *
     * @see #containsAtLeast
     */
    protected final ArrayList<String> containsAtLeastMsgs;

    public InputPolicy() {
        tests = new ArrayList<Predicate<String>>();
        distinctStrings = new ArrayList<String>();
        containsAtLeast = new ArrayList<String>();

        testsMsgs = new ArrayList<String>();
        distinctStringsMsgs = new ArrayList<String>();
        containsAtLeastMsgs = new ArrayList<String>();
    }

    // GETTERS

    /**
     * Returns if the given password is valid.
     * @param password The password to check.
     * @return True if the password is valid, false otherwise.
     */
    public boolean isValid(String password) {
        int i;
        for (i = 0; i < tests.size(); i++)
            if (!tests.get(i).test(password))
                return false;

        for (i = 0; i < distinctStrings.size(); i++)
            if (stringsAlike(password, distinctStrings.get(i)))
                return false;

        for (i = 0; i < containsAtLeast.size(); i++)
            if (!password.matches(String.format(".*[%s].*", containsAtLeast.get(i))))
                return false;

        String all = "";
        for (i = 0; i < containsAtLeast.size(); i++)
            all += containsAtLeast.get(i);
        if (!password.matches(String.format("[%s]*", all)))
            return false;
        return true;
    }

    /**
     * Tests if the given string is valid. If not, throws an InvalidDataException with the error message.
     * <br>
     * Follows the same rules as isValid.
     *
     * @param password The password to check.
     * @throws InvalidDataException Error that makes the password invalid.
     * @see #isValid(String)
     * @see InvalidDataException
     */
    public void validate(String password) throws InvalidDataException {
        int i;
        for (i = 0; i < tests.size(); i++)
            if (!tests.get(i).test(password))
                throw new InvalidDataException(testsMsgs.get(i));

        for (i = 0; i < distinctStrings.size(); i++)
            if (stringsAlike(password, distinctStrings.get(i)))
                throw new InvalidDataException(distinctStringsMsgs.get(i));

        for (i = 0; i < containsAtLeast.size(); i++)
            if (!password.matches(String.format(".*[%s].*", containsAtLeast.get(i))))
                throw new InvalidDataException(containsAtLeastMsgs.get(i));
        String all = "";
        for (i = 0; i < containsAtLeast.size(); i++)
            all += containsAtLeast.get(i);
        if (!password.matches(String.format("[%s]*", all)))
            throw new InvalidDataException("Password can only contain the following chars: " + all);
    }

    /**
     * Returns a multiline string with all the errors of the string given.
     * If all the tests pass, returns null.
     * @param str The string to check.
     * @return A multiline string with all the errors of the string given.
     */
    public String testAll(String str) {
        String errors = "";
        int i;

        for (i = 0; i < tests.size(); i++)
            if (!tests.get(i).test(str))
                errors += testsMsgs.get(i) + "\n";
        for (i = 0; i < distinctStrings.size(); i++)
            if (stringsAlike(str, distinctStrings.get(i)))
                errors += distinctStringsMsgs.get(i) + "\n";
        for (i = 0; i < containsAtLeast.size(); i++)
            if (!str.matches(String.format(".*[%s].*", containsAtLeast.get(i))))
                errors += containsAtLeastMsgs.get(i) + "\n";
        String all = "";
        for (i = 0; i < containsAtLeast.size(); i++)
            all += containsAtLeast.get(i);
        if (all.length() > 0 && !str.matches(String.format("[%s]*", all)))
            errors += "Password can only contain the following chars: " + all + "\n";

        return errors;
    }

    /**
     * Tests if the given password is valid, having in mind the user given.
     * <br>
     * Follows the same rules as isValid.
     * @param password The password to check.
     * @param user The user to use to verify the password.
     * @return True if the password is valid, false otherwise.
     *
     * @see #isValid(String)
     */
    public boolean isValid(String password, String user) {
        return this.isValid(password) && !stringsAlike(user, password);
    }

    /**
     * Checks if the given string is valid, having in mind the user given.
     * <br>
     * Follows the same rules as isValid, throwing an InvalidDataException if not valid.
     * @param password The password to check.
     * @param user The user to use to verify the password.
     * @throws InvalidDataException Error that makes the password invalid.
     *
     * @see #validate(String)
     * @see InvalidDataException
     */
    public void validate(String password, String user) {
        this.validate(password);
        if (stringsAlike(user, password))
            throw new InvalidDataException("Password cannot be similar to the user name");
    }

    // SETTERS

    /**
     * Adds a test to the list of tests to verify.
     * @param test The test to add. Should return true if the password is valid, false otherwise.
     * @param msg The message to display if the test fails.
     */
    public void addTest(Predicate<String> test, String msg) {
        if (test == null)
            throw new InvalidDataException("Test cannot be null");
        if (msg == null)
            throw new InvalidDataException("Message cannot be null");
        if (tests.contains(test))
            return;
        tests.add(test);
        testsMsgs.add(msg);
    }

    /**
     * Adds a string to the list of strings that cannot be found in the password.
     * @param string The string to add.
     * @param msg The message to display if the string is found in the password.
     */
    public void addDistinctString(String string, String msg) {
        if (string == null)
            throw new InvalidDataException("String cannot be null. Use the tests to check this.");
        if (msg == null)
            throw new InvalidDataException("Message cannot be null");
        if (distinctStrings.contains(string))
            return;
        distinctStrings.add(string);
        distinctStringsMsgs.add(msg);
    }

    /**
     * Adds a string to the list of strings that must be found in the password.
     * @param string The string to add.
     * @param msg The message to display if the string is not found in the password.
     */
    public void addContainsAtLeast(String string, String msg) {
        if (string == null)
            throw new InvalidDataException("String cannot be null. Use the tests to check this.");
        if (msg == null)
            throw new InvalidDataException("Message cannot be null");
        if (containsAtLeast.contains(string))
            return;
        containsAtLeast.add(string);
        containsAtLeastMsgs.add(msg);
    }

    // TOOLS

    /**
     * Checks if the given string is similar to the given other string.
     * @param s1 The first string.
     * @param s2 The second string.
     * @return True if the strings are similar, false otherwise.
     *
     * @see #similarity(String, String)
     * @see #SIMILARITY_THRESHOLD
     */
    protected static boolean stringsAlike(String s1, String s2) {
        return similarity(s1, s2) > SIMILARITY_THRESHOLD;
    }

    /**
     * Calculates the similarity between two strings.
     * @param s1 The first string.
     * @param s2 The second string.
     * @return The similarity percentage (0-1) between the two strings.
     */
    private static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0)
            return 1.0; // Equal legth
        return (longerLength - levenshteinDist(longer, shorter)) / (double) longerLength;
    }

    /**
     * Calculates the Levenshtein distance between two strings.
     * <br>
     * Based on the algorithm found here:
     * <br>
     * http://rosettacode.org/wiki/Levenshtein_distance#Java
     * @param s1 The first string.
     * @param s2 The second string.
     * @return The Levenshtein distance between the two strings.
     */
    private static int levenshteinDist(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0, j, lastValue, newValue; i <= s1.length(); i++) {
            lastValue = i;
            for (j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}
