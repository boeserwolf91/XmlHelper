package de.boeserwolf91.xml.matcher;

public interface Matcher<T>
{
    public T match(String string, MatcherManager manager);
}
