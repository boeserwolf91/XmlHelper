package de.boeserwolf91.xml.installer.matcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import de.boeserwolf91.xml.installer.matcher.matchers.BooleanMatcher;
import de.boeserwolf91.xml.installer.matcher.matchers.ByteMatcher;
import de.boeserwolf91.xml.installer.matcher.matchers.CharMatcher;
import de.boeserwolf91.xml.installer.matcher.matchers.DoubleMatcher;
import de.boeserwolf91.xml.installer.matcher.matchers.FloatMatcher;
import de.boeserwolf91.xml.installer.matcher.matchers.IntegerMatcher;
import de.boeserwolf91.xml.installer.matcher.matchers.LocaleMatcher;
import de.boeserwolf91.xml.installer.matcher.matchers.LongMatcher;
import de.boeserwolf91.xml.installer.matcher.matchers.ShortMatcher;
import de.boeserwolf91.xml.installer.matcher.matchers.StringMatcher;

public final class MatcherManager implements Cloneable
{
    private Map<Class, Matcher> matchers;

    public MatcherManager()
    {
        this.matchers = new HashMap<Class, Matcher>(10);
        this.registerDefaultMatcher();
    }

    private void registerDefaultMatcher()
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

    public void registerMatcher(Matcher matcher, Class... types)
    {
        for (Class<?> type : types)
        {
            this.matchers.put(type, matcher);
        }
    }

    public void removeMatcher(Class clazz)
    {
        Iterator<Entry<Class, Matcher>> iterator = this.matchers.entrySet().iterator();

        while (iterator.hasNext())
        {
            Entry<Class, Matcher> entry = iterator.next();
            if (entry.getKey().getClass().equals(clazz) || entry.getValue().getClass().equals(clazz))
            {
                iterator.remove();
            }
        }
    }

    public void removeMatchers()
    {
        this.matchers.clear();
    }

    public Matcher<?> getMatcher(Class type)
    {
        return this.matchers.get(type);
    }

    @SuppressWarnings("unchecked")
    public <T> T match(String string, Class<T> type)
    {
        Matcher<?> matcher = this.matchers.get(type);
        try
        {
            return (T)matcher.match(string, this);
        }
        catch (Exception ignored)
        {}
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T match(String string, Class<T> type, T defaultValue)
    {
        T value = match(string, type);
        if (value == null)
        {
            return defaultValue;
        }
        return value;
    }
}
