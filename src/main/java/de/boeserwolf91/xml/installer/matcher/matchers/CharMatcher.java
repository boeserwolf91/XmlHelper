package de.boeserwolf91.xml.installer.matcher.matchers;

import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public class CharMatcher implements Matcher<Character>
{
    public Character match(String string, MatcherManager manager)
    {
        return string.charAt(0);
    }
}
