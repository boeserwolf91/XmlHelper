package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;

public class CharMatcher implements Matcher<Character>
{
    public Character match(String string)
    {
        return string.charAt(0);
    }
}
