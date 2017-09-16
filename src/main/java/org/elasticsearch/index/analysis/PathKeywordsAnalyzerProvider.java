package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public class PathKeywordsAnalyzerProvider extends AbstractIndexAnalyzerProvider<PathKeywordsAnalyzer> {

    private final PathKeywordsAnalyzer analyzer;

    @Inject
    @SuppressWarnings("unused")
    public PathKeywordsAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        analyzer = new PathKeywordsAnalyzer(settings);
    }

    @Override
    public PathKeywordsAnalyzer get() {
        return this.analyzer;
    }
}
