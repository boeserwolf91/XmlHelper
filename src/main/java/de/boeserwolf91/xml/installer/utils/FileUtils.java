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

/**
 * A few methods are created with the help of this: http://stackoverflow.com/questions/1386809/copy-directory-from-a-jar-file
 */
public class FileUtils
{
    /**
     * This method copies a file to the specified destination
     *
     * @param file        the file which should be copied
     * @param destination the destination file
     *
     * @return whether the file could be copied successfully
     *
     * @throws IOException if the file or the destination couldn't be read
     */
    public static boolean copyFile(final File file, final File destination) throws IOException
    {
        return FileUtils.copyStream(new FileInputStream(file), new FileOutputStream(destination));
    }

    /**
     * The method copies a file to the specified destination. If the file is directory,
     * the method will copy every file recursively.
     *
     * @param file           the file that should be copied
     * @param destinationDir the destination folder
     *
     * @return whether the file could be copied successfully
     *
     * @throws IOException
     */
    public static boolean copyFilesRecursively(final File file, final File destinationDir) throws IOException
    {
        if (destinationDir.isDirectory())
        {
            if (!file.isDirectory())
            {
                return FileUtils.copyFile(file, new File(destinationDir, file.getName()));
            }
            else
            {
                final File newDestinationDir = new File(destinationDir, file.getName());
                if (!newDestinationDir.exists() && !newDestinationDir.mkdir())
                {
                    return false;
                }
                for (final File child : file.listFiles())
                {
                    if (!FileUtils.copyFilesRecursively(child, newDestinationDir))
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * This method copies a file which is inside the jar to the specified destination.
     * If the file is a directory every file inside it will be copied recursively.
     *
     * @param jarConnection  the URLConnection instance of the file which should be copied.
     * @param destinationDir the destination folder
     *
     * @return whether the file should be copied successfully
     *
     * @throws IOException
     */
    public static boolean copyJarResourcesRecursively(final JarURLConnection jarConnection, final File destinationDir) throws IOException
    {
        JarFile jarFile = jarConnection.getJarFile();

        Enumeration<JarEntry> jarEntryEnum = jarFile.entries();
        while (jarEntryEnum.hasMoreElements())
        {
            JarEntry jarEntry = jarEntryEnum.nextElement();

            if (jarEntry.getName().startsWith(jarConnection.getEntryName()))
            {
                String fileName = StringUtils.removeStart(jarEntry.getName(), jarConnection.getEntryName());

                File file = new File(destinationDir, fileName);

                if (!jarEntry.isDirectory())
                {
                    InputStream entryInputStream = jarFile.getInputStream(jarEntry);
                    if (!FileUtils.copyStream(entryInputStream, file))
                    {
                        return false;
                    }
                    entryInputStream.close();
                }
                else
                {
                    if (!FileUtils.ensureDirectoryExists(file))
                    {
                        throw new IOException("Could not create directory: " + file.getAbsolutePath());
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method copies the URL instance to the specified destination directory.
     * If the file is a directory every file inside it will be copied recursively.
     * The method will detect automatically whether the url is inside or outside
     * the jar and calls the suitable method.
     *
     * @param originUrl   URL instance of the file which should be copied.
     * @param destination - File instance of the destination dir
     *
     * @return whether the file could be copied successfully
     *
     * @throws IOException
     */
    public static boolean copyResourcesRecursively(final URL originUrl, final File destination) throws IOException
    {
        final URLConnection urlConnection = originUrl.openConnection();
        if (urlConnection instanceof JarURLConnection)
        {
            return FileUtils.copyJarResourcesRecursively((JarURLConnection)urlConnection, destination);
        }
        else
        {
            return FileUtils.copyFilesRecursively(new File(originUrl.getPath()), destination);
        }
    }

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

    /**
     * This method copies the file of the InputStream to the position of the File
     *
     * @param is InputStream of the file, which should be copied
     * @param f  destination file
     *
     * @return whether the file could be copied successfully
     *
     * @throws IOException is thrown if the file couldn't be created successfully
     */
    private static boolean copyStream(final InputStream is, final File f) throws IOException
    {
        if (!f.exists())
        {
            f.createNewFile();
        }
        return FileUtils.copyStream(is, new FileOutputStream(f));
    }

    /**
     * This method copies the file of the InputStream to the position of the OutputStream
     *
     * @param is InputStream of the file, which should be copied
     * @param os OutputStream of the destination file.
     *
     * @return whether the file could be copied successfully
     */
    private static boolean copyStream(InputStream is, FileOutputStream os)
    {
        try
        {
            byte[] buffer = new byte[1024];
            int len;

            while ((len = is.read(buffer)) > 0)
            {
                os.write(buffer, 0, len);
            }
            is.close();
            os.close();
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }

    /**
     * This method ensures that a directory exists. It checks whether it exists. If it
     * does not exist, it will create the directory.
     *
     * @param f file object of the directory
     *
     * @return whether the directory exists now
     */
    public static boolean ensureDirectoryExists(final File f)
    {
        return f.exists() || f.mkdir();
    }

    /**
     * This method returns the directory where the jar is stored of the specified class.
     *
     * @param clazz the class specifies the jar
     *
     * @return the directory of the jar as a File object
     *
     * @throws UnsupportedEncodingException
     */
    public static File getJarDirectory(Class clazz)
    {
        String javaClassPath = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        //        javaClassPath = URLDecoder.decode(javaClassPath, "UTF-8");
        File tmpFile = new File(javaClassPath);
        javaClassPath = tmpFile.getAbsolutePath();
        int index = javaClassPath.lastIndexOf(File.separator);
        if (index != -1)
        {
            javaClassPath = javaClassPath.substring(0, index);
        }
        return new File(javaClassPath);
    }
}
