package org.elasticsearch.indices.analysis;

import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.AnalyzerScope;
import org.elasticsearch.index.analysis.NetworkAddressAnalyzer;
import org.elasticsearch.index.analysis.PartialNetworkAddressAnalyzer;
import org.elasticsearch.index.analysis.FullNetworkAddressAnalyzer;
import org.elasticsearch.index.analysis.PathKeywordsAnalyzer;
import org.elasticsearch.index.analysis.PreBuiltAnalyzerProviderFactory;

/**
 * Registers indices level analysis components so, if not explicitly configured, will be shared
 * among all indices.
 */
public class IndicesAnalysis extends AbstractComponent {

	@Inject
    public IndicesAnalysis(Settings settings, IndicesAnalysisService indicesAnalysisService) {
        super(settings);

        indicesAnalysisService.analyzerProviderFactories().put("networkaddress",
                new PreBuiltAnalyzerProviderFactory("networkaddress", AnalyzerScope.INDICES,
                        new NetworkAddressAnalyzer(settings)));
        
        indicesAnalysisService.analyzerProviderFactories().put("partialnetworkaddress",
                new PreBuiltAnalyzerProviderFactory("partialnetworkaddress", AnalyzerScope.INDICES,
                        new PartialNetworkAddressAnalyzer(settings)));
						
		indicesAnalysisService.analyzerProviderFactories().put("fullnetworkaddress",
                new PreBuiltAnalyzerProviderFactory("fullnetworkaddress", AnalyzerScope.INDICES,
                        new FullNetworkAddressAnalyzer(settings)));				
        
        indicesAnalysisService.analyzerProviderFactories().put("pathkeywords",
                new PreBuiltAnalyzerProviderFactory("pathkeywords", AnalyzerScope.INDICES,
                        new PathKeywordsAnalyzer(settings)));
    }
}
