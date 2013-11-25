package de.boeserwolf91.xml;

import junit.framework.TestCase;

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
        this.factory = new XmlFactory();
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
