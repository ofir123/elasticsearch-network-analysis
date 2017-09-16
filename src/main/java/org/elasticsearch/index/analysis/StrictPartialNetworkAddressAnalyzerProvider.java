package org.elasticsearch.index.analysis;

import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;

public class StrictPartialNetworkAddressAnalyzerProvider
        extends AbstractIndexAnalyzerProvider<StrictPartialNetworkAddressAnalyzer> {

    private final StrictPartialNetworkAddressAnalyzer analyzer;

    @Inject
    @SuppressWarnings("unused")
    public StrictPartialNetworkAddressAnalyzerProvider(IndexSettings indexSettings, Environment env, String name,
                                                       Settings settings) {

        super(indexSettings, name, settings);
        analyzer = new StrictPartialNetworkAddressAnalyzer(settings);
    }

    @Override
    public StrictPartialNetworkAddressAnalyzer get() {
        return this.analyzer;
    }
}
