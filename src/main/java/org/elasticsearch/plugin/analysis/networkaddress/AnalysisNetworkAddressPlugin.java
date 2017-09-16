package org.elasticsearch.plugin.analysis.networkaddress;

import com.google.common.collect.ImmutableMap;
import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.Map;

public class AnalysisNetworkAddressPlugin extends Plugin implements AnalysisPlugin {

    @Override
    public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        return ImmutableMap.<String, AnalysisProvider<TokenFilterFactory>>builder()
                .put("incremental_capture_group", IncrementalCaptureGroupTokenFilterFactory::new)
                .build();
    }

    @Override
    public Map<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        return ImmutableMap.<String, AnalysisProvider<AnalyzerProvider<? extends Analyzer>>>builder()
                .put("network_address", NetworkAddressAnalyzerProvider::new)
                .put("partial_network_address", PartialNetworkAddressAnalyzerProvider::new)
                .put("full_network_address", FullNetworkAddressAnalyzerProvider::new)
                .put("path_keywords", PathKeywordsAnalyzerProvider::new)
                .build();
    }
}
