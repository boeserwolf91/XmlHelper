package de.boeserwolf91.xml.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.PriorityQueue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import de.boeserwolf91.xml.exception.XmlParseException;
import de.boeserwolf91.xml.utils.FileUtils;
import de.boeserwolf91.xml.utils.NodeComparator;
import de.boeserwolf91.xml.utils.StringUtils;

public class DefaultXmlParser extends BaseXmlParser
{
    @Override
    public void install() throws XmlParseException
    {
        if (this.getXmlParserManager().getXmlParsers().length == 0)
        {
            throw new XmlParseException("It isn't specified a class which implements the XmlParser interface!");
        }
        else if (this.getXmlDirectoryManager().getXmlDirectories().length == 0)
        {
            throw new XmlParseException("It isn't specified a directory which should be searched for xml files!");
        }

        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, new NodeComparator());

        XmlDirectory[] directories = this.getXmlDirectoryManager().getXmlDirectories();
        for (XmlDirectory directory : directories)
        {
            URL directoryURL = null;

            if (directory.isInsideJar())    // wandelt pfad in ein URL-Objekt um
            {
                ClassLoader classLoader = this.getClass().getClassLoader();
                directoryURL = classLoader.getResource(directory.getPath());
            }
            else
            {
                File file = new File(directory.getPath());

                try
                {
                    directoryURL = new URL("file", "", -1, file.getAbsolutePath());
                }
                catch (MalformedURLException e)
                {
                    throw new XmlParseException("Can't parse directory " + directory + " to url object!", e);
                }
            }

            URL[] urls;
            try                // alle Dateien werden geladen
            {
                urls = FileUtils.getSubURLs(directoryURL, directory.searchSubfolder());
            }
            catch (IOException e)
            {
                throw new XmlParseException("Can't load suburls from the directory: " + directory, e);
            }

            for (URL url : urls)
            {
                if (!StringUtils.getFileExtension(url.getPath()).equalsIgnoreCase("xml"))    // alle Wurzeltags der xml-Dateien werden in die Prioritätswarteschlange geladen
                {
                    continue;
                }
                try
                {
                    InputStream inputStream = url.openStream();
                    nodePriorityQueue.add(this.getNode(inputStream));
                    inputStream.close();
                }
                catch (Exception e)
                {
                    throw new XmlParseException("Can't parse url: " + url.getPath() + "!", e);
                }
            }

            this.getXmlDirectoryManager().removeDirectory(directory);
        }

        while (!nodePriorityQueue.isEmpty())
        {
            this.parse(nodePriorityQueue.poll());   // Wurzeltags werden geparst
        }
    }

    public void parse(Node node) throws XmlParseException
    {
        String name = node.getNodeName();

        for (XmlParser parser : this.getXmlParserManager().getXmlParsers())
        {
            if (parser.getRootTag().equals(name))
            {
                parser.addNode(node, this.getMatcherManager());
                break;
            }
        }
    }

    public Node getNode(InputStream stream) throws XmlParseException
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

    @Override
    public void parse(String path, boolean jarDir) throws XmlParseException
    {
        if (jarDir)
        {
            this.parse(this.getClass().getClassLoader().getResource(path));
        }
        else
        {
            this.parse(new File(path));
        }
    }

    @Override
    public void parse(InputStream stream) throws XmlParseException
    {
        this.parse(this.getNode(stream));
    }

    @Override
    public void parse(URL url) throws XmlParseException
    {
        if (!StringUtils.getFileExtension(url.getPath()).equalsIgnoreCase("xml"))
        {
            throw new XmlParseException("The specified url is not a xml file!");
        }

        try
        {
            InputStream inputStream = url.openStream();
            this.parse(inputStream);
            inputStream.close();
        }
        catch (IOException e)
        {
            throw new XmlParseException("The file couldn't be read!", e);
        }
    }

    @Override
    public void parse(File file) throws XmlParseException
    {
        if (!StringUtils.getFileExtension(file.getName()).equalsIgnoreCase("xml"))
        {
            throw new XmlParseException("The specified file is not a xml file!");
        }

        try
        {
            InputStream inputStream = new FileInputStream(file);
            this.parse(this.getNode(inputStream));
            inputStream.close();
        }
        catch (IOException e)
        {
            throw new XmlParseException("The file couldn't be read!", e);
        }
    }
}
