package de.boeserwolf91.xml.matcher;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.boeserwolf91.xml.matcher.matchers.BooleanMatcher;
import de.boeserwolf91.xml.matcher.matchers.ByteMatcher;
import de.boeserwolf91.xml.matcher.matchers.CharMatcher;
import de.boeserwolf91.xml.matcher.matchers.DoubleMatcher;
import de.boeserwolf91.xml.matcher.matchers.FloatMatcher;
import de.boeserwolf91.xml.matcher.matchers.IntegerMatcher;
import de.boeserwolf91.xml.matcher.matchers.LocaleMatcher;
import de.boeserwolf91.xml.matcher.matchers.LongMatcher;
import de.boeserwolf91.xml.matcher.matchers.ShortMatcher;
import de.boeserwolf91.xml.matcher.matchers.StringMatcher;

/**
 * The DefaultMatcher class helps to handle different Matcher without to use  * every single Matcher alone.  * This class is looking for the right Matcher at every situation.  * <p/>  * The only
 * thing you have to do is to register every matcher you want to support.  * A few Matchers are registered at the creation of this class. (every Matcher in package:  *
 * 'de.rotkaeppchenproductions.utillib.matcher.matchers')!
 */
public class DefaultMatcher
{
    private static DefaultMatcher INSTANCE;
    private Map<Class<?>, Matcher<?>> matchers = new HashMap<Class<?>, Matcher<?>>();

    /**
     * This static method returns the singleton instance of this class.      * If the instance does not exist, it will be created.      *      * @return the instance of the class
     */
    public static DefaultMatcher getInstance()
    {
        if (INSTANCE == null) { INSTANCE = new DefaultMatcher(); }
        return INSTANCE;
    }

    /**
     * The constructor of the class registers all main matchers from      * 'de.rotkaeppchenproductions.utillib.matcher.matchers' package
     */
    private DefaultMatcher()
    {
        registerMatcher(new BooleanMatcher(), Boolean.class, boolean.class);
        registerMatcher(new ByteMatcher(), Byte.class, byte.class);
        registerMatcher(new CharMatcher(), Character.class, char.class);
        registerMatcher(new DoubleMatcher(), Double.class, double.class);
        registerMatcher(new FloatMatcher(), Float.class, float.class);
        registerMatcher(new IntegerMatcher(), Integer.class, int.class);
        registerMatcher(new LongMatcher(), Long.class, long.class);
        registerMatcher(new ShortMatcher(), Short.class, short.class);
        registerMatcher(new StringMatcher(), String.class);
        registerMatcher(new LocaleMatcher(), Locale.class);
    }

    /**
     * This method registered a new matcher for the specified classes      *      * @param matcher the matcher which should be registered      * @param types   the classes which are provided be this
     * matcher
     */
    public void registerMatcher(Matcher<?> matcher, Class<?>... types)
    { for (Class<?> type : types) { this.matchers.put(type, matcher); } }

    /**
     * This method returns the matcher from the specified class      *      * @param type the class      *      * @return matcher object
     */
    public Matcher<?> getMatcher(Class<?> type)
    { return this.matchers.get(type); }

    /**
     * This method matches the string with the matcher which is specified for the given class.      *      * @param string string which should be matched      * @param type   the class representing
     * the result object      * @param <T>    The class of the instance which is returned by this method      *      * @return the object which is representing by this string and type
     */
    @SuppressWarnings("unchecked")
    public <T> T match(String string, Class<T> type)
    {
        Matcher<?> matcher = this.matchers.get(type);
        try { return (T)matcher.match(string); } catch (Exception ignored) {}
        return null;
    }

    /**
     * This method matches the string with the matcher which is specified for the given class.      *      * @param string       string which should be matched      * @param type         the class
     * representing the result object      * @param defaultValue the object that should be returned if the string does not match with a instance of this class.      * @param <T>          The class of
     * the instance which is returned by this method      *      * @return the object which is representing by this string and type or the default value if the other is null.
     */
    @SuppressWarnings("unchecked")
    public <T> T match(String string, Class<T> type, T defaultValue)
    {
        T value = match(string, type);
        if (value == null) { return defaultValue; }
        return value;
    }
}
