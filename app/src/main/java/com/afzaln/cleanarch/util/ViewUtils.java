package com.afzaln.cleanarch.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

/**
 * Created by afzal on 2015-12-07.
 */
public class ViewUtils {
    public static void setPaddingInDp(Context context, View view, int left, int top, int right, int bottom) {
        float density = context.getResources().getDisplayMetrics().density;
        view.setPadding((int) (left * density), (int) (top * density), (int) (right * density), (int) (bottom * density));
    }

    public static void setMarginInDp(Context context, View view, int left, int top, int right, int bottom) {
        float density = context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.setMargins((int) (left * density), (int) (top * density), (int) (right * density), (int) (bottom * density));
        view.setLayoutParams(lp);
    }
}

