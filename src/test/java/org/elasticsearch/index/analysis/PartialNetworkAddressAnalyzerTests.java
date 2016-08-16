package org.elasticsearch.index.analysis;

import org.elasticsearch.test.ESTestCase;
import org.junit.Test;

public class PartialNetworkAddressAnalyzerTests extends ESTestCase {

	@Test
    public void testPartialNetworkAddressAnalyzer() throws Exception {
		AnalysisTestUtils.testAnalyzer(new PartialNetworkAddressAnalysisBinderProcessor(), 
				"partialnetworkaddress", 
				"1.2.3\n1.2.30\nAD:cc:xx\n", 
        		"1", "2", "3", "1", "2", "30", "ad", "cc");
    }
	
}
