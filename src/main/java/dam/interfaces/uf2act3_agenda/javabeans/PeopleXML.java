package dam.interfaces.uf2act3_agenda.javabeans;

import dam.interfaces.uf2act3_agenda.model.Person;
import javafx.collections.ObservableList;
import jkutkut.dom.write.NodeXML;

import java.util.ArrayList;

/**
 * JavaBean representing the People class in a XML file.
 *
 * @author jkutkut
 */
public class PeopleXML implements NodeXML {
    private final ArrayList<PersonXML> lstPeople;

    public PeopleXML(ObservableList<Person> people) {
        this.lstPeople = new ArrayList<>(people.size());
        for (Person person : people) {
            this.lstPeople.add(new PersonXML(person));
        }
    }

    // ********** GETTERS **********
    public ArrayList<PersonXML> getPeople() {
        return lstPeople;
    }

    // ********** NodeXML **********
    @Override
    public String nodeName() {
        return "people";
    }

    @Override
    public Object[] nodeValues() {
        Object[] people = new Object[this.lstPeople.size()];
        for (int i = 0; i < people.length; i++) {
            people[i] = this.lstPeople.get(i);
        }
        return people;
    }
}