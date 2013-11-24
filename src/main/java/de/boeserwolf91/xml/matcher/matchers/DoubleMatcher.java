package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;
import de.boeserwolf91.xml.matcher.MatcherManager;

public class DoubleMatcher implements Matcher<Double>
{
    public Double match(String string, MatcherManager manager)
    {
        return Double.parseDouble(string);
    }
}
