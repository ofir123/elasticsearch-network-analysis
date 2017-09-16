package org.elasticsearch.index.analysis;

public class StrictPartialNetworkAddressAnalyzerTests extends BaseAnalyzerTest {

    public void testStrictPartialNetworkAddressAnalyzer() throws Exception {
        testAnalyzer(StrictPartialNetworkAddressAnalyzer.NAME,
                     "1.2.3\n1.2.30\nAD:cc:xx\n1\naa xx\n1.2",
                    "1", "2", "3", "1", "2", "30", "ad", "cc", "1", "2");
    }
}
