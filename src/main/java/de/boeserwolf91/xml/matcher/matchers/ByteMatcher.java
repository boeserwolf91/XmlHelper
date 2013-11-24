package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;
import de.boeserwolf91.xml.matcher.MatcherManager;

public class ByteMatcher implements Matcher<Byte>
{
    public Byte match(String string, MatcherManager manager)
    {
        return Byte.parseByte(string);
    }
}
