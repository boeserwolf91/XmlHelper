package de.boeserwolf91.xml.installer.matcher.matchers;

import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public class StringMatcher implements Matcher<String>
{
    public String match(String string, MatcherManager manager)
    {
        return string;
    }
}
