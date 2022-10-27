package dam.interfaces.uf2act3_agenda.util;

import jkutkut.inputPolicy.BirthdayPolicy;
import jkutkut.inputPolicy.UserPolicy;

public class PersonPolicy {

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

    private static class LastNamePolicy extends NamePolicy {
        protected static final String POLICY_NAME = "Last Name";

        protected String getPolicyName() {
            return POLICY_NAME;
        }
    }

    private class StreetPolicy extends NamePolicy {
        protected static final String POLICY_NAME = "Street";

        protected String getPolicyName() {
            return POLICY_NAME;
        }
    }

    private class CityPolicy extends NamePolicy {
        protected static final String POLICY_NAME = "City";

        protected String getPolicyName() {
            return POLICY_NAME;
        }
    }

    private NamePolicy namePolicy;
    private BirthdayPolicy birthdayPolicy;
    private LastNamePolicy lastNamePolicy;
    private StreetPolicy streetPolicy;
    private CityPolicy cityPolicy;


    public PersonPolicy() {
        namePolicy = new NamePolicy();
        birthdayPolicy = new BirthdayPolicy();
        lastNamePolicy = new LastNamePolicy();
        streetPolicy = new StreetPolicy();
        cityPolicy = new CityPolicy();
    }

    public String test(String fname, String lname, String street, String city, String sPostalCode, String birthday) {
        StringBuilder sb = new StringBuilder();
        sb.append(namePolicy.testAll(fname) + "\n");
        sb.append(lastNamePolicy.testAll(lname) + "\n");
        try {
            int postalCode = Integer.parseInt(sPostalCode);
            if (postalCode < 10000) {
                sb.append("Postal code must be at least 5 digits long\n");
            }
            else if (postalCode > 99999) {
                sb.append("Postal code must be at most 5 digits long\n");
            }
        } catch (NumberFormatException e) {
            sb.append("Postal code must be a number");
        }
        sb.append(streetPolicy.testAll(street) + "\n");
        sb.append(cityPolicy.testAll(city) + "\n");
        sb.append(birthdayPolicy.testAll(birthday) + "\n");
        String result = sb.toString();
        if (result.trim().isEmpty())
            return "";
        return result;
    }
}
