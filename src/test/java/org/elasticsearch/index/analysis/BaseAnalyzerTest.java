package org.elasticsearch.index.analysis;

import com.google.common.collect.Lists;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.assertj.core.api.Assertions;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.plugin.analysis.networkaddress.NetworkAddressPathAnalysisPlugin;
import org.elasticsearch.test.ESTestCase;

import java.io.IOException;
import java.util.List;

public abstract class BaseAnalyzerTest extends ESTestCase {

    public static void testAnalyzer(String analyzerName, String source, String... expectedTokens) throws IOException {
        testAnalyzer(analyzerName, source, true, expectedTokens);
    }

    public static void testAnalyzerIgnoringPosition(String analyzerName, String source, String... expectedTokens)
            throws IOException {

        testAnalyzer(analyzerName, source, false, expectedTokens);
    }

    private static void testAnalyzer(String analyzerName, String source, boolean checkPosition, String... expectedTokens) throws IOException {
        Analyzer analyzer = createTestAnalyzer(analyzerName);
        List<String> actualTokens = runAnalysis(source, analyzer, checkPosition);

        // Due to poor imports of ESTestCase...
        Assertions.assertThat(actualTokens).containsExactly(expectedTokens);
    }

    private static Analyzer createTestAnalyzer(String analyzerName) throws IOException {
        TestAnalysis analysis = createTestAnalysis(new Index("test", "_na_"),
                                                   Settings.EMPTY,
                                                   new NetworkAddressPathAnalysisPlugin());

        return analysis.indexAnalyzers.get(analyzerName).analyzer();
    }

    private static List<String> runAnalysis(String source, Analyzer analyzer, boolean checkPosition) throws IOException {
        TokenStream ts = analyzer.tokenStream("test", source);
        CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
        PositionIncrementAttribute pos = ts.addAttribute(PositionIncrementAttribute.class);
        ts.reset();

        List<String> actualTokens = Lists.newArrayList();
        boolean first = true;

        while (ts.incrementToken()) {
            if (!first && checkPosition) {
                Assertions.assertThat(pos.getPositionIncrement()).isEqualTo(1);
            }
            first = false;

            actualTokens.add(term.toString());
        }

        return actualTokens;
    }
}
