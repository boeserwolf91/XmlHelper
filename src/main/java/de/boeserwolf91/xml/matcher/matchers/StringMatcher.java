package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;
import de.boeserwolf91.xml.matcher.MatcherManager;

public class StringMatcher implements Matcher<String>
{
    public String match(String string, MatcherManager manager)
    {
        return string;
    }
}
