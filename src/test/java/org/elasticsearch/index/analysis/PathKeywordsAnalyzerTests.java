package org.elasticsearch.index.analysis;

import org.elasticsearch.test.ESTestCase;


public class PathKeywordsAnalyzerTests extends ESTestCase {

    public void testPathKeywordsAnalyzer() throws Exception {
		AnalysisTestUtils.testAnalyzer("path_keywords", 
				"/network/logs/bla\\test", 
        		"network", "logs", "bla", "test");
    }
	
}
