package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.inject.ModulesBuilder;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsModule;
import org.elasticsearch.env.Environment;
import org.elasticsearch.env.EnvironmentModule;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexNameModule;

import org.elasticsearch.index.settings.IndexSettingsModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.elasticsearch.test.ESTestCase;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class AnalysisTestUtils extends ESTestCase {

    public static void testAnalyzer(AnalysisModule.AnalysisBinderProcessor analysisBinderProcessor, 
    		String analyzerName, String source, String... expected_terms) throws IOException {
    	Settings settings = Settings.settingsBuilder()
                .put("path.home", createTempDir()).build();
    	AnalysisService analysisService = createAnalysisService(analysisBinderProcessor, settings);

        Analyzer analyzer = analysisService.analyzer(analyzerName).analyzer();

        TokenStream ts = analyzer.tokenStream("test", source);

        CharTermAttribute term1 = ts.addAttribute(CharTermAttribute.class);
        ts.reset();

        for (String expected : expected_terms) {
            assertThat(ts.incrementToken(), equalTo(true));
            assertThat(term1.toString(), equalTo(expected));
        }
    }
    
    private static AnalysisService createAnalysisService(AnalysisModule.AnalysisBinderProcessor analysisBinderProcessor, 
    		Settings settings) {
    	Index index = new Index("test");
        Settings indexSettings = Settings.settingsBuilder().put(settings)
                .put(IndexMetaData.SETTING_VERSION_CREATED, Version.CURRENT)
                .build();
        Injector parentInjector = new ModulesBuilder().add(new SettingsModule(settings), new EnvironmentModule(new Environment(settings))).createInjector();
        Injector injector = new ModulesBuilder().add(
                new IndexSettingsModule(index, indexSettings),
                new IndexNameModule(index),
                new AnalysisModule(settings, parentInjector.getInstance(IndicesAnalysisService.class)).addProcessor(analysisBinderProcessor))
                .createChildInjector(parentInjector);

        return injector.getInstance(AnalysisService.class);
    }
}
