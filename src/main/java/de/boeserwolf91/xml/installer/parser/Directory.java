package de.boeserwolf91.xml.installer.parser;

public class Directory
{
    private String path;
    private boolean insideJar;
    private boolean subfolder;

    public Directory(String path, boolean subfolder, boolean insideJar)
    {
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
