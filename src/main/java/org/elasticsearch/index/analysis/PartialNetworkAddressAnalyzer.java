package org.elasticsearch.index.analysis;

import org.elasticsearch.common.settings.Settings;

public final class PartialNetworkAddressAnalyzer extends BasePatternAnalyzer {

    public static final String NAME = "partial_network_address";

    public PartialNetworkAddressAnalyzer(Settings settings) {
        super(settings);
    }

    @Override
    protected String defaultPattern() {
        return Patterns.NETWORK_ADDRESS_SEPARATOR;
    }

    @Override
    protected int defaultGroup() {
        return -1;
    }
}
