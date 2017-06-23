package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;


public class PartialNetworkAddressAnalyzerProvider extends AbstractIndexAnalyzerProvider<PartialNetworkAddressAnalyzer> {

    private final PartialNetworkAddressAnalyzer analyzer;

    @Inject
    public PartialNetworkAddressAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        analyzer = new PartialNetworkAddressAnalyzer(settings);
    }

    @Override
    public PartialNetworkAddressAnalyzer get() {
        return this.analyzer;
    }
}
