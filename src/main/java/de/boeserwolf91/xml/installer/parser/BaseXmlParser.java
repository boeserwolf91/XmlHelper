package de.boeserwolf91.xml.installer.parser;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

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

    public abstract void install() throws XmlParseException;

    public abstract void parse(String path, boolean jarDir) throws XmlParseException;

    public abstract void parse(InputStream stream) throws XmlParseException;

    public abstract void parse(URL url) throws XmlParseException;

    public abstract void parse(File file) throws XmlParseException;
}
