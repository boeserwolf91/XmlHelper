package de.boeserwolf91.xml.parser;

import org.w3c.dom.Node;

import de.boeserwolf91.xml.exception.XmlParseException;
import de.boeserwolf91.xml.matcher.MatcherManager;

public interface XmlParser
{
    public void addNode(Node node, MatcherManager manager) throws XmlParseException;

    public String getRootTag();
}
