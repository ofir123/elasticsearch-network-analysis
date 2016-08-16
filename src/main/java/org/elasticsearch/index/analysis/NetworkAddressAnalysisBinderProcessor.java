package org.elasticsearch.index.analysis;

public class NetworkAddressAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("networkaddress", NetworkAddressAnalyzerProvider.class);
    }

    @Override
    public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {
        tokenFiltersBindings.processTokenFilter("networkaddress", IncrementalCaptureGroupTokenFilterFactory.class);
    }
}
