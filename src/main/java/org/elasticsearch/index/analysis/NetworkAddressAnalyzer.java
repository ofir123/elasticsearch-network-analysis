package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.pattern.PatternTokenizer;
import org.elasticsearch.common.regex.Regex;
import org.elasticsearch.common.settings.Settings;

import java.util.regex.Pattern;

import static org.elasticsearch.index.analysis.Patterns.IP_PART;
import static org.elasticsearch.index.analysis.Patterns.MAC_PART;

public final class NetworkAddressAnalyzer extends Analyzer {

    private static final String IP_PATTERN = Patterns.repeat(Patterns.toGroup(IP_PART), "\\.", 4);
    private static final String MAC_PATTERN1 = Patterns.repeat(Patterns.toGroup(MAC_PART), "-", 6);
    private static final String MAC_PATTERN2 = Patterns.repeat(Patterns.toGroup(MAC_PART), ":", 6);
    private static final String MAC_PATTERN3 = Patterns.repeat(Patterns.toGroup(MAC_PART), "_", 6);
    private static final String MAC_PATTERN4 = Patterns.repeat(Patterns.toGroup(MAC_PART), "\\.", 6);
    private static final String MAC_PATTERN5 = Patterns.repeat(
            Patterns.toGroup(MAC_PART) + Patterns.toGroup(MAC_PART), ":", 3);
    private static final String MAC_PATTERN6 = Patterns.repeat(
            Patterns.toGroup(MAC_PART) + Patterns.toGroup(MAC_PART), "\\.", 3);

    private final Pattern pattern;
    private final int group;

    public NetworkAddressAnalyzer(Settings settings) {
        String sPattern = settings.get("pattern", Patterns.combinePatterns(IP_PATTERN, MAC_PATTERN1, MAC_PATTERN2, MAC_PATTERN3,
                                                                           MAC_PATTERN4, MAC_PATTERN5, MAC_PATTERN6));

        this.pattern = Regex.compile(sPattern, settings.get("flags"));
        this.group = settings.getAsInt("group", 0);

    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new PatternTokenizer(pattern, group);
        TokenStream lowered = new LowerCaseFilter(source);
        TokenStream result = new IncrementalCaptureGroupTokenFilter(lowered, this.pattern);
        return new TokenStreamComponents(source, result);
    }
}
