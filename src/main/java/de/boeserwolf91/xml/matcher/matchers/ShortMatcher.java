package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;

public class ShortMatcher implements Matcher<Short>
{
    public Short match(String string)
    {
        return Short.parseShort(string);
    }
}
