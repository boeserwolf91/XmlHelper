package de.boeserwolf91.xml;

import de.boeserwolf91.xml.exception.XmlParseException;

/**
 * This class manages main information of a directory which contains xml-files. Have a look at the constructor
 * for detailed information.
 */
public class XmlDirectory
{
    /**
     * The path of the directory
     */
    private String path;
    /**
     * This attribute stores the information whether the directory is inside the jar file or not.
     */
    private boolean insideJar;
    /**
     * This attribute stores the information whether the DefaultXmlParser class should also
     * loads the xml files of subfolders or not.
     */
    private boolean subfolder;

    /**
     * The constructor initialises the XmlDirectory instance. It needs the params below.
     *
     * @param path      the path where the directory could be found.
     * @param subfolder the information whether the DefaultXmlParser should also load the xml files of the subfolder
     * @param insideJar the information whether the path is inside the jar file or not.
     *
     * @throws XmlParseException is thrown if the path isn't specified.
     */
    public XmlDirectory(String path, boolean subfolder, boolean insideJar) throws XmlParseException
    {

        if (path == null || path.trim().equals(""))
        {
            throw new XmlParseException("You have to specify a path to create a xmlDirectory");
        }

        this.path = path.trim();
        this.subfolder = subfolder;
        this.insideJar = insideJar;
    }

    /**
     * Method returns the path of the directory.
     *
     * @return the path of the directory
     */
    public String getPath()
    {
        return this.path;
    }

    /**
     * This method returns the information whether the subfolder should be considered or not.
     *
     * @return the information whether the subfolder should be considered or not.
     */
    public boolean searchSubfolder()
    {
        return this.subfolder;
    }

    /**
     * This method returns the information whether the path of the directory is inside the jar file.
     *
     * @return whether the path of the xml file is inside the jar.
     */
    public boolean isInsideJar()
    {
        return this.insideJar;
    }

    /**
     * This method returns instance to string
     *
     * @return String instance representing this instance
     */
    @Override
    public String toString()
    {
        return "XmlDir: " + this.insideJar + ":" + this.subfolder + ":" + this.path;
    }
}
