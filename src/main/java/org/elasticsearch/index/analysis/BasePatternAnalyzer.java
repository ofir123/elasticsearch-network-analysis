package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.pattern.PatternTokenizer;
import org.elasticsearch.common.regex.Regex;
import org.elasticsearch.common.settings.Settings;

import java.util.regex.Pattern;

public abstract class BasePatternAnalyzer extends Analyzer {

    protected final Pattern pattern;
    protected final int group;

    public BasePatternAnalyzer(Settings settings) {
        String sPattern = settings.get("pattern", defaultPattern());
        this.pattern = Regex.compile(sPattern, settings.get("flags"));
        this.group = settings.getAsInt("group", defaultGroup());
    }

    protected abstract String defaultPattern();

    protected abstract int defaultGroup();

    protected TokenStream applyFilters(TokenStream tokenStream) {
        return new LowerCaseFilter(tokenStream);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer source = new PatternTokenizer(pattern, group);
        TokenStream result = applyFilters(source);
        return new TokenStreamComponents(source, result);
    }
}
