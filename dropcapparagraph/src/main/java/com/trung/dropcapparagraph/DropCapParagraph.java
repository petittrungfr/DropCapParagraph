package com.trung.dropcapparagraph;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Display a paragraph with a Drop Cap Character
 */
public class DropCapParagraph extends LinearLayout {

    private static final int DEFAULT_DROPCAP_LINE = 2;
    private static final float DEFAULT_PARAGRAPH_TEXT_SIZE = 14;
    private static final int DEFAULT_MAX_LINES = 8;

    /**
     * First Big Character
     */
    private TextView mDropCapCharacter;

    /**
     * Right Paragraph
     */
    private TextView mRightParagraph;

    /**
     * Bottom Paragraph
     */
    private TextView mBottomParagraph;

    /**
     * Read More
     */
    private TextView mReadMore;

    /**
     * Text
     */
    private String mText;

    /**
     * Number of DropCap Lines
     */
    private int mDropCapLines;

    /**
     * First Character Style
     */
    private int mDropCapStyle;

    /**
     * Paragraph Style
     */
    private int mParagraphStyle;

    /**
     * Paragraph Text Size
     */
    private float mParagraphTextSize;

    /**
     * If true, display "read more"
     */
    private boolean mIsExpandable = false;

    /**
     * Text
     */
    private String mCollapsingMoreText;
    private String mExpandingMoreText;

    /**
     * Read More Style
     */
    private int mReadMoreStyle;

    /**
     * Expand or Collapse
     */
    private boolean mIsExpanded = false;

    /**
     * Metrics
     */
    private DisplayMetrics mMetrics;

    /**
     * Constructor.
     *
     * @param context holding context.
     */
    public DropCapParagraph(Context context) {
        super(context);
        if (!isInEditMode()) {
            init(null);
        }
    }

    /**
     * Constructor.
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public DropCapParagraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    /**
     * Constructor.
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style from xml.
     */
    public DropCapParagraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    private void init(AttributeSet attrs) {
        mMetrics = getResources().getDisplayMetrics();

        LayoutInflater.from(getContext()).inflate(R.layout.layout_dropcap_paragraph, this);

        /**
         * Init Views
         */
        mDropCapCharacter = (TextView) findViewById(R.id.first_character);
        mRightParagraph = (TextView) findViewById(R.id.right_paragraph);
        mBottomParagraph = (TextView) findViewById(R.id.bottom_paragraph);
        mReadMore = (TextView) findViewById(R.id.read_more);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DropCapParagraph);

            /**
             * Text
             */
            mText = a.getString(R.styleable.DropCapParagraph_dcp_text);
            mCollapsingMoreText = a.getString(R.styleable.DropCapParagraph_dcp_collapsing_more_text);
            if (TextUtils.isEmpty(mCollapsingMoreText)) {
                mCollapsingMoreText = getResources().getString(R.string.collapse);
            }
            mExpandingMoreText = a.getString(R.styleable.DropCapParagraph_dcp_expanding_more_text);
            if (TextUtils.isEmpty(mCollapsingMoreText)) {
                mExpandingMoreText = getResources().getString(R.string.expand);
            }

            /**
             * Drop Cap Lines
             */
            mDropCapLines = a.getInt(R.styleable.DropCapParagraph_dcp_lines, DEFAULT_DROPCAP_LINE);
            mRightParagraph.setMaxLines(mDropCapLines);

            /**
             * Styles
             */
            mDropCapStyle = a.getInt(R.styleable.DropCapParagraph_dcp_first_character_style, R.style.FirstCharacter);
            mParagraphStyle = a.getInt(R.styleable.DropCapParagraph_dcp_paragraph_style, R.style.Paragraph);
            mReadMoreStyle = a.getInt(R.styleable.DropCapParagraph_dcp_more_style, R.style.ReadMore);

            setInternalTextAppearance();

            /**
             * Size
             */
            mParagraphTextSize = a.getDimension(R.styleable.DropCapParagraph_dcp_paragraph_text_size,
                    DEFAULT_PARAGRAPH_TEXT_SIZE);

