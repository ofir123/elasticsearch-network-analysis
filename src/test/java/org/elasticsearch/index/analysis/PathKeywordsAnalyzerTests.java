package org.elasticsearch.index.analysis;

public class PathKeywordsAnalyzerTests extends BaseAnalyzerTest {

    public void testPathKeywordsAnalyzer() throws Exception {
        testAnalyzer(PathKeywordsAnalyzer.NAME,
                     "/network/logs/bla\\test",
                     "network", "logs", "bla", "test");
    }
}
