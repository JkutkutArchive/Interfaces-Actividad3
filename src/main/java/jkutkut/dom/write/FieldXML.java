package jkutkut.dom.write;

public class FieldXML {
    private final String name;
    private final Object value;

    public FieldXML(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getStringValue() {
        return value.toString();
    }
}