            setParagraphTextSize(mParagraphTextSize);

            /**
             * Expandable or not
             */
            mIsExpandable = a.getBoolean(R.styleable.DropCapParagraph_dcp_is_expandable, true);


            a.recycle();
        }

        if (!TextUtils.isEmpty(mText)) {
            setText(mText);
        }

        if (mIsExpandable) {
            mBottomParagraph.setMaxLines(DEFAULT_MAX_LINES);
            collapse(DEFAULT_MAX_LINES);
            mReadMore.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBottomParagraph.getLineCount() <= DEFAULT_MAX_LINES) {
                        return;
                    }
                    if (mIsExpanded) {
                        collapse(DEFAULT_MAX_LINES);
                    } else {
                        expand();
                    }
                }
            });
        }
    }

    @SuppressWarnings("deprecation")
    private void setInternalTextAppearance() {
        if (getContext() != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                mDropCapCharacter.setTextAppearance(getContext(), mDropCapStyle);
                mRightParagraph.setTextAppearance(getContext(), mParagraphStyle);
                mBottomParagraph.setTextAppearance(getContext(), mParagraphStyle);
                mReadMore.setTextAppearance(getContext(), mReadMoreStyle);
            } else {
                mDropCapCharacter.setTextAppearance(mDropCapStyle);
                mRightParagraph.setTextAppearance(mParagraphStyle);
                mBottomParagraph.setTextAppearance(mParagraphStyle);
                mReadMore.setTextAppearance(mReadMoreStyle);
            }
        }
    }

    private void expand() {
        mIsExpanded = true;
        ObjectAnimator animation = ObjectAnimator.ofInt(mBottomParagraph, "maxLines", mBottomParagraph.getLineCount());
        animation.setDuration(200).start();
        if (!TextUtils.isEmpty(mCollapsingMoreText)) {
            mReadMore.setText(mCollapsingMoreText);
        }
    }

    private void collapse(int numLines) {
        mIsExpanded = false;
        ObjectAnimator animation = ObjectAnimator.ofInt(mBottomParagraph, "maxLines", numLines);
        animation.setDuration(200).start();
        if (!TextUtils.isEmpty(mExpandingMoreText)) {
            mReadMore.setText(mExpandingMoreText);
        }
    }

    /**
     * Set Text
     *
     * @param text given Text
     */
    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            String remainingText = text.substring(1, text.length());

            mDropCapCharacter.setText(text.substring(0, 1).toUpperCase());
            mRightParagraph.setText(remainingText);
        }
    }

    /**
     * Set Text Size
     *
     * @param paragraphTextSize given text size in sp
     */
    public void setParagraphTextSize(float paragraphTextSize) {
        this.mParagraphTextSize = paragraphTextSize;

        mRightParagraph.setTextSize(TypedValue.COMPLEX_UNIT_SP, mParagraphTextSize);
        mBottomParagraph.setTextSize(TypedValue.COMPLEX_UNIT_SP, mParagraphTextSize);

        mDropCapCharacter.setTextSize(TypedValue.COMPLEX_UNIT_SP, mParagraphTextSize * mDropCapLines
                + TextViewUtils.getLineSpacing(mRightParagraph) * (mDropCapLines - 1));

        mReadMore.setTextSize(TypedValue.COMPLEX_UNIT_SP, mParagraphTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRightParagraph.getLineCount() > mDropCapLines) {
            String text = mRightParagraph.getText().toString();

            int secondLineEnd = mRightParagraph.getLayout().getLineEnd(1);
//            String twoLines = text.substring(0, secondLineEnd).trim();
            String remainder = text.substring(secondLineEnd, text.length());

//            mRightParagraph.setText(twoLines);
            mBottomParagraph.setText(remainder);
            mBottomParagraph.post(new Runnable() {
                @Override
                public void run() {
                    if (mBottomParagraph.getLineCount() > DEFAULT_MAX_LINES) {
                        mReadMore.setVisibility(VISIBLE);
                    } else {
                        mReadMore.setVisibility(GONE);
                    }
                }
            });
        }
    }
}
