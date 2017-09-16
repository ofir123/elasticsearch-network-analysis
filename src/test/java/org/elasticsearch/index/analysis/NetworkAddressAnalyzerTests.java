package org.elasticsearch.index.analysis;

public class NetworkAddressAnalyzerTests extends BaseAnalyzerTest {

    public void testNetworkAddressAnalyzer() throws Exception {
        testAnalyzer(NetworkAddressAnalyzer.NAME,
                     "aa 1.2.3.4 bb acw 22.44.33.42.1 22:4e:b2:53:d7:93 acDD.cccc.AAaa kn",
                     "1", "2", "3", "4", "22", "44", "33", "42", "22", "4e", "b2", "53", "d7",
                     "93", "ac", "dd", "cc", "cc", "aa", "aa");
    }
}
