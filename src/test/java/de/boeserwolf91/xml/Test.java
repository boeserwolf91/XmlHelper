package de.boeserwolf91.xml;

import junit.framework.TestCase;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.boeserwolf91.xml.installer.XmlFactory;
import de.boeserwolf91.xml.installer.utils.FileUtils;

public class Test extends TestCase
{
    public XmlFactory factory;
    public TestXML testXML;

    public static void main(String[] args) throws Exception
    {
        Test test = new Test();
        test.setUp();
        test.test();
    }

    @Override
    public void setUp() throws Exception
    {
        Logger logger = Logger.getLogger("testlogger");
        logger.setLevel(Level.WARNING);

        this.factory = new XmlFactory(logger);
        this.testXML = new TestXML();

        factory.registerXmlParser(this.testXML);
    }

    @org.junit.Test
    public void test() throws Exception
    {
        this.factory.registerDirectory(FileUtils.getJarDirectory(this.getClass()).getAbsolutePath(), true, false);
        this.factory.install();
    }
}
