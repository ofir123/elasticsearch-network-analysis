package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.CharsRefBuilder;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Modified version of {@link org.apache.lucene.analysis.pattern.PatternCaptureGroupTokenFilter} that also increments
 * the tokens' position attribute and sets token offsets.
 */
public final class IncrementalCaptureGroupTokenFilter extends TokenFilter {

    public static final String NAME = "incremental_capture_group";

    private final CharTermAttribute charTermAttr = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute posAttr = addAttribute(PositionIncrementAttribute.class);
    private final OffsetAttribute offsetAttr = addAttribute(OffsetAttribute.class);
    private final Matcher[] matchers;
    private final CharsRefBuilder spare = new CharsRefBuilder();
    private final int[] groupCounts;

    private State state;
    private int[] currentGroup;
    private int currentMatcher;

    /**
     * @param input    the input {@link TokenStream}
     * @param patterns an array of {@link Pattern} objects to match against each token
     */
    public IncrementalCaptureGroupTokenFilter(TokenStream input, Pattern... patterns) {
        super(input);

        this.matchers = new Matcher[patterns.length];
        this.groupCounts = new int[patterns.length];
        this.currentGroup = new int[patterns.length];

        for (int i = 0; i < patterns.length; i++) {
            this.matchers[i] = patterns[i].matcher("");
            this.groupCounts[i] = this.matchers[i].groupCount();
            this.currentGroup[i] = -1;
        }
    }

    @Override
    public boolean incrementToken() throws IOException {
        if (currentMatcher != -1 && nextCapture()) {
            assert state != null;

            clearAttributes();
            restoreState(state);

            final int start = matchers[currentMatcher].start(currentGroup[currentMatcher]);
            final int end = matchers[currentMatcher].end(currentGroup[currentMatcher]);

            // Each group it considered as 'following' the previous one, allowing 'phrase' matching.
            posAttr.setPositionIncrement(1);
            charTermAttr.copyBuffer(spare.chars(), start, end - start);
            updateOffset(start, end);
            currentGroup[currentMatcher]++;

            return true;
        }

        if (!input.incrementToken()) {
            return false;
        }

        char[] buffer = charTermAttr.buffer();
        int length = charTermAttr.length();
        spare.copyChars(buffer, 0, length);
        state = captureState();

        for (int i = 0; i < matchers.length; i++) {
            matchers[i].reset(spare.get());
            currentGroup[i] = -1;
        }

        if (nextCapture()) {
            final int start = matchers[currentMatcher].start(currentGroup[currentMatcher]);
            final int end = matchers[currentMatcher].end(currentGroup[currentMatcher]);

            if (start == 0) {
                charTermAttr.setLength(end);
            } else {
                charTermAttr.copyBuffer(spare.chars(), start, end - start);
            }

            // We want to separate the 'matches', meaning that same-regex groups are sequential, but different matches are not.
            posAttr.setPositionIncrement(2);
            updateOffset(start, end);
            currentGroup[currentMatcher]++;
        }

        return true;
    }

    private void updateOffset(int start, int end) {
        int base = offsetAttr.startOffset();
        offsetAttr.setOffset(base + start, base + end);
    }

    private boolean nextCapture() {
        int minOffset = Integer.MAX_VALUE;
        currentMatcher = -1;
        Matcher matcher;

        for (int i = 0; i < matchers.length; i++) {
            matcher = matchers[i];

            if (currentGroup[i] == -1) {
                currentGroup[i] = matcher.find() ? 1 : 0;
            }

            if (currentGroup[i] != 0) {
                while (currentGroup[i] < groupCounts[i] + 1) {
                    final int start = matcher.start(currentGroup[i]);
                    final int end = matcher.end(currentGroup[i]);

                    if (start == end) {
                        currentGroup[i]++;
                        continue;
                    }

                    if (start < minOffset) {
                        minOffset = start;
                        currentMatcher = i;
                    }

                    break;
                }

                if (currentGroup[i] == groupCounts[i] + 1) {
                    currentGroup[i] = -1;
                    i--;
                }
            }
        }

        return currentMatcher != -1;
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        state = null;
        currentMatcher = -1;
    }
}
