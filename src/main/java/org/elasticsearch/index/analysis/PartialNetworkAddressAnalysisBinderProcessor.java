package org.elasticsearch.index.analysis;

public class PartialNetworkAddressAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("partialnetworkaddress", PartialNetworkAddressAnalyzerProvider.class);
    }
}
