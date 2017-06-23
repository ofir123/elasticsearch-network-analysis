package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;


public class NetworkAddressAnalyzerProvider extends AbstractIndexAnalyzerProvider<NetworkAddressAnalyzer> {

    private final NetworkAddressAnalyzer analyzer;

    @Inject
    public NetworkAddressAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        analyzer = new NetworkAddressAnalyzer(settings);
    }

    @Override
    public NetworkAddressAnalyzer get() {
        return this.analyzer;
    }
}
