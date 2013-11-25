package de.boeserwolf91.xml.installer.matcher.matchers;

import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public class IntegerMatcher implements Matcher<Integer>
{
    public Integer match(String string, MatcherManager manager)
    {
        return Integer.parseInt(string);
    }
}
