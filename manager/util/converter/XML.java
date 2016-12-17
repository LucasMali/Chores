package manager.util.converter;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Helper method to build an object from XML
 * 
 * @author Lucas Maliszewski, S019356741
 * @version Nov 25, 2016, CSC-240 Assignment
 */
public class XML {

    private String xml;

    private Document doc;

    // The Hashmap container
    private ArrayList<HashMap> theList = new ArrayList();

    public static XML init() {
        return new XML();
    }

    /**
     * Returns the API XML to a ArrayList
     * 
     * @param nodeType Contains either user or chore string
     * @return ArrayList
     * @throws Exception 
     */
    public ArrayList toList(String nodeType) throws Exception {
        if (this.xml.isEmpty()) {
            throw new Exception("XML cannot be null");
        }

        // This will allow us to access the XML values
        this.buildDoc();

        this.doc.getDocumentElement().normalize();
        NodeList nl = doc.getElementsByTagName(nodeType);

        this.buildTheList(nl);
        return this.theList;
    }

    /**
     * Returns the API XML to a ArrayList
     * 
     * @param xml
     * @param nodeType
     * @return
     * @throws Exception 
     */
    public ArrayList toList(String xml, String nodeType) throws Exception {
        this.xml = xml;
        return toList(nodeType);
    }

    /**
     * Converts the XML to a MAP
     * 
     * @param nodeType
     * @return
     * @throws Exception 
     */
    public Map toMap(String nodeType) throws Exception {
        if (this.xml.isEmpty()) {
            throw new Exception("XML cannot be null");
        }

        // This will allow us to access the XML values
        this.buildDoc();

        this.doc.getDocumentElement().normalize();
        NodeList nl = doc.getElementsByTagName(nodeType);

        return this.buildMap(nl.item(0).getChildNodes());
    }

    /**
     * Converts the XML to a MAP
     * 
     * @param nodeType
     * @return
     * @throws Exception 
     */
    public Map toMap(String xml, String nodeType) throws Exception {
        this.xml = xml;
        return toMap(nodeType);
    }

    /**
     * NOT IMPLEMENT BY ME
     * 
     * This code was given by Stack Overflow.
     * 
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException 
     */
    private void buildDoc() throws SAXException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            this.doc = builder.parse(new InputSource(new StringReader(this.xml)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will build a map based on the XML content given.
     * 
     * @param nl
     * @return 
     */
    private Map buildMap(NodeList nl) {
        Map<String, String> _theMap = new HashMap<>();
        for (int z = 0; z < nl.getLength(); z++) {
            _theMap.put(nl.item(z).getNodeName(), nl.item(z).getTextContent());
        }
        return _theMap;
    }

    /**
     * Builds the primary list for users and chores based off of XML given.
     * 
     * @param nl
     * @return
     * @throws Exception
     */
    private void buildTheList(NodeList nl) throws Exception {
        for (int i = 0; i < nl.getLength(); i++) {
            // Get the contents of the primary containers

            this.theList.add(
                    (HashMap) this.buildMap(
                            nl.item(i).getChildNodes()
                    )
            );
        }
    }

}
