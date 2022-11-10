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

public class XMLPersonParser {
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
