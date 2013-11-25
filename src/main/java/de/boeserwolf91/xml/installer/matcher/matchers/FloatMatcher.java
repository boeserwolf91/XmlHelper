package de.boeserwolf91.xml.installer.matcher.matchers;

import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public class FloatMatcher implements Matcher<Float>
{
    public Float match(String string, MatcherManager manager)
    {
        return Float.parseFloat(string);
    }
}
