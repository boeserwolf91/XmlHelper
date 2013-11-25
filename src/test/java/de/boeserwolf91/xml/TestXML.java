package de.boeserwolf91.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.boeserwolf91.xml.installer.exception.XmlParseException;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;
import de.boeserwolf91.xml.installer.parser.XmlParser;

public class TestXML implements XmlParser
{
    public void addNode(Node node, MatcherManager manager) throws XmlParseException
    {
        System.out.println("found node!");
        NodeList list = node.getChildNodes();
        for(int i = 0; i < list.getLength(); i++)
        {
            node = list.item(i);
            if(node.getTextContent().contains("\n"))
            {
                continue;
            }
            System.out.printf("%s -> %s\n", node.getNodeName(), node.getTextContent());
        }
    }

    public String getRootTag()
    {
        return "tests";
    }
}
