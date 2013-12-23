package de.boeserwolf91.xml.installer.parser;

public class Directory
{
    private String path;
    private boolean subfolder;

    public Directory(String path, boolean subfolder)
    {
        this.path = path.trim();
        this.subfolder = subfolder;
    }

    public String getPath()
    {
        return this.path;
    }

    public boolean searchSubfolder()
    {
        return this.subfolder;
    }

    @Override
    public String toString()
    {
        return this.path;
    }
}
