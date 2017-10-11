package org.elasticsearch.index.analysis;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Patterns {

    public static final String MAC_PART = "[a-fA-F0-9][a-fA-F0-9]";
    public static final String IP_PART = "25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?";
    public static final String PATHS_SEPARATORS = "[/\\\\]";
    public static final String NETWORK_ADDRESS_SEPARATOR = "[^a-fA-F0-9]";
    public static final String[] MAC_SEPARATORS = new String[] { ":", "-", "_" };

    public static String repeat(String base, String separator, int count) {
        return combinePatterns(Collections.nCopies(count, base), separator);
    }

    public static String rawRepeat(String base, String separator, int count) {
        return rawCombinePatterns(Collections.nCopies(count, base), separator);
    }

    public static String combinePatterns(String... patterns) {
        return combinePatterns(Lists.newArrayList(patterns));
    }

    public static String combinePatterns(List<String> patterns) {
        return combinePatterns(patterns, "|");
    }

    public static String combinePatterns(List<String> patterns, String separator) {
        return rawCombinePatterns(patterns.stream()
                                          .map(Patterns::toNonCapturingGroup)
                                          .collect(Collectors.toList()),
                                  separator);
    }

    public static String rawCombinePatterns(List<String> patterns, String separator) {
        return Joiner.on(separator).join(patterns);
    }

    public static String toNonCapturingGroup(String pattern) {
        return toGroup("?:" + pattern);
    }

    public static String toGroup(String pattern) {
        return "(" + pattern + ")";
    }
}
