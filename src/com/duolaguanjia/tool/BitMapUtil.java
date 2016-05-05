package com.duolaguanjia.tool;

import android.graphics.*;

/**
 * Created by Administrator on 2015/12/29.
 */
public class BitMapUtil
{
public static final Bitmap grey(Bitmap bitmap)
{
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();

    Bitmap faceIconGreyBitmap = Bitmap
            .createBitmap(width, height, Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(faceIconGreyBitmap);
    Paint paint = new Paint();
    ColorMatrix colorMatrix = new ColorMatrix();
    colorMatrix.setSaturation(0);
    ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
            colorMatrix);
    paint.setColorFilter(colorMatrixFilter);
    canvas.drawBitmap(bitmap, 0, 0, paint);
    return faceIconGreyBitmap;
}
}

