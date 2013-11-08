package de.boeserwolf91.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;

import de.boeserwolf91.xml.exception.XmlParseException;
import de.boeserwolf91.xml.utils.FileUtils;
import de.boeserwolf91.xml.utils.XmlUtils;

public class TestXML implements XmlSaver, XmlParser
{
    public String getPath()
    {
        return FileUtils.getJarDirectory(this.getClass()).getAbsolutePath() + File.separator + "test.xml";
    }

    public boolean overwritingOldFiles()
    {
        return true;
    }

    public void addNode(Node node) throws XmlParseException
    {
        System.out.println("rootnode: " + node.getNodeName());
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++)
        {
            node = nodeList.item(i);
            System.out.println("node: " + node.getNodeName());
            System.out.println("text: " + node.getTextContent());
        }
    }

    public String getRootTag()
    {
        return "tests";
    }

    public Integer getPriority()
    {
        return 1;
    }

    public Attribute[] getRootAttributes()
    {
        return null;
    }

    public void save(XMLEventWriter eventWriter, XMLEventFactory eventFactory) throws XMLStreamException
    {
        XmlUtils.createSimpleComment(eventWriter, eventFactory, 1, "This is still a little test comment!");

        XmlUtils.createTextElement(eventWriter, eventFactory, 1, "test", "Dies ist ein Testnode. moep..");

        XmlUtils.createStartElement(eventWriter, eventFactory, 1, "test");
        Attribute attribute = eventFactory.createAttribute("blah", "blub");
        XmlUtils.createTextElement(eventWriter, eventFactory, 2, "innernode", "text text text", attribute);
        XmlUtils.createEndElement(eventWriter, eventFactory, 1, "test");

        XmlUtils.createMultilineComment(eventWriter, eventFactory, 1, 100, "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
        XmlUtils.createStartElement(eventWriter, eventFactory, 1, "test");
        XmlUtils.createCharacaters(eventWriter, eventFactory, 2, "bli bla blub");
        XmlUtils.createEndElement(eventWriter, eventFactory, 1, "test");
    }
}
