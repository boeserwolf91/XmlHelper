package de.boeserwolf91.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import de.boeserwolf91.xml.exception.XmlParseException;
import de.boeserwolf91.xml.utils.FileUtils;
import de.boeserwolf91.xml.utils.NodeComparator;
import de.boeserwolf91.xml.utils.StringUtils;

/**
 * This class is a little helper for the java dom xml parser. It helps to parse a xml file easier.
 * <p/>
 * With the help of this class, one can install every xml file of a directory.
 * Just register a few classes which implements XmlParser interface and a directory
 * representing by a XmlDirectory-Object. Now use the install() - method and all xml files of this
 * directory will be parsed.
 * <p/>
 * Furthermore the class provides an opportunity to just parse a single file without to register a
 * XmlDirectory. Just use the getNode(InputStream) and parseNode(Node node) methods.
 */
public class DefaultXmlParser
{
    private static DefaultXmlParser INSTANCE;

    /**
     * This static method returns an instance of this singleton class.
     *
     * @return instance of this class
     */
    public static DefaultXmlParser getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new DefaultXmlParser();
        }
        return INSTANCE;
    }

    /**
     * This set stores the classes implementing XmlParser, which should be considerd
     */
    private Set<XmlParser> xmlParserSet;
    /**
     * This list stores the directories which should be installed.
     * The directory will be removed from this list if it was installed succesfully.
     */
    private List<XmlDirectory> xmlDirectoryList;

    private DefaultXmlParser()
    {
        this.xmlParserSet = new HashSet<XmlParser>(3);
        this.xmlDirectoryList = new ArrayList<XmlDirectory>(2);
    }

    /**
     * This method registers a class which implements XmlParser interface.
     * This class will be considered if a xml-file is parsed by this class.
     *
     * @param parser class which implements XmlParser and should be considered by this class.
     *
     * @return whether the class could be registered.
     */
    public boolean registerXmlParser(XmlParser parser)
    {
        return this.xmlParserSet.add(parser);
    }

    /**
     * This class checks a parser out of this class. This class will not be considered if
     * a xml-file is parsed by this class anymore.
     *
     * @param parser class which implements XmlParser and should be checked out
     *
     * @return whether the class should be checked out
     */
    public boolean checkOutXmlParser(XmlParser parser)
    {
        return this.xmlParserSet.remove(parser);
    }

    /**
     * This method registers a directory, which should be search for xml-files by using install()-method.
     * The XmlDirectory class will ne be registered anymore if it was installed successfully.
     *
     * @param directory XmlDirectoy-Instance of a directory
     *
     * @return whether the directory could be registered.
     */
    public boolean registerXmlDirectory(XmlDirectory directory)
    {
        return this.xmlDirectoryList.add(directory);
    }

    /**
     * This method searches for all xml files of the registered directories.
     * Afterwards the xml files will be parsed by java dom parser ordered by priority. Therefore
     * it calls the addNode(Node node) methods of the XmlParser-classes.
     *
     * @throws XmlParseException is thrown if a directory couldn't be searched or a xml file couldn't be parsed.
     */
    public void install() throws XmlParseException
    {
        if (this.xmlParserSet.isEmpty())
        {
            throw new XmlParseException("It isn't specified a class which implements the XmlParser interface!");
        }
        else if (this.xmlDirectoryList.isEmpty())
        {
            throw new XmlParseException("It isn't specified a directory which should be searched for xml files!");
        }

        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<Node>(10, new NodeComparator());

        Iterator<XmlDirectory> directoryIterator = this.xmlDirectoryList.iterator();
        while (directoryIterator.hasNext())
        {
            XmlDirectory directory = directoryIterator.next();
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

            directoryIterator.remove();     // Ordner wird aus der Liste gelöscht.
        }

        while (!nodePriorityQueue.isEmpty())
        {
            this.parse(nodePriorityQueue.poll());        // Wurzeltags werden geparst
        }
    }

    /**
     * This method searches for a suitable XmlParser instance for the root tag and
     * calls the addNode(Node node) method of the instance.
     *
     * @param node the root tag
     *
     * @throws XmlParseException
     */
    public void parse(Node node) throws XmlParseException
    {
        String name = node.getNodeName();

        for (XmlParser parser : this.xmlParserSet)
        {
            if (parser.getRootTag().equals(name))
            {
                parser.addNode(node);
                break;
            }
        }
    }

    /**
     * This method returns the root tag of a xml file.
     *
     * @param stream thie input stream of the xml file
     *
     * @return the root tag of the xml file
     *
     * @throws XmlParseException is thrown if the stream isn't from a xml file, if the xml file does not have a root tag or if the file can't be read.
     */
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

    /**
     * This method converts the path with the help of the jarDir-Variable to a URL- or a File-Object
     * and calls the suitable parse(..) method.
     *
     * @param path   the path of the xml file
     * @param jarDir the information whether the file is inside of the jar or outside
     *
     * @throws XmlParseException is thrown if something went wrong!
     */
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

    /**
     * This method parses the URL with the help of the XMLDomParser of java.
     * In Addition it looks for a suitable XmlParser class and calls the addNode(Node) method of it.
     *
     * @param url the url of the file
     *
     * @throws XmlParseException is thrown if something went wrong!
     */
    public void parse(URL url) throws XmlParseException
    {
        if (!StringUtils.getFileExtension(url.getPath()).equalsIgnoreCase("xml"))
        {
            throw new XmlParseException("The specified url is not a xml file!");
        }

        try
        {
            InputStream inputStream = url.openStream();
            this.parse(this.getNode(inputStream));
            inputStream.close();
        }
        catch (IOException e)
        {
            throw new XmlParseException("The file couldn't be read!", e);
        }
    }

    /**
     * This method parses the File object with the help of the XMLDomParser of java.
     * In Addition it looks for a suitable XmlParser class and calls the addNode(Node) method of it.
     *
     * @param file the File object of the xml file
     *
     * @throws XmlParseException is thrown if something went wrong
     */
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
