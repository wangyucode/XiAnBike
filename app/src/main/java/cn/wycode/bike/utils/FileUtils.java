package cn.wycode.bike.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;

import java.io.File;


/**
 * Created by wy on 2015/11/23.
 * 关于文件的处理
 */
public class FileUtils {

    protected static boolean return_data = false;
    protected static boolean scale = true;
    protected static boolean faceDetection = true;
    protected static boolean circleCrop = false;

    /**
     * 裁剪
     *
     * @param uri
     */
    public static void startPhotoZoom(Context context, Uri uri, int proportion,
                                      int x, int y, Uri outPut) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", proportion);
        intent.putExtra("aspectY", 2);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", x);
        intent.putExtra("outputY", y);

        intent.putExtra("scale", scale);
        intent.putExtra("return-data", return_data);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPut);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", !faceDetection); // lol, negative
        // boolean
        // noFaceDetection
        if (circleCrop) {
            intent.putExtra("circleCrop", true);
        }
        ((Activity) context).startActivityForResult(intent,
                500);  //request code 500
    }


    /**
     * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
     *
     * @param imagePath 图片在SDCard中的路径
     * @return
     */
    public static Bitmap getSDCardImg(String imagePath) {
        if (!isSDCardUser()) {
            return null;
        }
        // Log.i("HDD","url = " + imagePath);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        Bitmap bmp = null;
        try {
            if (isFile(imagePath)) {
                bmp = BitmapFactory.decodeFile(imagePath, opt);
            }
        } catch (OutOfMemoryError err) {
            if (bmp != null) {
                bmp.recycle();
            }
            return getSDCardImgDistortion(imagePath);
        }
        return bmp;
    }

    /**
     * 以最省内存的方式读取本地资源的图片 或者SDCard中的图片
     *
     * @param imageFile 图片在SDCard中的路径
     * @return
     */
    public static Bitmap getSDCardImgDistortion(String imageFile) {
        if (!isSDCardUser()) {
            return null;
        }

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, 128 * 128);
        opts.inJustDecodeBounds = false;
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeFile(imageFile, opts);
        } catch (OutOfMemoryError err) {
            return getNativeImageForAppointSize(imageFile);
        }
        return bmp;
    }

    /**
     * 判断SD卡是否可用
     *
     * @return
     */
    public static boolean isSDCardUser() {
        if (FileUtils.isExistSDCard()) {
            return FileUtils.getSDFreeSize() > 2;
        } else {
            return false;
        }
    }

    /**
     * 判断SD卡上的文件夹是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFile(String fileName) {
        File file = new File(fileName);
        return file.isFile();
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
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

    /**
     * 获取本地图片并指定高度和宽度（最大宽度：600；最大高度：1000）
     *
     * @param imagePath 图片路径
     * @return
     */
    public static Bitmap getNativeImageForAppointSize(String imagePath) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap myBitmap = BitmapFactory.decodeFile(imagePath, options); // 此时返回myBitmap为空

        // 计算缩放比
        int be = 1;
        int wScale = (int) (options.outWidth / (float) 600 + 0.5);
        int hScale = (int) (options.outWidth / (float) 1000 + 0.5);
        if (wScale > 1 || hScale > 1) {
            if (wScale > hScale) {
                be = wScale;
            } else {
                be = hScale;
            }
        }
        options.inSampleSize = be;

        // 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        myBitmap = BitmapFactory.decodeFile(imagePath, options);
        return myBitmap;
    }

    /**
     * SD卡是否存在
     *
     * @return
     */
    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * SD卡剩余空间
     *
     * @return
     */
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        @SuppressWarnings("deprecation")
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        @SuppressWarnings("deprecation")
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        // return freeBlocks * blockSize; //单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }


    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {

        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
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


    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        try {
            if (width <= height) {
                roundPx = width / 2;
                left = 0;
                top = 0;
                right = width;
                bottom = width;
                height = width;
                dst_left = 0;
                dst_top = 0;
                dst_right = width;
                dst_bottom = width;
            } else {
                roundPx = height / 2;
                float clip = (width - height) / 2;
                left = clip;
                right = width - clip;
                top = 0;
                bottom = height;
                width = height;
                dst_left = 0;
                dst_top = 0;
                dst_right = height;
                dst_bottom = height;
            }
            output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        } catch (OutOfMemoryError e) {
            // TODO: handle exception
            return null;
        }
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

        // 以下有两种方法画圆,drawRounRect和drawCircle
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        // canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

}
