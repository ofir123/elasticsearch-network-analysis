package org.elasticsearch.index.analysis;

import com.google.common.collect.Lists;
import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;

import java.util.List;
import java.util.regex.Pattern;

import static org.elasticsearch.index.analysis.Patterns.*;

/**
 * Similar to {@link PartialNetworkAddressAnalyzer}, but will pick up strings that have two or more parts that look like
 * a network address parts. For example:
 * "10" -> no tokens
 * "10.0" -> "10", "0"
 * "AA:BB -> "AA", "BB"
 */
public final class StrictPartialNetworkAddressAnalyzer extends BasePatternAnalyzer {

    public static final String NAME = "strict_partial_network_address";

    private static final int MAX_MAC_PART_COUNT = 6;
    private static final int MAX_IP_PART_COUNT = 4;
    private static final int MIN_PART_COUNT = 2;
    private static final String COMBINED_PATTERN = createPatterns();

    private static String createPatterns() {
        List<String> patterns = Lists.newArrayList();
        for (int i = MAX_MAC_PART_COUNT; i >= MIN_PART_COUNT; i--) {
            for (String macSeparator : MAC_SEPARATORS) {
                patterns.add(rawRepeat(toGroup(MAC_PART), macSeparator, i));
            }

            if (i <= MAX_IP_PART_COUNT) {
                patterns.add(rawRepeat(toGroup(IP_PART), "\\.", i));
            }
        }

        return combinePatterns(patterns);
    }

    public StrictPartialNetworkAddressAnalyzer(Settings settings) {
        super(settings);
    }

    @Override
    protected String defaultPattern() {
        return COMBINED_PATTERN;
    }

    @Override
    protected int defaultGroup() {
        return 0;
    }

    @Override
    protected TokenStream applyFilters(TokenStream tokenStream) {
        TokenStream stream = super.applyFilters(tokenStream);
        return new IncrementalCaptureGroupTokenFilter(stream, pattern);
    }
}
