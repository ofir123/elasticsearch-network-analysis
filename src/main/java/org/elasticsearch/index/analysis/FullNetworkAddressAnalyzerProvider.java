package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;


public class FullNetworkAddressAnalyzerProvider extends AbstractIndexAnalyzerProvider<FullNetworkAddressAnalyzer> {

    private final FullNetworkAddressAnalyzer analyzer;

    @Inject
    public FullNetworkAddressAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        analyzer = new FullNetworkAddressAnalyzer(settings);
    }

    @Override
    public FullNetworkAddressAnalyzer get() {
        return this.analyzer;
    }
}
