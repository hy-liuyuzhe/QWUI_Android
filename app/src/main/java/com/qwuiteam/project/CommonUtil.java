package com.qwuiteam.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.Utils;


/**
 * Description:
 * Author: zw
 * CreateDate: 2022/2/11 18:36
 */
public class CommonUtil {

    public static String checkValue(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        return content;
    }

    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(Utils.getApp(), id);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(Utils.getApp(), id);
    }

    public static String getStringRes(int id) {
        return Utils.getApp().getResources().getString(id);
    }

    public static String getStringRes(int id, Object... formatArgs) {
        return Utils.getApp().getResources().getString(id, formatArgs);
    }
    

    public static Drawable createDrawable(int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    public static Drawable createDrawable(int color, float tlRadius, float trRadius, float brRadius,
                                          float blRadius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        boolean isArabic = false;
        if (isArabic){
            drawable.setCornerRadii(new float[]{
                    trRadius, trRadius,
                    tlRadius, tlRadius,
                    blRadius, blRadius,
                    brRadius, brRadius,
            });
            drawable.setAutoMirrored(true);
        }else {
            drawable.setCornerRadii(new float[]{
                    tlRadius, tlRadius,
                    trRadius, trRadius,
                    brRadius, brRadius,
                    blRadius, blRadius});
            drawable.setAutoMirrored(true);
        }
        drawable.setAutoMirrored(true);
        return drawable;
    }

    public static Drawable createDrawable(@ColorInt int[] colors, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColors(colors);
        drawable.setCornerRadius(radius);
        drawable.setAutoMirrored(true);
        return drawable;
    }

    public static Drawable createDrawable(@ColorInt int[] colors, float tlRadius, float trRadius, float brRadius,
                                          float blRadius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColors(colors);
        boolean isArabic = false;
        if (isArabic){
            drawable.setCornerRadii(new float[]{
                    trRadius, trRadius,
                    tlRadius, tlRadius,
                    blRadius, blRadius,
                    brRadius, brRadius,
            });
            drawable.setAutoMirrored(true);
        }else {
            drawable.setCornerRadii(new float[]{
                    tlRadius, tlRadius,
                    trRadius, trRadius,
                    brRadius, brRadius,
                    blRadius, blRadius});
            drawable.setAutoMirrored(true);
        }
        return drawable;
    }


}
