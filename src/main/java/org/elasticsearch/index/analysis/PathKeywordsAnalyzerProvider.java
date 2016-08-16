package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

public class PathKeywordsAnalyzerProvider extends AbstractIndexAnalyzerProvider<PathKeywordsAnalyzer> {

    private final PathKeywordsAnalyzer analyzer;

    @Inject
    public PathKeywordsAnalyzerProvider(Index index, IndexSettingsService indexSettingsService, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        analyzer = new PathKeywordsAnalyzer(settings);
    }

    @Override
    public PathKeywordsAnalyzer get() {
        return this.analyzer;
    }
}
