package de.boeserwolf91.xml.parser;

import java.util.HashSet;
import java.util.Set;

public final class XmlParserManager
{
    Set<XmlParser> parsers;

    public XmlParserManager()
    {
        this.parsers = new HashSet<XmlParser>();
    }

    public XmlParser[] getXmlParsers()
    {
        return this.parsers.toArray(new XmlParser[this.parsers.size()]);
    }

    public boolean registerXmlParser(XmlParser xmlParser)
    {
        return this.parsers.add(xmlParser);
    }

    public boolean removeXmlParser(XmlParser xmlParser)
    {
        return this.parsers.remove(xmlParser);
    }

    public void removeXmlParsers()
    {
        this.parsers.clear();
    }
}
