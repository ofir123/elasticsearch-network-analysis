package org.elasticsearch.index.analysis;

public class FullNetworkAddressAnalyzerTests extends BaseAnalyzerTest {

    public void testFullNetworkAddressAnalyzer() throws Exception {
        testAnalyzer(FullNetworkAddressAnalyzer.NAME,
                     "aa 1.2.3.4 bb acw 22.44.33.42.1 22:4e:b2:53:d7:93 acDD.cccc.AAaa kn 224e:b253:d793",
                     "1.2.3.4", "22.44.33.42", "22:4e:b2:53:d7:93", "acdd.cccc.aaaa", "224e:b253:d793");
    }
}
