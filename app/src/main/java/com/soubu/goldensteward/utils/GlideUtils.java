package com.soubu.goldensteward.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.soubu.goldensteward.base.mvp.presenter.ActivityPresenter;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by iMonkey on 2016/5/24.
 */
public class GlideUtils {
//    public static void loadImage2(Context context, final ImageView targetView, Uri uri, int placeholder, int errorRes) {
//        RequestManager manager = Glide.with(context);
//        manager.load(uri).placeholder(placeholder).error(errorRes).into(new ViewTarget<ImageView, GlideDrawable>(targetView) {
//            @Override
//            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                targetView.setImageDrawable(resource);
//            }
//
//        });
//    }

    public static Bitmap getRoundCornerImage(Bitmap bitmap_bg, Bitmap bitmap_in, int width, int height) {
        Bitmap roundConcerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);
        Rect rectF = new Rect(0, 0, bitmap_in.getWidth(), bitmap_in.getHeight());
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap_in, rectF, rect, paint);
        return roundConcerImage;
    }

    public static Bitmap getShardImage(Bitmap bitmap_bg, Bitmap bitmap_in) {
        Bitmap roundConcerImage = Bitmap.createBitmap(bitmap_in.getWidth(), bitmap_in.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap_in.getWidth(), bitmap_in.getHeight());
        paint.setAntiAlias(true);
        NinePatch patch = new NinePatch(bitmap_bg, bitmap_bg.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
        Rect rect2 = new Rect(2, 2, bitmap_in.getWidth() - 2, bitmap_in.getHeight() - 2);
        canvas.drawBitmap(bitmap_in, rect, rect2, paint);
        return roundConcerImage;
    }

    public static void loadShardImage(final Context context, final ImageView targetView, Uri uri, final int bgId, int placeholder, final int errorRes) {
        if (context != null) {
            if (context instanceof ActivityPresenter) {
                if (((ActivityPresenter) context).isFinishing()) {
                    return;
                }
            }
            RequestManager manager = Glide.with(context);
            manager.load(uri).centerCrop().placeholder(placeholder).error(errorRes).crossFade().listener(new RequestListener<Uri, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                    targetView.setImageResource(errorRes);
                    return false;
                }


                @Override
                public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    Bitmap bitmap_bg = BitmapFactory.decodeResource(context.getResources(), bgId);

                    int w = resource.getIntrinsicWidth();
                    int h = resource.getIntrinsicHeight();
                    Bitmap.Config config = resource.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
                    Bitmap bitmap_in = Bitmap.createBitmap(w, h, config);
                    Canvas canvas = new Canvas(bitmap_in);
                    resource.setBounds(0, 0, w, h);
                    resource.draw(canvas);

                    if (targetView.getWidth() == 0 || targetView.getHeight() == 0) {
                        targetView.setImageDrawable(resource);
                        return true;
                    }

                    final Bitmap bp = getRoundCornerImage(bitmap_bg, bitmap_in, targetView.getWidth(), targetView.getHeight());
                    final Bitmap bp2 = getShardImage(bitmap_bg, bp);
                    targetView.setImageBitmap(bp2);
                    //targetView.setImageDrawable(resource);
                    return true;
                }
            }).into(targetView);
        }
    }

    public static void loadImage(Context context, final ImageView targetView, Object object, int placeholder, final int errorRes) {
        Uri uri = null;
        String url = null;
        if (object instanceof Uri) {
            uri = (Uri) object;
        }
        if (object instanceof String) {
            url = (String) object;
        }
        if (context != null) {
            if (context instanceof ActivityPresenter) {
                if (((ActivityPresenter) context).isFinishing()) {
                    return;
                }
            }
            RequestManager manager = Glide.with(context);
            manager.load(uri == null ? url : uri).placeholder(placeholder).error(errorRes).crossFade().
                    listener(new RequestListener<Comparable<? extends Comparable<?>>, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Comparable<? extends Comparable<?>> model, Target<GlideDrawable> target, boolean isFirstResource) {
                            targetView.setImageResource(errorRes);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Comparable<? extends Comparable<?>> model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            targetView.setImageDrawable(resource);
                            return false;
                        }
                    }).into(targetView);
        }
    }


    public static void loadBigImage(Context context, final ImageView targetView, Object object, int placeholder, final int errorRes) {
        Uri uri = null;
        String url = null;
        if (object instanceof Uri) {
            uri = (Uri) object;
        }
        if (object instanceof String) {
            url = (String) object;
        }
        if (context != null) {
            if (context instanceof ActivityPresenter) {
                if (((ActivityPresenter) context).isFinishing()) {
                    return;
                }
            }
            RequestManager manager = Glide.with(context);
            manager.load(uri == null ? url : uri).fitCenter().placeholder(placeholder).error(errorRes).crossFade().
                    listener(new RequestListener<Comparable<? extends Comparable<?>>, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Comparable<? extends Comparable<?>> model, Target<GlideDrawable> target, boolean isFirstResource) {
                            targetView.setImageResource(errorRes);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Comparable<? extends Comparable<?>> model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            targetView.setImageDrawable(resource);
                            return false;
                        }
                    }).into(targetView);
        }
    }


    public static void loadBigImagePage(Context context, final ImageView targetView, Uri uri, int placeholder, final int errorRes, final ProgressBar pb) {
        if (context != null) {
            if (context instanceof ActivityPresenter) {
                if (((ActivityPresenter) context).isFinishing()) {
                    return;
                }
            }
            RequestManager manager = Glide.with(context);
            manager.load(uri).fitCenter().placeholder(placeholder).error(errorRes).crossFade().listener(new RequestListener<Uri, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                    targetView.setImageResource(errorRes);
                    pb.setVisibility(View.INVISIBLE);
                    return false;
                }


                @Override
                public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    targetView.setImageDrawable(resource);
                    pb.setVisibility(View.INVISIBLE);
                    return true;
                }
            }).into(targetView);
        }
    }


    public static void loadCircleImage(Context context, final ImageView targetView, Uri uri, int placeholder, final int errorRes) {
        if (context != null) {
            if (context instanceof ActivityPresenter) {
                if (((ActivityPresenter) context).isFinishing()) {
                    return;
                }
            }
            RequestManager manager = Glide.with(context);
            RoundedBitmapDrawable placeHolder = RoundedBitmapDrawableFactory.create(context.getResources(), BitmapFactory.decodeResource(context.getResources(), placeholder));
            placeHolder.setCircular(true);
            RoundedBitmapDrawable error = RoundedBitmapDrawableFactory.create(context.getResources(), BitmapFactory.decodeResource(context.getResources(), errorRes));
            error.setCircular(true);
            manager.load(uri).placeholder(placeHolder).error(error).bitmapTransform(new CropCircleTransformation(context), new CenterCrop(context)).crossFade().listener(new RequestListener<Uri, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                    targetView.setImageResource(errorRes);
                    return false;
                }


                @Override
                public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    targetView.setImageDrawable(resource);
                    return true;
                }
            }).into(targetView);
        }
    }

    public static void loadTopRoundedImage(Context context, final ImageView targetView, Uri uri, int placeholder, final int errorRes) {
        if (context != null) {
            if (context instanceof ActivityPresenter) {
                if (((ActivityPresenter) context).isFinishing()) {
                    return;
                }
            }
            RequestManager manager = Glide.with(context);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
            RoundedCornersTransformation rtf = new RoundedCornersTransformation(context, px, 0,
                    RoundedCornersTransformation.CornerType.TOP);
            manager.load(uri).placeholder(placeholder).error(errorRes).crossFade().bitmapTransform(new CenterCrop(context), rtf).
                    listener(new RequestListener<Uri, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                            targetView.setImageResource(errorRes);

                            return false;
                        }


                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            targetView.setImageDrawable(resource);

                            return true;
                        }
                    }).into(targetView);
        }
    }

    public static void loadRoundedImage(Context context, final ImageView targetView, Object object, int placeholder, final int errorRes) {
        Uri uri = null;
        String url = null;
        if (object instanceof Uri) {
            uri = (Uri) object;
        }
        if (object instanceof String) {
            url = (String) object;
        }
        if (context != null) {
            if (context instanceof ActivityPresenter) {
                if (((ActivityPresenter) context).isFinishing()) {
                    return;
                }
            }
            RequestManager manager = Glide.with(context);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
            RoundedCornersTransformation rtf = new RoundedCornersTransformation(context, px, 0,
                    RoundedCornersTransformation.CornerType.ALL);
            manager.load(uri == null ? url : uri).placeholder(placeholder).error(errorRes).crossFade().bitmapTransform(new CenterCrop(context), rtf).
                    listener(new RequestListener<Comparable<? extends Comparable<?>>, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Comparable<? extends Comparable<?>> model, Target<GlideDrawable> target, boolean isFirstResource) {
                            targetView.setImageResource(errorRes);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Comparable<? extends Comparable<?>> model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            targetView.setImageDrawable(resource);
                            return false;
                        }
                    }).into(targetView);
        }
    }


    public static void loadBlurImage(Context context, final ImageView targetView, Uri uri, int placeholder, final int errorRes) {
        if (context != null) {
            if (context instanceof ActivityPresenter) {
                if (((ActivityPresenter) context).isFinishing()) {
                    return;
                }
            }
            RequestManager manager = Glide.with(context);
            manager.load(uri).placeholder(placeholder).error(errorRes).bitmapTransform(new BlurTransformation(context)).crossFade().listener(new RequestListener<Uri, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                    targetView.setImageResource(errorRes);
                    return false;
                }


                @Override
                public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    targetView.setImageDrawable(resource);
                    return true;
                }
            }).into(targetView);
        }
    }

}
