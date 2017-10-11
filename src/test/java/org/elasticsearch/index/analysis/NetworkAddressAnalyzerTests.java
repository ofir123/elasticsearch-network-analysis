package org.elasticsearch.index.analysis;

public class NetworkAddressAnalyzerTests extends BaseAnalyzerTest {

    public void testNetworkAddressAnalyzerSingleDigitIp() throws Exception {
        testAnalyzer(NetworkAddressAnalyzer.NAME, "aa 1.2.3.4 bb acw", "1", "2", "3", "4");
    }

    public void testNetworkAddressAnalyzerIpWithTrailing() throws Exception {
        testAnalyzer(NetworkAddressAnalyzer.NAME, "acw 22.44.33.42.1", "22", "44", "33", "42");
    }

    public void testNetworkAddressAnalyzerMac() throws Exception {
        testAnalyzer(NetworkAddressAnalyzer.NAME, "22:4e:b2:53:d7:93 kn", "22", "4e", "b2", "53", "d7", "93");
    }

    public void testNetworkAddressAnalyzerMac2() throws Exception {
        testAnalyzer(NetworkAddressAnalyzer.NAME, "93 acDD.cccc.AAaa kn", "ac", "dd", "cc", "cc", "aa", "aa");
    }

    public void testNetworkAddressAnalyzerMac3() throws Exception {
        testAnalyzer(NetworkAddressAnalyzer.NAME, "kn 224e:b253:d793", "22", "4e", "b2", "53", "d7", "93");
    }

    public void testNetworkAddressAnalyzerMulti() throws Exception {
        testAnalyzerIgnoringPosition(NetworkAddressAnalyzer.NAME,
                                     "aa 1.2.3.4 bb acw 22.44.33.42.1 22:4e:b2:53:d7:93 acDD.cccc.AAaa kn " +
                                     "224e:b253:d793",
                                     "1", "2", "3", "4", "22", "44", "33", "42", "22", "4e", "b2", "53", "d7", "93",
                                     "ac", "dd", "cc", "cc", "aa", "aa", "22", "4e", "b2", "53", "d7", "93");
    }
}
