package de.boeserwolf91.xml.installer.parser;

import org.w3c.dom.Node;

import de.boeserwolf91.xml.installer.exception.XmlParseException;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public interface XmlParser
{
    public void addNode(Node node, MatcherManager manager) throws XmlParseException;

    public String getRootTag();
}
