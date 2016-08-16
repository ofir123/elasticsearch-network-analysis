package org.elasticsearch.index.analysis;

public class PathKeywordsAnalysisBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {

    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("pathkeywords", PathKeywordsAnalyzerProvider.class);
    }
}
