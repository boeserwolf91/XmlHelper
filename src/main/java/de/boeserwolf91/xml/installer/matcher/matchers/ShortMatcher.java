package de.boeserwolf91.xml.installer.matcher.matchers;

import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public class ShortMatcher implements Matcher<Short>
{
    public Short match(String string, MatcherManager manager)
    {
        return Short.parseShort(string);
    }
}
