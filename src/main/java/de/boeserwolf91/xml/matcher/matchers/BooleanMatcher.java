package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;
import de.boeserwolf91.xml.matcher.MatcherManager;

public class BooleanMatcher implements Matcher<Boolean>
{
    public Boolean match(String string, MatcherManager manager)
    {
        return Boolean.parseBoolean(string);
    }
}
