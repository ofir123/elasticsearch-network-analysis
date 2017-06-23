package org.elasticsearch.index.analysis;

import org.elasticsearch.test.ESTestCase;


public class PartialNetworkAddressAnalyzerTests extends ESTestCase {

    public void testPartialNetworkAddressAnalyzer() throws Exception {
		AnalysisTestUtils.testAnalyzer("partial_network_address", 
				"1.2.3\n1.2.30\nAD:cc:xx\n", 
        		"1", "2", "3", "1", "2", "30", "ad", "cc");
    }
	
}
