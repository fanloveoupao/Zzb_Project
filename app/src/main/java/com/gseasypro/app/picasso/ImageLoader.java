package com.gseasypro.app.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.example.lang.StringUtil;
import com.example.resources.SessionTypes;
import com.gseasypro.app.R;
import com.gseasypro.app.okhttp.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

/**
 * Created by fan-gk on 2017/2/9.
 */

public class ImageLoader {
    private static Picasso iconPicasso = null;
    private static Picasso imagePicasso = null;
    private static CircleTransformation circleTransformation = null;
    private static RoundedBitmapDrawable defaultRoundIcon = null;

    public static void init(@NonNull Context context) {
        iconPicasso = new Picasso
                .Builder(context)
                .downloader(new OkHttp3Downloader(context, "gsnet-cahce-icon"))
                .build();
        imagePicasso = new Picasso
                .Builder(context)
                .downloader(new OkHttp3Downloader(context, "gsnet-cache-image", 500 * 1024 * 1024)) //500M
                .build();
        circleTransformation = new CircleTransformation(context);
        defaultRoundIcon = RoundedBitmapDrawableFactory.create(context.getResources()
                , BitmapFactory.decodeResource(context.getResources(), R.drawable.default_portrait));
        defaultRoundIcon.setCircular(true);
    }

    public static void reloadIcon(@NonNull String url) {
        invalidate(url);
        iconPicasso
                .load(url)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    public static void invalidate(@NonNull String url) {
        iconPicasso.invalidate(url);
    }

    public static boolean loadSystemIcon(@NonNull String sessionType, @NonNull ImageView imageView) {
        int iconResId = getResIcon(sessionType);
        if (iconResId == -1)
            return false;
        else {
            imageView.setImageResource(iconResId);
            return true;
        }
    }

    private static int getResIcon(String sessionType) {
        if (SessionTypes.FRIEND_APPLY.equals(sessionType)) {
            return R.mipmap.icon_new_friends;
        } else if (SessionTypes.ADMIN_MESSAGE.equals(sessionType)) {
            return R.mipmap.icon_tg_assistant;
        } else {
            return -1;
        }
    }

    public static void loadIcon(@NonNull Bitmap image,@NonNull ImageView imageView,boolean isRound){
        if(isRound){
            RoundedBitmapDrawable roundIcon = RoundedBitmapDrawableFactory.create(imageView.getContext().getResources()
                    , image);
            roundIcon.setCircular(true);
            imageView.setImageDrawable(roundIcon);
        }else{
            imageView.setImageBitmap(image);
        }
    }

    public static void loadIcon(@NonNull String url, @NonNull ImageView imageView, boolean useCache) {
        loadIcon(Uri.parse(url), imageView, useCache, false);
    }

    public static void loadIcon(@NonNull String url, @NonNull ImageView imageView, boolean useCache, boolean isRound) {
        loadIcon(Uri.parse(url), imageView, useCache, isRound);
    }

    public static void loadIcon(@NonNull Uri url, @NonNull final ImageView imageView, boolean useCache, boolean isRound) {
        RequestCreator creator = iconPicasso
                .load(url)
                .fit();

        if (!useCache) {
            creator = creator.networkPolicy(NetworkPolicy.NO_CACHE);
            creator = creator.memoryPolicy(MemoryPolicy.NO_CACHE);
        }

        if (isRound) {
            creator.placeholder(defaultRoundIcon)
                    .error(defaultRoundIcon)
                    .transform(circleTransformation);
        }else{
            creator.placeholder(R.drawable.default_portrait)
                    .error(R.drawable.default_portrait);
        }
        creator.into(imageView);
    }

    public static void loadImage(@NonNull Uri url, @NonNull final ImageView imageView) {
        loadImage(url, null, imageView, null, null);
    }

    public static void loadImage(@NonNull Uri url, @NonNull final ImageView imageView, final Runnable onLoaded) {
        loadImage(url, null, imageView, onLoaded, null);
    }

    public static void loadImage(@NonNull Uri url, @Nullable String thumbnailUrl, @NonNull final ImageView imageView) {
        loadImage(url, thumbnailUrl, imageView, null, null);
    }

    public static void loadImage(@NonNull Uri url, @Nullable String thumbnailUrl, @NonNull final ImageView imageView, final Runnable onLoaded) {
        loadImage(url, thumbnailUrl, imageView, onLoaded, null);
    }


    /**
     * @param url
     * @param thumbnailUrl 缩略图地址，只从缓存加载如果缓存没有，显示默认加载中的图片。设置为null时显示默认加载图
     * @param imageView
     * @param onLoaded
     * @param onError
     */
    public static void loadImage(@NonNull Uri url, String thumbnailUrl, @NonNull final ImageView imageView, final Runnable onLoaded, final Runnable onError) {
        RequestCreator loader;
        if (!StringUtil.isNullOrWhiteSpace(thumbnailUrl)) {
            ImageView placeholder = new ImageView(imageView.getContext());
            imagePicasso
                    .load(thumbnailUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.mipmap.icon_pic_loding)
                    .error(R.mipmap.icon_pic_loding)
                    .into(placeholder);
            loader = createrLoader(url, placeholder.getDrawable());
        } else {
            loader = createrLoader(url, R.mipmap.icon_pic_loding);
        }

        loader
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (onLoaded != null)
                            onLoaded.run();
                    }

                    @Override
                    public void onError() {
                        if (onError != null)
                            onError.run();
                    }
                });
    }

    private static RequestCreator createrLoader(@NonNull Uri url, Drawable placeholder) {
        return imagePicasso
                .load(url)
                .placeholder(placeholder)
                .error(R.mipmap.icon_pic_errow);
    }

    private static RequestCreator createrLoader(@NonNull Uri url, @DrawableRes int placeholder) {
        return imagePicasso
                .load(url)
                .placeholder(placeholder)
                .error(R.mipmap.icon_pic_errow);
    }

    public static Bitmap getBitmap(@NonNull String url) {
        final Bitmap[] mBitmap = new Bitmap[1];
        imagePicasso.load(Uri.parse(url)).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mBitmap[0] = bitmap;
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        return mBitmap[0];
    }


}

