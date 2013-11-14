package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;

public class ByteMatcher implements Matcher<Byte>
{
    public Byte match(String string)
    {
        return Byte.parseByte(string);
    }
}
