package dam.interfaces.uf2act3_agenda.util;

import dam.interfaces.uf2act3_agenda.model.Person;
import jkutkut.inputPolicy.BirthdayPolicy;
import jkutkut.inputPolicy.UserPolicy;

public class PersonPolicy {

    private NamePolicy namePolicy;
    private BirthdayPolicy birthdayPolicy;

    private class NamePolicy extends UserPolicy {
        protected static final int MIN_LENGTH = 2;
        protected static final int MAX_LENGTH = Integer.MAX_VALUE;
    }


    public PersonPolicy() {
        namePolicy = new NamePolicy();
        birthdayPolicy = new BirthdayPolicy();
    }

    public String test(String fname, String lname, String street, String city, String sPostalCode, String birthday) {
        StringBuilder sb = new StringBuilder();
        sb.append(namePolicy.testAll(fname) + "\n");
        sb.append(namePolicy.testAll(lname) + "\n");
        try {
            int postalCode = Integer.parseInt(sPostalCode);
            if (postalCode < 0) {
                sb.append("Postal code must be positive\n");
            }
            else if (postalCode > 99999) {
                sb.append("Postal code must be at most 5 digits long\n");
            }
        } catch (NumberFormatException e) {
            sb.append("Postal code must be a number");
        }
        sb.append(namePolicy.testAll(city) + "\n");
        sb.append(birthdayPolicy.testAll(birthday) + "\n");
        return sb.toString();
    }
}
