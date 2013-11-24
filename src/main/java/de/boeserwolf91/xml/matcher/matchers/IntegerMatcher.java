package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;
import de.boeserwolf91.xml.matcher.MatcherManager;

public class IntegerMatcher implements Matcher<Integer>
{
    public Integer match(String string, MatcherManager manager)
    {
        return Integer.parseInt(string);
    }
}
