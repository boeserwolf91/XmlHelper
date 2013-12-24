package de.boeserwolf91.xml.installer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtils
{
    /**
     * Returns every file, which is found inside the specified folder
     *
     * @param directory the folder which should be searched for files
     * @param subfolder whether subfolders should be searched for files or not.
     *
     * @return an array of URL objects which contains every file inside the directory
     *
     * @throws IOException
     */
    public static URL[] getSubURLs(final URL directory, boolean subfolder) throws IOException
    {
        final URLConnection urlConnection = directory.openConnection();
        if (urlConnection instanceof JarURLConnection)
        {
            return FileUtils.getJarSubFiles(directory, (JarURLConnection)urlConnection, subfolder);
        }
        else
        {
            File[] files = FileUtils.getSubFiles(new File(directory.getPath()), subfolder);
            URL[] urls = new URL[files.length];
            for (int i = 0; i < files.length; i++)
            {
                urls[i] = new URL(directory.getProtocol(), directory.getHost(), directory.getPort(), files[i].getAbsolutePath());
            }
            return urls;
        }
    }

    /**
     * This method returns every file which is inside a directory.
     * the file has to be <b>outside</b> the jar!
     *
     * @param file      File instance of the directory
     * @param subfolder whether subfolders should be searched for files or not.
     *
     * @return an array of File objects which contains every file inside the directory
     */
    public static File[] getSubFiles(File file, boolean subfolder)
    {
        if (!file.isDirectory())
        {
            return null;
        }
        List<File> fileList = FileUtils.getSubFilesAsList(file, subfolder);
        return fileList.toArray(new File[fileList.size()]);
    }

    /**
     * This method returns every file which is inside a directory.
     * the file has to be <b>outside</b> the jar!
     *
     * @param file      File instance of the directory
     * @param subfolder whether subfolders should be searched for files or not.
     *
     * @return a List of File objects which contains every file inside the directory
     */
    public static List<File> getSubFilesAsList(File file, boolean subfolder)
    {
        List<File> fileList = new ArrayList<File>();
        for (File subFile : file.listFiles())
        {
            fileList.add(subFile);
            if (subFile.isDirectory() && subfolder)
            {
                fileList.addAll(FileUtils.getSubFilesAsList(subFile, true));
            }
        }
        return fileList;
    }

    /**
     * This method returns every file which is inside a directory.
     * the file has to be <b>inside</b> the jar!
     *
     * @param directory     URL object of the directory
     * @param jarConnection URLConnection of the directory
     * @param subfolder     whether subfolders should be searched for files or not.
     *
     * @return an array of URL objects which contains every file inside the directory
     *
     * @throws IOException
     */
    private static URL[] getJarSubFiles(final URL directory, JarURLConnection jarConnection, boolean subfolder) throws IOException
    {
        JarFile jarFile = jarConnection.getJarFile();

        Enumeration<JarEntry> jarEntryEnum = jarFile.entries();

        Set<URL> urlList = new HashSet<URL>();
        while (jarEntryEnum.hasMoreElements())
        {
            JarEntry jarEntry = jarEntryEnum.nextElement();

            if (jarEntry.getName().startsWith(jarConnection.getEntryName()))
            {
                String fileName = StringUtils.removeStart(jarEntry.getName(), jarConnection.getEntryName());
                if (!fileName.isEmpty() && (subfolder || !fileName.contains("/")))
                {
                    urlList.add(new URL(directory.getProtocol(), directory.getHost(), directory.getPort(), directory.getPath() + fileName));
                }
            }
        }
        return urlList.toArray(new URL[urlList.size()]);
    }
}
