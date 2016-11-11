package com.soubu.goldensteward.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.soubu.goldensteward.module.AppConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BitmapUtils {

    static {
        System.loadLibrary("jpegbither");
        System.loadLibrary("bitherjni");
    }

    private static native String compressImage(Bitmap bit, int w, int h, int quality, byte[] fileNameBytes, boolean optimize);

    public static Bitmap toRoundCorner(Bitmap bitmap, float pixels) {
        // System.out.println("图片是否变成圆角模式了+++++++++++++");
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // System.out.println("pixels+++++++"+pixels);

        return output;
    }

    /**
     * 将图片截取为圆角图片
     *
     * @param bitmap 原图片
     * @param ratio  截取比例，如果是8，则圆角半径是宽高的1/8，如果是2，则是圆形图片
     * @return 圆角矩形图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float ratio) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio, bitmap.getHeight() / ratio, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    // 将view转为bitmap
    public static Bitmap getBitmapFromView(View view) {
        // Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        // Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        // Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            // has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            // does not have background drawable, then draw white background on
            // the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        // return the bitmap
        return returnedBitmap;
    }

    // 将view转为bitmap
    public static Bitmap viewToBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bm = view.getDrawingCache();
        return bm;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128
                : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    // 按大小缩放
    public static File getimage(String srcPath) {
        try {
            Log.e("imageTest", "start path=" + srcPath);
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

            newOpts.inPurgeable = true;// 允许可清除
            newOpts.inInputShareable = true;

            newOpts.inJustDecodeBounds = false;
            /*
             * int w = newOpts.outWidth; int h = newOpts.outHeight; //
			 * 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可 if (h > w) { float tmp = hh; hh
			 * = ww; ww = tmp; } int be = 1;// be=1表示不缩放 if (w > h && w > ww)
			 * {// 如果宽度大的话根据宽度固定大小缩放 be = (int) (newOpts.outWidth / ww); } else
			 * if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放 be = (int)
			 * (newOpts.outHeight / hh); } if (be <= 0) be = 1;
			 */
            float w = newOpts.outWidth;
            float h = newOpts.outHeight;
            float standscale = AppConfig.IMAGE_MAX_WIDTH / AppConfig.IMAGE_MAX_HEIGHT;
            // float scale = (float)w/(float)h;
            float scale;
            boolean isNeedScale = false;
            if (w >= h) {
                scale = h / w;
            } else {
                scale = w / h;
            }
            float stand;
            if (standscale >= scale) {
                stand = AppConfig.IMAGE_MAX_HEIGHT;
            } else {
                stand = AppConfig.IMAGE_MAX_WIDTH;
            }
            if (w >= h) {
                if (stand == AppConfig.IMAGE_MAX_WIDTH) {
                    scale = h / stand;
                    h = stand;
                    w = w / scale;
                } else {
                    scale = w / stand;
                    w = stand;
                    h = h / scale;
                }
            } else {
                if (stand == AppConfig.IMAGE_MAX_WIDTH) {
                    scale = w / stand;
                    w = stand;
                    h = h / scale;
                } else {
                    scale = h / stand;
                    h = stand;
                    w = w / scale;
                }
            }
            if (scale > 1)
                isNeedScale = true;
            Log.e("imageTest", "scale=" + scale);
            newOpts.inSampleSize = computeSampleSize(newOpts, -1,
                    (int) (AppConfig.IMAGE_MAX_WIDTH * AppConfig.IMAGE_MAX_HEIGHT));// be;//
            // 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了s
            // bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

            bitmap = BitmapFactory.decodeFile(srcPath, newOpts); // decodeFileDescriptor比decodeFile占内存小
            Log.e("srcPath", srcPath);

            if (isNeedScale) {
                String time = String.valueOf(System.currentTimeMillis());
                //srcPath = AppConfig.CACHE_DIR + File.separator + "sobu_cache_img_" + time + ".jpg";
                srcPath = Environment.getExternalStorageDirectory() + "/DCIM/SoubuShopManager/Compress/" + time + ".jpg";
                bitmap = scaleBitmap(bitmap, w, h);
                Log.e("ffdd", bitmap == null ? "bitmap null" : "bitmap not null ");
            }
            Log.e("dddd", bitmap == null ? "bitmap null" : "bitmap not null ");


//            compressImage(bitmap);
            compressImage(bitmap, bitmap.getWidth(), bitmap.getHeight(), 90, srcPath.getBytes(), true);
            File file = new File(srcPath);
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
            return file;// 压缩好比例大小后再进行质量压缩
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ff", e.getClass().toString() + "");
        }
        return null;
    }


    // 图片质量压缩
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        /*
         * Log.i("compressImage", ""+baos.toByteArray().length); while
		 * (baos.toByteArray().length / 1024 > 200) { //
		 * 循环判断如果压缩后图片是否大于100kb,大于继续压缩 baos.reset();// 重置baos即清空baos
		 * image.compress(Bitmap.CompressFormat.JPEG, options, baos);//
		 * 这里压缩options%，把压缩后的数据存放到baos中 options -= 10;// 每次都减少10
		 * Log.i("compressImage", ""+baos.toByteArray().length); //
		 * System.out.println("压缩: "+baos.toByteArray().length); }
		 */
        if (baos.toByteArray().length / 1024 > 300) {
            baos.reset();// 重置baos即清空baos
            options = 90;
            image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    // 把Bitmap 转成 Byte
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    static public Bitmap scaleBitmap(Bitmap aBitmap, float width, float height) {
        if (aBitmap == null) {
            return null;
        }

        final float fWidth = width / (float) aBitmap.getWidth();
        final float fHeight = height / (float) aBitmap.getHeight();
        final float fScale = fWidth < fHeight ? fWidth : fHeight;

        if (fScale == 1.0) {
            return aBitmap;
        } else {
            final Matrix matrix = new Matrix();
            matrix.setScale(fScale, fScale);
            final Bitmap miniThumbnail = Bitmap.createBitmap(aBitmap, 0, 0, aBitmap.getWidth(), aBitmap.getHeight(),
                    matrix, true);
            if (aBitmap != null) {
                aBitmap.recycle();
                aBitmap = null;
            }
            return miniThumbnail;
        }
    }

    // 图片转为文件
    public static boolean saveBitmap2file(Bitmap bmp) {
        CompressFormat format = CompressFormat.JPEG;
        Bitmap bitmap = compressImage(bmp);
        int quality = 100;
        OutputStream stream = null;
        try {
            // 在SDcard创建文件夹及文件
            File bitmapFile = new File(File.separator + "sobu_cache_img.jpg");
            bitmapFile.getParentFile().mkdirs();// 创建文件夹
            stream = new FileOutputStream(File.separator + "sobu_cache_img.jpg");// "/sdcard/"
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean sucess = bitmap.compress(format, quality, stream);
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        return sucess;
    }

    // 图片转为文件
    public static boolean saveBitmap2file(Bitmap bmp, String path) {

        CompressFormat format = CompressFormat.JPEG;
        Bitmap bitmap = compressImage(bmp);

        int quality = 100;
        OutputStream stream = null;
        try {
            // 在SDcard创建文件夹及文件
            File bitmapFile = new File(path);
            bitmapFile.getParentFile().mkdirs();// 创建文件夹
            stream = new FileOutputStream(path);// "/sdcard/"
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean sucess = bitmap.compress(format, quality, stream);
        return sucess;
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

}
