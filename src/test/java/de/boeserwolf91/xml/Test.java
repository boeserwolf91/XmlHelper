package de.boeserwolf91.xml;

import junit.framework.TestCase;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.boeserwolf91.xml.installer.XmlFactory;

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
        this.factory.registerDirectory(getJarDirectory(this.getClass()).getAbsolutePath(), true);
        this.factory.install();
    }

    public static File getJarDirectory(Class clazz)
    {
        String javaClassPath = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(javaClassPath).getParentFile();
    }
}
