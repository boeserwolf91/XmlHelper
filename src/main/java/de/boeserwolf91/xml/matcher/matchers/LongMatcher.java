package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;

public class LongMatcher implements Matcher<Long>
{
    public Long match(String string)
    {
        return Long.parseLong(string);
    }
}
