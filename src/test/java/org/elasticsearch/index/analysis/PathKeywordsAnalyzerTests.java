package org.elasticsearch.index.analysis;

import org.elasticsearch.test.ESTestCase;
import org.junit.Test;

public class PathKeywordsAnalyzerTests extends ESTestCase {

	@Test
    public void testPathKeywordsAnalyzer() throws Exception {
		AnalysisTestUtils.testAnalyzer(new PathKeywordsAnalysisBinderProcessor(), 
				"pathkeywords", 
				"/network/logs/bla\\test", 
        		"network", "logs", "bla", "test");
    }
	
}
