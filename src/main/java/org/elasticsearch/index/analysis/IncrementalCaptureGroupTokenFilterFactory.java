package org.elasticsearch.index.analysis;

import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.regex.Regex;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;


public class IncrementalCaptureGroupTokenFilterFactory extends AbstractTokenFilterFactory {
    
	private Pattern pattern;
    
	public IncrementalCaptureGroupTokenFilterFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);
		// PatternAnalyzer.NON_WORD_PATTERN.
		String sPattern = settings.get("pattern", "(\\w+)");

		this.pattern = Regex.compile(sPattern, settings.get("flags"));
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new IncrementalCaptureGroupTokenFilter(tokenStream, pattern);
    }
}
