package org.elasticsearch.plugin.analysis.networkaddress;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.*;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import java.util.HashMap;
import java.util.Map;


public class AnalysisNetworkAddressPlugin extends Plugin implements AnalysisPlugin {

	@Override
    public Map<String, AnalysisModule.AnalysisProvider<org.elasticsearch.index.analysis.TokenFilterFactory>> getTokenFilters() {
        Map<String, AnalysisModule.AnalysisProvider<org.elasticsearch.index.analysis.TokenFilterFactory>> extra = new HashMap<>();
        extra.put("incremental_capture_group", IncrementalCaptureGroupTokenFilterFactory::new);
        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();
		extra.put("network_address", NetworkAddressAnalyzerProvider::new);
		extra.put("partial_network_address", PartialNetworkAddressAnalyzerProvider::new);
		extra.put("full_network_address", FullNetworkAddressAnalyzerProvider::new);
		extra.put("path_keywords", PathKeywordsAnalyzerProvider::new);
		return extra;
    }
}
