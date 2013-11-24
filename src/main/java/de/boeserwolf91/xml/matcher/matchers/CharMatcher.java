package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;
import de.boeserwolf91.xml.matcher.MatcherManager;

public class CharMatcher implements Matcher<Character>
{
    public Character match(String string, MatcherManager manager)
    {
        return string.charAt(0);
    }
}
