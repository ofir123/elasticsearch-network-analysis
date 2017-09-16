package org.elasticsearch.index.analysis;

public class PathKeywordsAnalyzerTests extends BaseAnalyzerTest {

    public void testPathKeywordsAnalyzer() throws Exception {
        testAnalyzer("path_keywords",
                     "/network/logs/bla\\test",
                     "network", "logs", "bla", "test");
    }
}
