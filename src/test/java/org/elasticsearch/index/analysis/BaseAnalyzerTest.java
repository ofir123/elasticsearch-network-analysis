package org.elasticsearch.index.analysis;

import com.google.common.collect.Lists;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.plugin.analysis.networkaddress.NetworkAddressPathAnalysisPlugin;
import org.elasticsearch.test.ESTestCase;

import java.io.IOException;
import java.util.List;

public abstract class BaseAnalyzerTest extends ESTestCase {

    public static void testAnalyzer(String analyzerName, String source, String... expectedTokens) throws IOException {
        Analyzer analyzer = createTestAnalyzer(analyzerName);
        List<String> actualTokens = runAnalysis(source, analyzer);

        // Due to poor imports of ESTestCase...
        org.assertj.core.api.Assertions.assertThat(actualTokens).containsExactly(expectedTokens);
    }

    private static Analyzer createTestAnalyzer(String analyzerName) throws IOException {
        TestAnalysis analysis = createTestAnalysis(new Index("test", "_na_"),
                                                   Settings.EMPTY,
                                                   new NetworkAddressPathAnalysisPlugin());

        return analysis.indexAnalyzers.get(analyzerName).analyzer();
    }

    private static List<String> runAnalysis(String source, Analyzer analyzer) throws IOException {
        TokenStream ts = analyzer.tokenStream("test", source);
        CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
        ts.reset();
        List<String> actualTokens = Lists.newArrayList();

        while (ts.incrementToken()) {
            actualTokens.add(term.toString());
        }

        return actualTokens;
    }
}
