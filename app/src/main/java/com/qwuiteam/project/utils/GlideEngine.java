package com.qwuiteam.project.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import org.jetbrains.annotations.NotNull;

/**
 * @author：luck
 * @date：2019-11-13 17:02
 * @describe：Glide加载引擎
 */
public class GlideEngine implements ImageEngine {

    /**
     * 加载图片
     *
     * @param context   上下文
     * @param url       资源url
     * @param imageView 图片承载控件
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull @NotNull Context context, @NonNull @NotNull String url, @NonNull @NotNull ImageView imageView, SubsamplingScaleImageView longImageView, OnImageCompleteCallback callback) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadImage(@NonNull @NotNull Context context, @NonNull @NotNull String url, @NonNull @NotNull ImageView imageView, SubsamplingScaleImageView longImageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadFolderImage(@NonNull @NotNull Context context, @NonNull @NotNull String url, @NonNull @NotNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadAsGifImage(@NonNull @NotNull Context context, @NonNull @NotNull String url, @NonNull @NotNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        Glide.with(context)
                .load(url)
                .override(200, 200)
                .centerCrop()
//                .placeholder(R.drawable.ps_image_placeholder)
                .into(imageView);
    }

    private GlideEngine() {
    }

    private static GlideEngine instance;

    public static GlideEngine createGlideEngine() {
        if (null == instance) {
            synchronized (GlideEngine.class) {
                if (null == instance) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }
}
