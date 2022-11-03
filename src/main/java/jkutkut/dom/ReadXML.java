package jkutkut.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class with the logic needed to read an XML file.
 *
 * @author jkutkut - Jorge Re
 */
public class ReadXML {
    /**
     * Arraylist with the tabs stored in heap.
     */
    private static ArrayList<String> tabs = null;

    public static void printDocumentXML(String xmlFilename) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = dbf.newDocumentBuilder();
            Document document = docBuilder.parse(xmlFilename);

            ReadXML.printDocumentXML(document);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the XML file in a human-readable format on the standard output.
     * @param doc Document to print.
     */
    public static void printDocumentXML(Document doc) {
        printDocumentXML(doc, true);
    }

    /**
     * Prints the XML file in a human-readable format on the standard output.
     * @param doc Document to print.
     * @param compact If true, the output will be compacted.
     */
    public static void printDocumentXML(Document doc, boolean compact) {
        printXML(doc.getFirstChild(), 0, compact);
    }

    /**
     * Prints the given node on the standard output.
     * @param node Node to print.
     * @param lvl Level of indentation (0 is no indentation).
     * @param compact If true, the output will be compact.
     *
     */
    public static void printXML(Node node, int lvl, boolean compact) {
        NodeList children = node.getChildNodes();
        Node child;
        if (compact && children.getLength() == 1) {
            String content = children.item(0).getNodeValue().trim();
            printXML(String.format(
                    "<%s>%s</%s>",
                    node.getNodeName(), content, node.getNodeName()
            ), lvl);
            return;
        }
        printXML(String.format("<%s>", node.getNodeName()), lvl);
        for (int i = 0; i < children.getLength(); i++) {
            child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE)
                printXML(child.getNodeValue(), lvl + 1);
            else if (child.getNodeType() == Node.ELEMENT_NODE)
                printXML(child, lvl + 1, compact);
        }
        printXML(String.format("</%s>", node.getNodeName()), lvl);
    }

    /**
     * Prints the given node on the standard output.
     * @param node Node to print.
     * @param lvl Level of indentation (0 is no indentation).
     */
    public static void printXML(Node node, int lvl) {
        printXML(node, lvl, false);
    }

    private static void printXML(String content, int lvl) {
        content = content.trim();
        if (!content.isEmpty())
            System.out.printf("%s%s\n", getTabs(lvl), content);
    }

    /**
     * Returns a string with the given number of tabs.
     * @param lvl Number of tabs.
     * @return String with the given number of tabs.
     */
    private static String getTabs(int lvl) {
        if (tabs == null) {
            tabs = new ArrayList<>();
            tabs.add("");
        }
        while (lvl >= tabs.size())
            tabs.add(tabs.get(tabs.size() - 1) + "  ");
        return tabs.get(lvl);
    }
}

