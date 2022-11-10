package dam.interfaces.uf2act3_agenda.javabeans;

import dam.interfaces.uf2act3_agenda.model.Person;
import dam.interfaces.uf2act3_agenda.util.DateUtil;
import jkutkut.dom.write.FieldXML;
import jkutkut.dom.write.NodeXML;

public class PersonXML implements NodeXML {

    private final String firstName;
    private final String lastName;
    private final String street;
    private final int postalCode;
    private final String city;
    private final String birthday;

    public PersonXML(Person p) {
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.street = p.getStreet();
        this.postalCode = p.getPostalCode();
        this.city = p.getCity();
        this.birthday = DateUtil.format(p.getBirthday());
    }

    public Person toPerson() {
        return new Person(firstName, lastName, street, postalCode, city, DateUtil.parse(birthday));
    }

    @Override
    public String nodeName() {
        return "person";
    }

    @Override
    public Object[] nodeValues() {
        return new Object[] {
            new FieldXML("firstName", firstName),
            new FieldXML("lastName", lastName),
            new FieldXML("street", street),
            new FieldXML("postalCode", postalCode),
            new FieldXML("city", city),
            new FieldXML("birthday", birthday)
        };
    }
}
