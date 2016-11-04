package com.trung.dropcapparagraph;

import android.content.Context;
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
        return pxToSp(textView.getContext(), textView.getPaint().getFontSpacing() * textView.getLineSpacingMultiplier()
                + textView.getLineSpacingExtra());
    }

    private static float pxToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }
}
