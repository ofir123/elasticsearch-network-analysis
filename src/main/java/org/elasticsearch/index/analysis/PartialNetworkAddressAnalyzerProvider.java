package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

public class PartialNetworkAddressAnalyzerProvider extends AbstractIndexAnalyzerProvider<PartialNetworkAddressAnalyzer> {

    private final PartialNetworkAddressAnalyzer analyzer;

    @Inject
    public PartialNetworkAddressAnalyzerProvider(Index index, IndexSettingsService indexSettingsService, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        analyzer = new PartialNetworkAddressAnalyzer(settings);
    }

    @Override
    public PartialNetworkAddressAnalyzer get() {
        return this.analyzer;
    }
}
