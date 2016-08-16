package org.elasticsearch.index.analysis;

import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.pattern.PatternTokenizer;
import org.elasticsearch.common.regex.Regex;
import org.elasticsearch.common.settings.Settings;


public final class PathKeywordsAnalyzer extends Analyzer {
	private final Pattern pattern;
	private final int group;
	
    public PathKeywordsAnalyzer(Settings settings) {
		String sPattern = settings.get("pattern", "[/\\\\]");

		this.pattern = Regex.compile(sPattern, settings.get("flags"));
		this.group = settings.getAsInt("group", -1);
		
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
    	Tokenizer source = new PatternTokenizer(pattern, group);
    	TokenStream result = new LowerCaseFilter(source);
        return new TokenStreamComponents(source, result);
    }
}
