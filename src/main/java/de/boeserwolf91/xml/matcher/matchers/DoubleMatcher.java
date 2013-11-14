package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;

public class DoubleMatcher implements Matcher<Double>
{
    public Double match(String string)
    {
        return Double.parseDouble(string);
    }
}
