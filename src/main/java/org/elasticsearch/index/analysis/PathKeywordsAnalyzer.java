package org.elasticsearch.index.analysis;

import org.elasticsearch.common.settings.Settings;

public final class PathKeywordsAnalyzer extends BasePatternAnalyzer {

    public static final String NAME = "path_keywords";

    public PathKeywordsAnalyzer(Settings settings) {
        super(settings);
    }

    @Override
    protected String defaultPattern() {
        return Patterns.PATHS_SEPARATORS;
    }

    @Override
    protected int defaultGroup() {
        return -1;
    }
}
