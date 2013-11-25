package de.boeserwolf91.xml.installer.matcher.matchers;

import java.util.Locale;

import de.boeserwolf91.xml.installer.matcher.Matcher;
import de.boeserwolf91.xml.installer.matcher.MatcherManager;

public class LocaleMatcher implements Matcher<Locale>
{
    public Locale match(String string, MatcherManager manager)
    {
        return Locale.forLanguageTag(string);
    }
}
