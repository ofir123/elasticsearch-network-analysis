package org.elasticsearch.index.analysis;

import org.elasticsearch.common.settings.Settings;

import static org.elasticsearch.index.analysis.Patterns.*;

public final class FullNetworkAddressAnalyzer extends BasePatternAnalyzer {

    public static String NAME = "full_network_address";

    private static final String IP_PATTERN = repeat(toNonCapturingGroup(IP_PART), "\\.", 4);
    private static final String MAC_PATTERN1 = repeat(MAC_PART, "-", 6);
    private static final String MAC_PATTERN2 = repeat(MAC_PART, ":", 6);
    private static final String MAC_PATTERN3 = repeat(MAC_PART, "_", 6);
    private static final String MAC_PATTERN4 = repeat(MAC_PART, "\\.", 6);
    private static final String MAC_PATTERN5 = repeat(MAC_PART + MAC_PART, ":", 3);
    private static final String MAC_PATTERN6 = repeat(MAC_PART + MAC_PART, "\\.", 3);
    private static final String COMBINED_PATTERN = combinePatterns(IP_PATTERN, MAC_PATTERN1, MAC_PATTERN2, MAC_PATTERN3,
                                                                   MAC_PATTERN4, MAC_PATTERN5, MAC_PATTERN6);

    public FullNetworkAddressAnalyzer(Settings settings) {
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
}
