package de.boeserwolf91.xml.matcher.matchers;

import java.util.Locale;

import de.boeserwolf91.xml.matcher.Matcher;

public class LocaleMatcher implements Matcher<Locale>
{
    public Locale match(String string)
    {
        return Locale.forLanguageTag(string);
    }
}
