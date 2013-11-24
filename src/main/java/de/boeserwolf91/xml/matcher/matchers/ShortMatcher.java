package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;
import de.boeserwolf91.xml.matcher.MatcherManager;

public class ShortMatcher implements Matcher<Short>
{
    public Short match(String string, MatcherManager manager)
    {
        return Short.parseShort(string);
    }
}
