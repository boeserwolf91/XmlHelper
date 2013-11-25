package de.boeserwolf91.xml.installer;

import java.util.logging.Logger;

import de.boeserwolf91.xml.installer.exception.XmlParseException;
import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;
import de.boeserwolf91.xml.installer.parser.BaseXmlParser;
import de.boeserwolf91.xml.installer.parser.DefaultXmlParser;
import de.boeserwolf91.xml.installer.parser.DirectoryManager;
import de.boeserwolf91.xml.installer.parser.XmlParser;
import de.boeserwolf91.xml.installer.parser.XmlParserManager;

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

    public void registerMatcher(Matcher matcher, Class ... types)
    {
        this.matcherManager.registerMatcher(matcher, types);
    }

    public void install()
    {
        try
        {
            this.getBaseXmlParser().install();
        }
        catch (XmlParseException e)     // TODO add RuntimeException and throw it!
        {
            e.printStackTrace();
        }
    }

    public void parse(String path, boolean insideJar)
    {
        try
        {
            this.getBaseXmlParser().parse(path, insideJar);
        }
        catch (XmlParseException e)       // TODO add RuntimeException and throw it!
        {
            e.printStackTrace();
        }
    }
}
