package com.shoppingbag.constants;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;


import com.shoppingbag.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReadMoreTextView extends AppCompatTextView {

    private static final int TRIM_MODE_LINES = 0;
    private static final int TRIM_MODE_LENGTH = 1;
    private static final int DEFAULT_TRIM_LENGTH = 240;
    private static final int DEFAULT_TRIM_LINES = 2;
    private static final int INVALID_END_INDEX = -1;
    private static final boolean DEFAULT_SHOW_TRIM_EXPANDED_TEXT = true;
    private static final String ELLIPSIZE = "... ";

//    private CharSequence text;
    private BufferType bufferType;
    private boolean readMore = true;
    private int trimLength;
    private CharSequence trimCollapsedText;
    private CharSequence trimExpandedText;
    private ReadMoreClickableSpan viewMoreSpan;
    private int colorClickableText;
    private boolean showTrimExpandedText;

    private int trimMode;
    private int lineEndIndex;
    private int trimLines;

    public ReadMoreTextView(Context context) {
        this(context, null);
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReadMoreTextView);
        this.trimLength = typedArray.getInt(R.styleable.ReadMoreTextView_trimLength, DEFAULT_TRIM_LENGTH);
        int resourceIdTrimCollapsedText =
                typedArray.getResourceId(R.styleable.ReadMoreTextView_trimCollapsedText, R.string.read_more);
        int resourceIdTrimExpandedText =
                typedArray.getResourceId(R.styleable.ReadMoreTextView_trimExpandedText, R.string.read_less);
        this.trimCollapsedText = getResources().getString(resourceIdTrimCollapsedText);
        this.trimExpandedText = getResources().getString(resourceIdTrimExpandedText);
        this.trimLines = typedArray.getInt(R.styleable.ReadMoreTextView_trimLines, DEFAULT_TRIM_LINES);
        this.colorClickableText = typedArray.getColor(R.styleable.ReadMoreTextView_colorClickableText,
                ContextCompat.getColor(context, R.color.colorAccent));
        this.showTrimExpandedText =
                typedArray.getBoolean(R.styleable.ReadMoreTextView_showTrimExpandedText, DEFAULT_SHOW_TRIM_EXPANDED_TEXT);
        this.trimMode = typedArray.getInt(R.styleable.ReadMoreTextView_trimMode, TRIM_MODE_LINES);
        typedArray.recycle();
        viewMoreSpan = new ReadMoreClickableSpan();
        onGlobalLayoutLineEndIndex();
        setText();
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(Color.TRANSPARENT);
    }

    private CharSequence getDisplayableText() {
        return getTrimmedText(justifiedText);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        this.justifiedText = text;
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (trimMode == TRIM_MODE_LENGTH) {
            if (text != null && text.length() > trimLength) {
                if (readMore) {
                    return updateCollapsedText();
                } else {
                    return updateExpandedText();
                }
            }
        }
        if (trimMode == TRIM_MODE_LINES) {
            if (text != null && lineEndIndex > 0) {
                if (readMore) {
                    if (getLayout().getLineCount() > trimLines) {
                        return updateCollapsedText();
                    }
                } else {
                    return updateExpandedText();
                }
            }
        }
        return text;
    }

    private CharSequence updateCollapsedText() {
        int trimEndIndex = justifiedText.length();
        switch (trimMode) {
            case TRIM_MODE_LINES:
                trimEndIndex = lineEndIndex - (ELLIPSIZE.length() + trimCollapsedText.length() + 1);
                if (trimEndIndex < 0) {
                    trimEndIndex = trimLength + 1;
                }
                break;
            case TRIM_MODE_LENGTH:
                trimEndIndex = trimLength + 1;
                break;
        }
        SpannableStringBuilder s = new SpannableStringBuilder(justifiedText, 0, trimEndIndex)
                .append(ELLIPSIZE)
                .append(trimCollapsedText);
        return addClickableSpan(s, trimCollapsedText);
    }

    private CharSequence updateExpandedText() {
        if (showTrimExpandedText) {
            SpannableStringBuilder s = new SpannableStringBuilder(justifiedText, 0, justifiedText.length()).append(trimExpandedText);
            return addClickableSpan(s, trimExpandedText);
        }
        return justifiedText;
    }

    private CharSequence addClickableSpan(SpannableStringBuilder s, CharSequence trimText) {
        s.setSpan(viewMoreSpan, s.length() - trimText.length(), s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        setText();
    }

    public void setColorClickableText(int colorClickableText) {
        this.colorClickableText = colorClickableText;
    }

    public void setTrimCollapsedText(CharSequence trimCollapsedText) {
        this.trimCollapsedText = trimCollapsedText;
    }

    public void setTrimExpandedText(CharSequence trimExpandedText) {
        this.trimExpandedText = trimExpandedText;
    }

    public void setTrimMode(int trimMode) {
        this.trimMode = trimMode;
    }

    public void setTrimLines(int trimLines) {
        this.trimLines = trimLines;
    }

    private class ReadMoreClickableSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            readMore = !readMore;
            setText();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(colorClickableText);
        }
    }

    private void onGlobalLayoutLineEndIndex() {
        if (trimMode == TRIM_MODE_LINES) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver obs = getViewTreeObserver();
                    obs.removeOnGlobalLayoutListener(this);
                    refreshLineEndIndex();
                    setText();
                }
            });
        }
    }

    private void refreshLineEndIndex() {
        try {
            if (trimLines == 0) {
                lineEndIndex = getLayout().getLineEnd(0);
            } else if (trimLines > 0 && getLineCount() >= trimLines) {
                lineEndIndex = getLayout().getLineEnd(trimLines - 1);
            } else {
                lineEndIndex = INVALID_END_INDEX;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Hair space character that will fill the space among spaces.
    private final static String HAIR_SPACE = "\u200A";

    //Normal space character that will take place between words.
    private final static String NORMAL_SPACE = " ";

    //TextView's width.
    private int viewWidth;

    //Justified sentences in TextView's text.
    private List<String> sentences = new ArrayList<>();

    //Sentence being justified.
    private List<String> currentSentence = new ArrayList<>();

    //Sentence filled with spaces.
    private List<String> sentenceWithSpaces = new ArrayList<>();

    //String that will storage the text with the inserted spaces.
    private CharSequence justifiedText = "";

    //Object that generates random numbers, this is part of the justification algorithm.
    private Random random = new Random();


//    @Override
//    protected void onDraw(Canvas canvas) {
        //This class won't repeat the process of justify text if it's already done.
//        if (!justifiedText.equals(getText().toString())) {
//            ViewGroup.LayoutParams params = getLayoutParams();
//            String text1 = getDisplayableText().toString();
//            viewWidth = getMeasuredWidth() - (getPaddingLeft() + getPaddingRight());
//            //This class won't justify the text if the TextView has wrap_content as width
//            //and won't justify the text if the view width is 0
//            //AND! won't justify the text if it's empty.
//            if (params.width != ViewGroup.LayoutParams.WRAP_CONTENT && viewWidth > 0 && !text1.isEmpty()) {
//                justifiedText = getJustifiedText(text1);
//
//                if (!justifiedText.toString().isEmpty()) {
//                    setText(justifiedText,bufferType);
//                    sentences.clear();
//                    currentSentence.clear();
//                }
//            } else {
//                Log.e("TEXT","else draw");
//                super.onDraw(canvas);
//            }
//        } else {
//            Log.e("TEXT12","else draw");
//            super.onDraw(canvas);
//        }
//    }

    /**
     * Retrieves a String with appropriate spaces to justify the text in the TextView.
     *
     * @param text Text to be justified
     * @return Justified text
     */
    private String getJustifiedText(String text) {
        String[] words = text.split(NORMAL_SPACE);

        for (String word : words) {
            boolean containsNewLine = (word.contains("\n") || word.contains("\r"));

            if (fitsInSentence(word, currentSentence, true)) {
                addWord(word, containsNewLine);
            } else {
                sentences.add(fillSentenceWithSpaces(currentSentence));
                currentSentence.clear();
                addWord(word, containsNewLine);
            }
        }

        //Making sure we add the last sentence if needed.
        if (currentSentence.size() > 0) {
            sentences.add(getSentenceFromList(currentSentence, true));
        }

        //Returns the justified text.
        return getSentenceFromList(sentences, false);
    }

    /**
     * Adds a word into sentence and starts a new one if "new line" is part of the string.
     *
     * @param word            Word to be added
     * @param containsNewLine Specifies if the string contains a new line
     */
    private void addWord(String word, boolean containsNewLine) {
        currentSentence.add(word);
        if (containsNewLine) {
            sentences.add(getSentenceFromListCheckingNewLines(currentSentence));
            currentSentence.clear();
        }
    }

    /**
     * Creates a string using the words in the list and adds spaces between words if required.
     *
     * @param strings   Strings to be merged into one
     * @param addSpaces Specifies if the method should add spaces between words.
     * @return Returns a sentence using the words in the list.
     */
    private String getSentenceFromList(List<String> strings, boolean addSpaces) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            stringBuilder.append(string);

            if (addSpaces) {
                stringBuilder.append(NORMAL_SPACE);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Creates a string using the words in the list and adds spaces between words taking new lines
     * in consideration.
     *
     * @param strings Strings to be merged into one
     * @return Returns a sentence using the words in the list.
     */
    private String getSentenceFromListCheckingNewLines(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            stringBuilder.append(string);

            //We don't want to add a space next to the word if this one contains a new line character
            if (!string.contains("\n") && !string.contains("\r")) {
                stringBuilder.append(NORMAL_SPACE);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Fills sentence with appropriate amount of spaces.
     *
     * @param sentence Sentence we'll use to build the sentence with additional spaces
     * @return String with spaces.
     */
    private String fillSentenceWithSpaces(List<String> sentence) {
        sentenceWithSpaces.clear();

        //We don't need to do this process if the sentence received is a single word.
        if (sentence.size() > 1) {
            //We fill with normal spaces first, we can do this with confidence because "fitsInSentence"
            //already takes these spaces into account.
            for (String word : sentence) {
                sentenceWithSpaces.add(word);
                sentenceWithSpaces.add(NORMAL_SPACE);
            }

            //Filling sentence with thin spaces.
            while (fitsInSentence(HAIR_SPACE, sentenceWithSpaces, false)) {
                //We remove 2 from the sentence size because we need to make sure we are not adding
                //spaces to the end of the line.
                sentenceWithSpaces.add(getRandomNumber(sentenceWithSpaces.size() - 2), HAIR_SPACE);
            }
        }

        return getSentenceFromList(sentenceWithSpaces, false);
    }

    /**
     * Verifies if word to be added will fit into the sentence
     *
     * @param word      Word to be added
     * @param sentence  Sentence that will receive the new word
     * @param addSpaces Specifies weather we should add spaces to validation or not
     * @return True if word fits, false otherwise.
     */
    private boolean fitsInSentence(String word, List<String> sentence, boolean addSpaces) {
        String stringSentence = getSentenceFromList(sentence, addSpaces);
        stringSentence += word;

        float sentenceWidth = getPaint().measureText(stringSentence);

        return sentenceWidth < viewWidth;
    }

    /**
     * Returns a random number, it's part of the algorithm... don't blame me.
     *
     * @param max Max number in range
     * @return Random number.
     */
    private int getRandomNumber(int max) {
        //We add 1 to the result because we wanna prevent the logic from adding
        //spaces at the beginning of the sentence.
        return random.nextInt(max) + 1;
    }
}

