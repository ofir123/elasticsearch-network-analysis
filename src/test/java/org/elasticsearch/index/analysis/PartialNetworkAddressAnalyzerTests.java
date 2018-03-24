package org.elasticsearch.index.analysis;

public class PartialNetworkAddressAnalyzerTests extends BaseAnalyzerTest {

    public void testPartialNetworkAddressAnalyzer() throws Exception {
        testAnalyzer(PartialNetworkAddressAnalyzer.NAME,
                     "1.2.3\n1.2.30\nAD:cc:xx\n",
                     "1", "2", "3", "1", "2", "30", "ad", "cc");
    }
}
