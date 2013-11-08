package de.boeserwolf91.xml;

import junit.framework.TestCase;

import java.io.File;

import de.boeserwolf91.xml.utils.FileUtils;

public class Test extends TestCase
{
    public TestXML testXML;

    @Override
    public void setUp() throws Exception
    {
        this.testXML = new TestXML();
    }

    @org.junit.Test
    public void test() throws Exception
    {
        DefaultXmlSaver xmlSaver = DefaultXmlSaver.getInstance();
        DefaultXmlParser xmlParser = DefaultXmlParser.getInstance();
        xmlParser.registerXmlParser(this.testXML);

        xmlSaver.save(this.testXML);

        XmlDirectory directory = new XmlDirectory(FileUtils.getJarDirectory(this.getClass()).getAbsolutePath(), false, false);
        xmlParser.registerXmlDirectory(directory);

        xmlParser.install();
        System.out.println("-------------------");

        xmlParser.parse(this.testXML.getPath(), false);

        File file = new File(this.testXML.getPath());
        file.delete();
    }
}
