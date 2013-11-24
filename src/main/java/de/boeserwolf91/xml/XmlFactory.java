package de.boeserwolf91.xml;

import java.util.logging.Logger;

import de.boeserwolf91.xml.exception.XmlParseException;
import de.boeserwolf91.xml.matcher.MatcherManager;
import de.boeserwolf91.xml.parser.BaseXmlParser;
import de.boeserwolf91.xml.parser.DefaultXmlParser;
import de.boeserwolf91.xml.parser.DirectoryManager;
import de.boeserwolf91.xml.parser.XmlParser;
import de.boeserwolf91.xml.parser.XmlParserManager;

public class XmlFactory
{
    private Logger logger;

    private final BaseXmlParser baseXmlParser;

    private MatcherManager matcherManager;
    private XmlParserManager parserManager;
    private DirectoryManager directoryManager;

    public XmlFactory()
    {
        this(new DefaultXmlParser(), Logger.getLogger("XmlHelper"));
    }

    public XmlFactory(Logger logger)
    {
        this(new DefaultXmlParser(), logger);
    }

    public XmlFactory(BaseXmlParser xmlParser)
    {
        this(xmlParser, Logger.getLogger("XmlHelper"));
    }

    public XmlFactory(BaseXmlParser xmlParser, Logger logger)
    {
        this.baseXmlParser = xmlParser;
        this.baseXmlParser.init(this);

        this.matcherManager = new MatcherManager();
        this.parserManager = new XmlParserManager();
        this.directoryManager = new DirectoryManager();

        this.logger = logger;
    }

    public void setLogger(Logger logger)
    {
        this.logger = logger;
    }

    public Logger getLogger()
    {
        return this.logger;
    }

    public BaseXmlParser getBaseXmlParser()
    {
        return this.baseXmlParser;
    }

    public MatcherManager getMatcherManager()
    {
        return this.matcherManager;
    }

    public XmlParserManager getXmlParserManager()
    {
        return this.parserManager;
    }

    public DirectoryManager getXmlDirectoryManager()
    {
        return this.directoryManager;
    }

    public void registerXmlParser(XmlParser parser)
    {
        this.getXmlParserManager().registerXmlParser(parser);
    }

    public void registerDirectory(String path, boolean subfolder, boolean insideJar)
    {
        this.getXmlDirectoryManager().registerDirectory(path, subfolder, insideJar);
    }

    public void install()
    {
        try
        {
            this.getBaseXmlParser().install();
        }
        catch (XmlParseException e)
        {
            e.printStackTrace();
        }
    }
}
