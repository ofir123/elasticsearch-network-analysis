package org.elasticsearch.index.analysis;

public class FullNetworkAddressAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("fullnetworkaddress", FullNetworkAddressAnalyzerProvider.class);
    }

}
