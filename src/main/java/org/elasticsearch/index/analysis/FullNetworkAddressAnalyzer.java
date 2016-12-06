package org.elasticsearch.index.analysis;

import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.pattern.PatternTokenizer;
import org.elasticsearch.common.regex.Regex;
import org.elasticsearch.common.settings.Settings;


public final class FullNetworkAddressAnalyzer extends Analyzer {
	private final String IP_PATTERN = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private final String MAC_PATTERN1 = "[a-fA-F0-9][a-fA-F0-9]-[a-fA-F0-9][a-fA-F0-9]-[a-fA-F0-9][a-fA-F0-9]-[a-fA-F0-9][a-fA-F0-9]-[a-fA-F0-9][a-fA-F0-9]-[a-fA-F0-9][a-fA-F0-9]";
	private final String MAC_PATTERN2 = "[a-fA-F0-9][a-fA-F0-9]:[a-fA-F0-9][a-fA-F0-9]:[a-fA-F0-9][a-fA-F0-9]:[a-fA-F0-9][a-fA-F0-9]:[a-fA-F0-9][a-fA-F0-9]:[a-fA-F0-9][a-fA-F0-9]";
	private final String MAC_PATTERN3 = "[a-fA-F0-9][a-fA-F0-9]_[a-fA-F0-9][a-fA-F0-9]_[a-fA-F0-9][a-fA-F0-9]_[a-fA-F0-9][a-fA-F0-9]_[a-fA-F0-9][a-fA-F0-9]_[a-fA-F0-9][a-fA-F0-9]";
	private final String MAC_PATTERN4 = "[a-fA-F0-9][a-fA-F0-9]\\.[a-fA-F0-9][a-fA-F0-9]\\.[a-fA-F0-9][a-fA-F0-9]\\.[a-fA-F0-9][a-fA-F0-9]\\.[a-fA-F0-9][a-fA-F0-9]\\.[a-fA-F0-9][a-fA-F0-9]";
	private final String MAC_PATTERN5 = "[a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]:[a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]:[a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]";
	private final String MAC_PATTERN6 = "[a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]\\.[a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]\\.[a-fA-F0-9][a-fA-F0-9][a-fA-F0-9][a-fA-F0-9]";
	
	private final Pattern pattern;
	private final int group;
	
    public FullNetworkAddressAnalyzer(Settings settings) {
		String sPattern = settings.get("pattern", String.format("(?:%s)|(?:%s)|(?:%s)|(?:%s)|(?:%s)|(?:%s)|(?:%s)", 
				IP_PATTERN, MAC_PATTERN1, MAC_PATTERN2, MAC_PATTERN3, MAC_PATTERN4, MAC_PATTERN5, MAC_PATTERN6));

		this.pattern = Regex.compile(sPattern, settings.get("flags"));
		this.group = settings.getAsInt("group", 0);
		
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
    	Tokenizer source = new PatternTokenizer(pattern, group);
    	TokenStream lowered = new LowerCaseFilter(source);
        return new TokenStreamComponents(source, lowered);
    }
}
