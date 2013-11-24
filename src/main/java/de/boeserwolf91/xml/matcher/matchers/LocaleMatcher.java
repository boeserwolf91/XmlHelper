package de.boeserwolf91.xml.matcher.matchers;

import java.util.Locale;

import de.boeserwolf91.xml.matcher.Matcher;
import de.boeserwolf91.xml.matcher.MatcherManager;

public class LocaleMatcher implements Matcher<Locale>
{
    public Locale match(String string, MatcherManager manager)
    {
        return Locale.forLanguageTag(string);
    }
}
