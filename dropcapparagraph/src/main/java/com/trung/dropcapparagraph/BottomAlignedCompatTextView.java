package com.trung.dropcapparagraph;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * A {@link android.widget.TextView} which is drawn aligned by it's bottom
 */
class BottomAlignedCompatTextView extends AppCompatTextView {

    private boolean mIsUppercase;

    /**
     * Constructor.
     *
     * @param context holding context.
     */
    public BottomAlignedCompatTextView(Context context) {
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
    public BottomAlignedCompatTextView(Context context, AttributeSet attrs) {
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
    public BottomAlignedCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BottomAlignedCompatTextView);
            mIsUppercase = a.getBoolean(R.styleable.BottomAlignedCompatTextView_bacptv_is_uppercase, true);
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            if (mIsUppercase) {
                text = text.toString().toUpperCase();
            } else {
                text = text.toString().toLowerCase();
            }
        }
        super.setText(text, type);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsUppercase) {
            if (getPaint() != null) {
                canvas.translate(0, getPaint().descent());
            }
            else {
                canvas.translate(0, getLineHeight() - getTextSize());
            }
        }
        super.onDraw(canvas);
    }

    /**
     * @return true if this text is uppercase
     */
    public boolean isIsUppercase() {
        return mIsUppercase;
    }

    /**
     * @param mIsUppercase is uppercase ?
     */
    public void setIsUppercase(boolean mIsUppercase) {
        this.mIsUppercase = mIsUppercase;
    }
}
