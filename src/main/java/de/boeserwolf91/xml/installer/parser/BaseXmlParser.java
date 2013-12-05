package de.boeserwolf91.xml.installer.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import de.boeserwolf91.xml.installer.XmlFactory;
import de.boeserwolf91.xml.installer.exception.XmlParseException;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public abstract class BaseXmlParser
{
    private XmlFactory factory;

    public void init(XmlFactory factory)
    {
        this.factory = factory;
    }

    public XmlFactory getFactory()
    {
        return this.factory;
    }

    public XmlParserManager getXmlParserManager()
    {
        return this.factory.getXmlParserManager();
    }

    public MatcherManager getMatcherManager()
    {
        return this.factory.getMatcherManager();
    }

    public DirectoryManager getXmlDirectoryManager()
    {
        return this.factory.getXmlDirectoryManager();
    }

    public Logger getLogger()
    {
        return this.factory.getLogger();
    }

    public void parse(InputStream stream) throws XmlParseException
    {
        this.parse(this.getNode(stream));
    }

    protected void parse(Node node)
    {
        for (XmlParser parser : this.getXmlParserManager().getXmlParsers())
        {
            if (parser.getRootTag().equals(node.getNodeName()))
            {
                this.getLogger().log(Level.INFO, "parses node with " + parser.getClass().getName());
                parser.addNode(node, this.getMatcherManager());
                return;
            }
        }
        this.getLogger().log(Level.WARNING, "Did not find any parser for root tag '" + node.getNodeName() + "'!");
    }

    protected Node getNode(InputStream stream) throws XmlParseException
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(stream);

            NodeList list = document.getChildNodes();
            Node node = list.item(0);

            return node;
        }
        catch (Exception e)
        {
            throw new XmlParseException("Can't parse the input stream!", e);
        }
    }

    public abstract void install() throws XmlParseException;
}
