package de.boeserwolf91.xml.matcher.matchers;

import de.boeserwolf91.xml.matcher.Matcher;

public class FloatMatcher implements Matcher<Float>
{
    public Float match(String string)
    {
        return Float.parseFloat(string);
    }
}
