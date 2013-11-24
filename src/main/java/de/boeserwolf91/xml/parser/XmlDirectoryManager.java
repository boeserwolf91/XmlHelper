package de.boeserwolf91.xml.parser;

import java.util.HashSet;
import java.util.Set;

import de.boeserwolf91.xml.exception.XmlParseException;

public class XmlDirectoryManager
{
    Set<XmlDirectory> directories;

    public XmlDirectoryManager()
    {
        this.directories = new HashSet<XmlDirectory>();
    }

    public XmlDirectory[] getXmlDirectories()
    {
        return this.directories.toArray(new XmlDirectory[this.directories.size()]);
    }

    public boolean registerDirectory(XmlDirectory directory)
    {
        return this.directories.add(directory);
    }

    public boolean registerDirectory(String path, boolean subfolder, boolean insideJar)
    {
        try
        {
            return this.directories.add(new XmlDirectory(path, subfolder, insideJar));
        }
        catch (XmlParseException e)   // TODO take a RuntimeException!
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeDirectory(XmlDirectory directory)
    {
        return this.directories.remove(directory);
    }
}
