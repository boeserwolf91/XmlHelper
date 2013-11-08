package de.boeserwolf91.xml;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;

/**
 * This interface allows classes to save a xml file easily.
 */
public interface XmlSaver
{
    /**
     * This method returns the path of the file.
     *
     * @return the path of the file
     */
    public String getPath();

    /**
     * This method returns the information whether an existing file should be overwrote or not.
     * If a old file exists and the method doesn't get the permission to overwrite it the new file
     * will be stored under a new name. The name will has this structure: getPath_X.xml
     * The X is the number of the file.
     *
     * @return whether an existing file should be overwrote
     */
    public boolean overwritingOldFiles();

    /**
     * This method returns the name of the root tag of the xml file
     *
     * @return the name of the root tag
     */
    public String getRootTag();

    /**
     * This method returns the priority of the xml file.
     * If this method doesn't return 'null' the root tag will get
     * a priority attribute with this number.
     *
     * @return the priority of this xml file.
     */
    public Integer getPriority();

    /**
     * This method returns other attributes of the root tag.
     * for example the namespace.
     * if the method returns null the root tag doesn't have other attributes.
     *
     * @return the attributes of the root tag
     */
    public Attribute[] getRootAttributes();

    /**
     * This method assumes the control to design the stomach of the xml file.
     * It can use the DefaultXmlSaver to solve this task. The class has a lot of
     * important and helpful methods to create a xml file.
     *
     * @param xmlEventWriter this instance is used to create the xml file. It has to be fed with content
     *
     * @throws XMLStreamException
     */
    public void save(XMLEventWriter xmlEventWriter, XMLEventFactory eventFactory) throws XMLStreamException;
}
