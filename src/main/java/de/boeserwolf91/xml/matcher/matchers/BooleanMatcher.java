package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;

public class BooleanMatcher implements Matcher<Boolean>
{
    public Boolean match(String string)
    {
        return Boolean.parseBoolean(string);
    }
}
