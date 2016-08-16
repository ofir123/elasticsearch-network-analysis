package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

public class FullNetworkAddressAnalyzerProvider extends AbstractIndexAnalyzerProvider<FullNetworkAddressAnalyzer> {

    private final FullNetworkAddressAnalyzer analyzer;

    @Inject
    public FullNetworkAddressAnalyzerProvider(Index index, IndexSettingsService indexSettingsService, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        analyzer = new FullNetworkAddressAnalyzer(settings);
    }

    @Override
    public FullNetworkAddressAnalyzer get() {
        return this.analyzer;
    }
}
