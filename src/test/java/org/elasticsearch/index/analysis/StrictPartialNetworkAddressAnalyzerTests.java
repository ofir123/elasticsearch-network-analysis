package org.elasticsearch.index.analysis;

public class StrictPartialNetworkAddressAnalyzerTests extends BaseAnalyzerTest {

    public void testStrictPartialNetworkAddressAnalyzerSingleDigit() throws Exception {
        testAnalyzer(StrictPartialNetworkAddressAnalyzer.NAME, "1.2.3", "1", "2", "3");
    }

    public void testStrictPartialNetworkAddressAnalyzerMultiDigit() throws Exception {
        testAnalyzer(StrictPartialNetworkAddressAnalyzer.NAME, "1.2.30", "1", "2", "30");
    }

    public void testStrictPartialNetworkAddressAnalyzerRedundantString() throws Exception {
        testAnalyzer(StrictPartialNetworkAddressAnalyzer.NAME, "AD:cc:xx", "ad", "cc");
    }

    public void testStrictPartialNetworkAddressAnalyzerUnrelatedNumber() throws Exception {
        testAnalyzer(StrictPartialNetworkAddressAnalyzer.NAME, "1");
    }

    public void testStrictPartialNetworkAddressAnalyzerNonStrict() throws Exception {
        testAnalyzer(StrictPartialNetworkAddressAnalyzer.NAME, "aa xx");
    }

    public void testStrictPartialNetworkAddressAnalyzerMac() throws Exception {
        testAnalyzer(StrictPartialNetworkAddressAnalyzer.NAME, "1a:2b:3c:4d:5e:6f", "1a", "2b", "3c", "4d", "5e", "6f");
    }

    public void testStrictPartialNetworkAddressAnalyzerMulti() throws Exception {
        testAnalyzerIgnoringPosition(StrictPartialNetworkAddressAnalyzer.NAME,
                                     "1.2.3\n1.2.30\nAD:cc:xx\n1\naa xx\n1.2",
                                     "1", "2", "3", "1", "2", "30", "ad", "cc", "1", "2");
    }
}
