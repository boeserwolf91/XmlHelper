package de.boeserwolf91.xml.utils;

public class StringUtils
{
    /**
     * This method removes the beginning of a string      *      * @param str    the string where the beginning should be removed      * @param remove the beginning which should be removed      *
     * * @return the new string without the beginning 'remove'.
     */
    public static String removeStart(String str, String remove)
    {
        if (isEmpty(str) || isEmpty(remove)) { return str; }
        if (str.startsWith(remove)) { return str.substring(remove.length()); }
        return str;
    }

    /**
     * checks whether a CharSequence is empty.      *      * @param cs the char sequence which should be checked      *      * @return whether the CharSequence is empty
     */
    public static boolean isEmpty(CharSequence cs)
    { return cs == null || cs.length() == 0; }

    /**
     * This methods returns the file extension of a file (name)      *      * @param fileName the file name      *      * @return the file extension of the file name
     */
    public static String getFileExtension(String fileName)
    {
        if (fileName == null) { return null; }
        int index = fileName.lastIndexOf(".");
        if (index > -1 && index + 1 < fileName.length()) { return fileName.substring(index + 1, fileName.length()); }
        return "";
    }

    /**
     * This method returns a string representing a file name without its file extension.      *      * @param filename the file name      *      * @return the file name without the file extension
     */
    public static String stripFileExtension(String filename)
    {
        if (filename == null) { return null; }
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > -1) { return filename.substring(0, lastDot); }
        return filename;
    }
}
