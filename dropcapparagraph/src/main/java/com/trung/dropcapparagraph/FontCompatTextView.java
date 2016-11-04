package com.trung.dropcapparagraph;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * A {@link AppCompatTextView} with a custom font
 */
class FontCompatTextView extends AppCompatTextView {

    private String mFont;

    /**
     * Constructor.
     *
     * @param context holding context.
     */
    public FontCompatTextView(Context context) {
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
    public FontCompatTextView(Context context, AttributeSet attrs) {
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
    public FontCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontCompatTextView);
            mFont = a.getString(R.styleable.FontCompatTextView_fctv_typeface);
            a.recycle();

            if (!TextUtils.isEmpty(mFont)) {
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), mFont);
                setTypeface(typeface);
            }
        }
    }

    /**
     * Define the font
     *
     * @param font given font
     */
    public void setFont(String font) {
        this.mFont = font;

        if (!TextUtils.isEmpty(mFont)) {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), mFont);
            setTypeface(typeface);
        }
    }
}
