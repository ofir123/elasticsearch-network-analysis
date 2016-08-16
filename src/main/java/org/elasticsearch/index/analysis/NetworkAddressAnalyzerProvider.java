package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

/**
 */
public class NetworkAddressAnalyzerProvider extends AbstractIndexAnalyzerProvider<NetworkAddressAnalyzer> {

    private final NetworkAddressAnalyzer analyzer;

    @Inject
    public NetworkAddressAnalyzerProvider(Index index, IndexSettingsService indexSettingsService, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        analyzer = new NetworkAddressAnalyzer(settings);
    }

    @Override
    public NetworkAddressAnalyzer get() {
        return this.analyzer;
    }
}
