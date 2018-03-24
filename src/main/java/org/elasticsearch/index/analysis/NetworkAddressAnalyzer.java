package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;

import static org.elasticsearch.index.analysis.Patterns.*;

public final class NetworkAddressAnalyzer extends BasePatternAnalyzer {

    public static final String NAME = "network_address";

    private static final String IP_PATTERN = repeat(toGroup(IP_PART), "\\.", 4);
    private static final String MAC_PATTERN1 = repeat(toGroup(MAC_PART), "-", 6);
    private static final String MAC_PATTERN2 = repeat(toGroup(MAC_PART), ":", 6);
    private static final String MAC_PATTERN3 = repeat(toGroup(MAC_PART), "_", 6);
    private static final String MAC_PATTERN4 = repeat(toGroup(MAC_PART), "\\.", 6);
    private static final String MAC_PATTERN5 = repeat(toGroup(MAC_PART) + toGroup(MAC_PART), ":", 3);
    private static final String MAC_PATTERN6 = repeat(toGroup(MAC_PART) + toGroup(MAC_PART), "\\.", 3);
    private static final String COMBINE_PATTERNS = combinePatterns(IP_PATTERN, MAC_PATTERN1, MAC_PATTERN2, MAC_PATTERN3,
                                                                   MAC_PATTERN4, MAC_PATTERN5, MAC_PATTERN6);

    public NetworkAddressAnalyzer(Settings settings) {
        super(settings);
    }

    @Override
    protected String defaultPattern() {
        return COMBINE_PATTERNS;
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
