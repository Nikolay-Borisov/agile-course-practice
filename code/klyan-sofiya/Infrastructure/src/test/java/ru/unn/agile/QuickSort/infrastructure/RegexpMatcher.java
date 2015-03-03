package ru.unn.agile.QuickSort.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegexpMatcher extends BaseMatcher {
    private final String regexp;

    public RegexpMatcher(final String regexp) {
        this.regexp = regexp;
    }

    public void describeTo(final Description descr) {
        descr.appendText("have match with regex = ");
        descr.appendText(regexp);
    }

    public static Matcher<? super String> havePatternMatch(final String regexp) {
        RegexpMatcher matcher = new RegexpMatcher(regexp);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>) matcher;
        return castedMatcher;
    }

    public boolean matches(final Object obj) {
        String convObj = (String) obj;
        return convObj.matches(regexp);
    }

}
