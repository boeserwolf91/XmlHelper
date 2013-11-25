package de.boeserwolf91.xml.installer.matcher.matchers;

import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public class ByteMatcher implements Matcher<Byte>
{
    public Byte match(String string, MatcherManager manager)
    {
        return Byte.parseByte(string);
    }
}
