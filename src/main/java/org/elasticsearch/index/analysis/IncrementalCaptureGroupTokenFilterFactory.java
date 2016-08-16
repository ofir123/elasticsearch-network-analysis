package org.elasticsearch.index.analysis;

import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.regex.Regex;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.settings.IndexSettingsService;

public class IncrementalCaptureGroupTokenFilterFactory extends AbstractTokenFilterFactory {
    
	private Pattern pattern;
    
	@Inject
    public IncrementalCaptureGroupTokenFilterFactory(Index index, IndexSettingsService indexSettingsService, @Assisted String name, @Assisted Settings settings) {
        this(index, indexSettingsService.getSettings(), name, settings);
    }
	
	public IncrementalCaptureGroupTokenFilterFactory(Index index, Settings indexSettings, String name, Settings settings) {
        super(index, indexSettings, name, settings);
		// PatternAnalyzer.NON_WORD_PATTERN.
		String sPattern = settings.get("pattern", "(\\w+)");

		this.pattern = Regex.compile(sPattern, settings.get("flags"));
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new IncrementalCaptureGroupTokenFilter(tokenStream, pattern);
    }
}
