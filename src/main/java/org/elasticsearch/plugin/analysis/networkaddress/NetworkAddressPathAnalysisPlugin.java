package org.elasticsearch.plugin.analysis.networkaddress;

import com.google.common.collect.ImmutableMap;
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Map;

public class NetworkAddressPathAnalysisPlugin extends Plugin implements AnalysisPlugin {

    @Override
    public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        return ImmutableMap.<String, AnalysisProvider<TokenFilterFactory>>builder()
                .put(IncrementalCaptureGroupTokenFilter.NAME, IncrementalCaptureGroupTokenFilterFactory::new)
                .build();
    }

    @Override
    public Map<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        return ImmutableMap.<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>>builder()
                .put(NetworkAddressAnalyzer.NAME, NetworkAddressAnalyzerProvider::new)
                .put(PartialNetworkAddressAnalyzer.NAME, PartialNetworkAddressAnalyzerProvider::new)
                .put(StrictPartialNetworkAddressAnalyzer.NAME, StrictPartialNetworkAddressAnalyzerProvider::new)
                .put(FullNetworkAddressAnalyzer.NAME, FullNetworkAddressAnalyzerProvider::new)
                .put(PathKeywordsAnalyzer.NAME, PathKeywordsAnalyzerProvider::new)
                .build();
    }
}
