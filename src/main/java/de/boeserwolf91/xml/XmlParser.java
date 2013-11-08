package de.boeserwolf91.xml;

import org.w3c.dom.Node;

import de.boeserwolf91.xml.exception.XmlParseException;

/**
 * interface which should be implemented by container classes managing the class represents by a xml file.
 * The class implementing this interface has to be registered inside the XmlParser (for example DefaultXmlParser)
 * <p/>
 * User: boeserwolf91
 */
public interface XmlParser
{
    /**
     * This method is executed if a node with the specified root tag was found.
     * It adds the node to the specified class
     *
     * @param node the node, which should be added by the class
     */
    public void addNode(Node node) throws XmlParseException;

    /**
     * Method returns the root tag, which the xml file needs
     * to get parsed by this class of XmlParser
     *
     * @return the root tag of the xml file
     */
    public String getRootTag();
}
