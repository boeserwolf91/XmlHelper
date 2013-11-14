package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;

public class IntegerMatcher implements Matcher<Integer>
{
    public Integer match(String string)
    {
        return Integer.parseInt(string);
    }
}
