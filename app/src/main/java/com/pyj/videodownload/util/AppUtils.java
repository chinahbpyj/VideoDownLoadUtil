package com.pyj.videodownload.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;

public class AppUtils {
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }

    public static void showSnackBar(View root, String msg, int bg) {
        Snackbar snackbar = Snackbar.make(root, msg, Snackbar.LENGTH_SHORT);
        setSnackbarColor(snackbar, bg);
        snackbar.show();
    }

    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();//获取Snackbar的view
        if (view != null) {
            view.setBackgroundColor(backgroundColor);//修改view的背景色
            //((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);//获取Snackbar的message控件，修改字体颜色
        }
    }
}
