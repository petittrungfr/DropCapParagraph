package com.trung.dropcapparagraph;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Display a paragraph with a Drop Cap Character
 */
public class DropCapParagraph extends LinearLayout {
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
        LayoutInflater.from(getContext()).inflate(R.layout.layout_dropcap, this);

        if (attrs != null) {

        }
    }
}
