package com.trung.dropcapparagraph;

import android.widget.TextView;

/**
 * Encapsulate some functions relative to {@link android.widget.TextView}
 */
public final class TextViewUtils {

    /**
     * Not instantiable class
     */
    private TextViewUtils() {
    }

    /**
     * @param textView given textview
     * @return line spacing
     */
    public static float getLineSpacing(final TextView textView) {
        return textView.getPaint().getFontSpacing() * textView.getLineSpacingMultiplier()
                + textView.getLineSpacingExtra();
    }
}
