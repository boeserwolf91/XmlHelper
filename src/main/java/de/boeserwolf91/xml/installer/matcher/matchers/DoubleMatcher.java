package de.boeserwolf91.xml.installer.matcher.matchers;

import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public class DoubleMatcher implements Matcher<Double>
{
    public Double match(String string, MatcherManager manager)
    {
        return Double.parseDouble(string);
    }
}
