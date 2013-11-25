package de.boeserwolf91.xml;

import org.w3c.dom.Node;

import de.boeserwolf91.xml.installer.exception.XmlParseException;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;
import de.boeserwolf91.xml.installer.parser.XmlParser;

public class TestXML implements XmlParser
{
    public void addNode(Node node, MatcherManager manager) throws XmlParseException
    {
        System.out.println("found node!");
    }

    public String getRootTag()
    {
        return "tests";
    }
}
