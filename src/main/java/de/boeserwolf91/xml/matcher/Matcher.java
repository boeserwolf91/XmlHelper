package de.boeserwolf91.xml.matcher;

/**
 * This is a util interface. It should be implemented by classes, which should match specified objects.
 * <p/>
 * To point it up, this class provides an interface between a string object, which could be parsed from a xml
 * file for example and the specified class T ..
 * With the help of this class one get easily an instance of the class T by parsing a String.
 * <p/>
 * as an example you can look at the classes in package de.rotkaeppchenproductions.utillib.matcher.matchers !
 * <p/>
 * To match the instances easier you could register the class implementing this interface in the DefaultXmlParser
 * class. Have a look at it!
 *
 * @param <T> The class which should be parsed by the string.
 */
public interface Matcher<T>
{
    /**
     * This method parses the string to the specified class T.
     *
     * @param string the string object that will be parsed
     *
     * @return the parsed object
     */
    public T match(String string);
}
