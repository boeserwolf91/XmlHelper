package de.boeserwolf91.xml.installer.parser;

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
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import de.boeserwolf91.xml.installer.exception.XmlParseException;
import de.boeserwolf91.xml.installer.utils.FileUtils;
import de.boeserwolf91.xml.installer.utils.NodeComparator;
import de.boeserwolf91.xml.installer.utils.StringUtils;

public class DefaultXmlParser extends BaseXmlParser
{
    @Override
    public void install() throws XmlParseException
    {
        this.getLogger().log(Level.INFO, "starts to install xml files.");
        if (this.getXmlParserManager().getXmlParsers().length == 0)
        {
            throw new XmlParseException("It isn't specified a class which implements the XmlParser interface!");
        }
        else if (this.getXmlDirectoryManager().getDirectories().length == 0)
        {
            throw new XmlParseException("It isn't specified a directory which should be searched for xml files!");
        }

        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, new NodeComparator());

        Directory[] directories = this.getXmlDirectoryManager().getDirectories();
        this.getLogger().log(Level.INFO, "installs xml files from " + directories.length + " directories.");
        for (Directory directory : directories)
        {
            this.getLogger().log(Level.INFO, String.format("installs xml files from directory %s with%s subfolders.", directory, directory.searchSubfolder() ? "" : "out"));
            URL directoryURL = null;

            if (directory.isInsideJar())    // converts path to an URL object
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
            this.getLogger().log(Level.INFO, "directory url: " + directoryURL);

            URL[] urls;
            try                // every files from directory are loaded
            {
                urls = FileUtils.getSubURLs(directoryURL, directory.searchSubfolder());
                this.getLogger().log(Level.INFO, "found " + urls.length + " files inside the directory.");
            }
            catch (IOException e)
            {
                throw new XmlParseException("Can't load suburls from the directory: " + directory, e);
            }

            for (URL url : urls)
            {
                if (!StringUtils.getFileExtension(url.getPath()).equalsIgnoreCase("xml"))    // every root node will be stored to PriorityQueue
                {
                    continue;
                }
                this.getLogger().log(Level.INFO, "parses and adds " + url + " to PriorityQueue<Node>.");
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

            this.getLogger().log(Level.INFO, "removes directory from DirectoryManager");
            this.getXmlDirectoryManager().removeDirectory(directory);
        }

        while (!nodePriorityQueue.isEmpty())
        {
            this.parse(nodePriorityQueue.poll());   // parses root nodes
        }
        this.getLogger().log(Level.INFO, "finished to install xml files from directories");
    }

    public void parse(Node node) throws XmlParseException
    {
        String name = node.getNodeName();

        for (XmlParser parser : this.getXmlParserManager().getXmlParsers())
        {
            if (parser.getRootTag().equals(name))
            {
                this.getLogger().log(Level.INFO, "parses node with " + parser.getClass().getName());
                parser.addNode(node, this.getMatcherManager());
                return;
            }
        }
        this.getLogger().log(Level.WARNING, "Did not find any parser for root tag '" + name + "'!");
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