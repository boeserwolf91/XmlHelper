package de.boeserwolf91.xml.installer;

import java.io.InputStream;
import java.util.logging.Logger;

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

    public void registerDirectory(String path, boolean subfolder)
    {
        this.getXmlDirectoryManager().registerDirectory(path, subfolder);
    }

    public void registerMatcher(Matcher matcher, Class ... types)
    {
        this.matcherManager.registerMatcher(matcher, types);
    }

    public void install()
    {
        this.getBaseXmlParser().install();
    }

    public void parse(InputStream stream)
    {
        this.getBaseXmlParser().parse(stream);
    }
}
