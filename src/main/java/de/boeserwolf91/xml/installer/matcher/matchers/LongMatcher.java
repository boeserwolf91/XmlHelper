package de.boeserwolf91.xml.installer.matcher.matchers;

import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public class LongMatcher implements Matcher<Long>
{
    public Long match(String string, MatcherManager manager)
    {
        return Long.parseLong(string);
    }
}
