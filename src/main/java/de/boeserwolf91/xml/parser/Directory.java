package de.boeserwolf91.xml.parser;

import de.boeserwolf91.xml.exception.XmlParseException;

public class Directory
{
    private String path;
    private boolean insideJar;
    private boolean subfolder;

    public Directory(String path, boolean subfolder, boolean insideJar) throws XmlParseException
    {

        if (path == null || path.trim().equals(""))
        {
            throw new XmlParseException("You have to specify a path to create a xmlDirectory");
        }

        this.path = path.trim();
        this.subfolder = subfolder;
        this.insideJar = insideJar;
    }

    public String getPath()
    {
        return this.path;
    }

    public boolean searchSubfolder()
    {
        return this.subfolder;
    }

    public boolean isInsideJar()
    {
        return this.insideJar;
    }

    @Override
    public String toString()
    {
        return this.path;
    }
}
