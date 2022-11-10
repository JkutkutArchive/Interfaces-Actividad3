package dam.interfaces.uf2act3_agenda.util;

import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import dam.interfaces.uf2act3_agenda.javabeans.PeopleXML;
import dam.interfaces.uf2act3_agenda.javabeans.PersonXML;
import dam.interfaces.uf2act3_agenda.model.Person;
import jkutkut.dom.write.WriteXML;
import jkutkut.exception.InvalidDataException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.collections.ObservableList;
import com.thoughtworks.xstream.XStream;

/**
 * Class with the logic needed to save and load the data using XML files.
 *
 * @author jkutkut
 */
public class XMLPersonParser {
    /**
     * Loads Person objects from a XML file to the given list.
     * @param xmlFilename The XML file to load the data from.
     * @param people The list to load the data to.
     * @throws InvalidDataException if an error occurs.
     */
    public static void loadPeople(String xmlFilename, ObservableList<Person> people) {
        people.clear();
        if (xmlFilename == null)
            return;
        PeopleXML peopleXML = readPeople(xmlFilename);
        if (peopleXML == null)
            throw new InvalidDataException("Error reading XML file.\nFile: " + xmlFilename);
        for (PersonXML p : peopleXML.getPeople()) {
            people.add(p.toPerson());
        }
    }

    /**
     * Reads the data from a XML file and returns a PeopleXML object.
     * @param xmlFilename The XML file to read the data from.
     * @return A PeopleXML object with the data read from the XML file.
     * @throws InvalidDataException if an error occurs.
     */
    private static PeopleXML readPeople(String xmlFilename) {
        XStream xstream = new XStream();

        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);

        xstream.allowTypes(new Class[]{PersonXML.class, PeopleXML.class});

        xstream.allowTypesByWildcard(new String[] {
            "dam.interfaces.uf2act3_agenda.javabeans.**"
        });

        xstream.alias("people", PeopleXML.class);
        xstream.alias("person", PersonXML.class);

        xstream.addImplicitCollection(PeopleXML.class, "lstPeople"); // Collection name

        try {
            FileInputStream fis = new FileInputStream(xmlFilename);
            return (PeopleXML) xstream.fromXML(fis);
        }
        catch (FileNotFoundException e) {
            throw new InvalidDataException("File not found: " + xmlFilename);
        }
    }

    /**
     * Saves the data from the given list to a XML file.
     * @param f The XML file to save the data to.
     * @param personData The list to save the data from.
     * @throws InvalidDataException if an error occurs.
     */
    public static void savePeople(String f, ObservableList<Person> personData) {
        if (f == null)
            throw new InvalidDataException("Not able to save file.\nFile: null");
        try {
            WriteXML.generateXML(f, new PeopleXML(personData));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new InvalidDataException("Not able to save file.\nFile: " + f);
        }
    }
}
