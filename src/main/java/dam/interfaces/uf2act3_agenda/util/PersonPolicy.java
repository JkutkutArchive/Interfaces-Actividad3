package dam.interfaces.uf2act3_agenda.util;

import jkutkut.inputPolicy.BirthdayPolicy;
import jkutkut.inputPolicy.UserPolicy;

import java.util.ArrayList;

/**
 * Policy to validate a person.
 *
 * @author jkutkut
 */
public class PersonPolicy {

    /**
     * Policy to validate the name of a person.
     */
    protected static class NamePolicy extends UserPolicy {
        protected static final String POLICY_NAME = "Name";
        protected static final int MIN_LENGTH = 2;
        protected static final int MAX_LENGTH = Integer.MAX_VALUE;

        protected String getPolicyName() {
            return POLICY_NAME;
        }

        protected int getMinLength() {
            return MIN_LENGTH;
        }

        protected int getMaxLength() {
            return MAX_LENGTH;
        }
    }

    /**
     * Policy to validate the last name of a person.
     */
    private static class LastNamePolicy extends NamePolicy {
        protected static final String POLICY_NAME = "Last Name";

        protected String getPolicyName() {
            return POLICY_NAME;
        }
    }

    /**
     * Policy to validate the street of a person.
     */
    private static class StreetPolicy extends NamePolicy {
        protected static final String POLICY_NAME = "Street";

        protected String getPolicyName() {
            return POLICY_NAME;
        }
    }

    /**
     * Policy to validate the city of a person.
     */
    private static class CityPolicy extends NamePolicy {
        protected static final String POLICY_NAME = "City";

        protected String getPolicyName() {
            return POLICY_NAME;
        }
    }

    private final NamePolicy namePolicy;
    private final BirthdayPolicy birthdayPolicy;
    private final LastNamePolicy lastNamePolicy;
    private final StreetPolicy streetPolicy;
    private final CityPolicy cityPolicy;

    public PersonPolicy() {
        namePolicy = new NamePolicy();
        birthdayPolicy = new BirthdayPolicy();
        lastNamePolicy = new LastNamePolicy();
        streetPolicy = new StreetPolicy();
        cityPolicy = new CityPolicy();
    }

    /**
     * Tests if the values of a person is valid.
     *
     * @param fname First name.
     * @param lname Last name.
     * @param street Street address.
     * @param city City address.
     * @param sPostalCode Postal code address.
     * @param birthday Birthday with format dd/mm/yyyy.
     * @return A string with all the error found, one per line.
     */
    public String test(String fname, String lname, String street, String city, String sPostalCode, String birthday) {
        ArrayList<String> errors = new ArrayList<>();

        errors.add(namePolicy.testAll(fname));
        errors.add(lastNamePolicy.testAll(lname));
        try {
            int postalCode = Integer.parseInt(sPostalCode);
            if (postalCode < 10000) {
                errors.add("Postal code must be at least 5 digits long\n");
            }
            else if (postalCode > 99999) {
                errors.add("Postal code must be at most 5 digits long\n");
            }
        } catch (NumberFormatException e) {
            errors.add("Postal code must be a number");
        }
        errors.add(streetPolicy.testAll(street));
        errors.add(cityPolicy.testAll(city));
        errors.add(birthdayPolicy.testAll(birthday));
        return String.join("\n", errors).trim();
    }
}