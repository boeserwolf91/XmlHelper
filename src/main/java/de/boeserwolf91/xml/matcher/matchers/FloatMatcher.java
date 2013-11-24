package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;
import de.boeserwolf91.xml.matcher.MatcherManager;

public class FloatMatcher implements Matcher<Float>
{
    public Float match(String string, MatcherManager manager)
    {
        return Float.parseFloat(string);
    }
}
