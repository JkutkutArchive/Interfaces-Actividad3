package dam.interfaces.uf2act3_agenda.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author JKutkut
 */
public class Person {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;

    /**
     * Empty constructor.
     */
    public Person() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.street = new SimpleStringProperty("");
        this.postalCode = new SimpleIntegerProperty(0);
        this.city = new SimpleStringProperty("");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1970, 1, 1));
    }

    /**
     * Constructor with attributes defined.
     *
     * @param firstName First name.
     * @param lastName Last name.
     * @param street Street address.
     * @param postalCode Postal code.
     * @param city City.
     * @param year Birthday year.
     * @param month Birthday month.
     * @param day Birthday day.
     */
    public Person(String firstName, String lastName, String street, int postalCode, String city,
                  int year, int month, int day) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.street = new SimpleStringProperty(street);
        this.postalCode = new SimpleIntegerProperty(postalCode);
        this.city = new SimpleStringProperty(city);
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(year, month, day));
    }

    // ********** GETTERS **********

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    // ********** SETTERS **********

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }
}