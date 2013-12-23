package de.boeserwolf91.xml.installer.parser;

import java.util.HashSet;
import java.util.Set;

public class DirectoryManager
{
    Set<Directory> directories;

    public DirectoryManager()
    {
        this.directories = new HashSet<Directory>();
    }

    public Directory[] getDirectories()
    {
        return this.directories.toArray(new Directory[this.directories.size()]);
    }

    public boolean registerDirectory(Directory directory)
    {
        return this.directories.add(directory);
    }

    public boolean registerDirectory(String path, boolean subfolder)
    {
        return this.directories.add(new Directory(path, subfolder));
    }

    public boolean removeDirectory(Directory directory)
    {
        return this.directories.remove(directory);
    }

    public void removeDirectories()
    {
        this.directories.clear();
    }
}
