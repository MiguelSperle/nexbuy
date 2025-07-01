package com.miguelsperle.nexbuy.core.application.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugUtils {
    private static final Pattern NON_LATIN_PATTERN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("[\\s]");
    private static final Pattern EDGE_DASHES_PATTERN = Pattern.compile("(^-|-$)");

    public static String createSlug(final String input) {
        if (input == null || input.trim().isBlank()) return null;
        final String noWhitespace = WHITESPACE_PATTERN.matcher(input).replaceAll("-");
        final String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        final String withoutNonLatin = NON_LATIN_PATTERN.matcher(normalized).replaceAll("");
        final String withoutEdges = EDGE_DASHES_PATTERN.matcher(withoutNonLatin).replaceAll("");
        return withoutEdges.toLowerCase(Locale.ENGLISH);
    }
}
