package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.pattern.PatternTokenizer;
import org.elasticsearch.common.settings.Settings;

import java.util.regex.Pattern;

public final class PartialNetworkAddressAnalyzer extends Analyzer {

    private final Pattern pattern;
    private final int group;

    public PartialNetworkAddressAnalyzer(Settings settings) {
        String sPattern = settings.get("pattern", "[^a-fA-F0-9]");

        this.pattern = Pattern.compile(sPattern, Pattern.CASE_INSENSITIVE);
        this.group = settings.getAsInt("group", -1);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new PatternTokenizer(pattern, group);
        TokenStream result = new LowerCaseFilter(source);
        return new TokenStreamComponents(source, result);
    }
}
