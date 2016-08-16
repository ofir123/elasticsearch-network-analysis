package org.elasticsearch.index.analysis;

import org.elasticsearch.test.ESTestCase;
import org.junit.Test;

public class FullNetworkAddressAnalyzerTests extends ESTestCase {

	@Test
    public void testFullNetworkAddressAnalyzer() throws Exception {
		AnalysisTestUtils.testAnalyzer(new FullNetworkAddressAnalysisBinderProcessor(), 
				"fullnetworkaddress", 
				"aa 1.2.3.4 bb acw 22.44.33.42.1 22:4e:b2:53:d7:93 acDD.cccc.AAaa kn", 
        		"1.2.3.4", "22.44.33.42", "22:4e:b2:53:d7:93");
    }
	
}
