package com.trubuzz.trubuzz.trySomething;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.widget.ImageView;

import com.trubuzz.trubuzz.utils.DoIt;

import java.util.Date;

/**
 * Created by king on 16/11/30.
 */

public class Ut {
    ImageView img;
    String TAG = "jcd_" + this.getClass().getSimpleName();

    private Bitmap imgMarker;
    private int width,height;   //图片的高度和宽带


    private Bitmap createDrawable(String str) {
        Bitmap imgTemp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imgTemp);
        Paint paint = new Paint(); // 建立画笔
        paint.setDither(true);
        paint.setFilterBitmap(true);
        Rect src = new Rect(0, 0, imgMarker.getWidth(), imgMarker.getHeight());
        Rect dst = new Rect(0, 0, imgMarker.getWidth(), imgMarker.getHeight());
        canvas.drawBitmap(imgMarker, src, dst, paint);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        textPaint.setTextSize(20.0f);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 采用默认的宽度
        textPaint.setColor(Color.WHITE);

        canvas.drawText(String.valueOf(str), width /2-5, height/2+5,
                textPaint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return imgTemp;

    }

    // 为图片target添加水印文字
    // Bitmap target：被添加水印的图片
    // String mark：水印文章
    public Bitmap createWatermark(Bitmap target, String mark) {
        int w = target.getWidth();
        int h = target.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);     //设置画笔
        p.setStrokeWidth(5);                //画笔大小
        p.setTextAlign(Paint.Align.CENTER);

        // 水印的颜色
        String cs = DoIt.toHexUpString(new Date().getTime());
        String c = cs.substring(cs.length()-8 , cs.length());
        Long l =  Long.parseLong(c ,16);
        int color = l.intValue();
//        p.setColor(Color.BLUE);
            p.setColor(color);
        // 水印的字体大小
        p.setTextSize(80);

        p.setAntiAlias(true);// 去锯齿

        canvas.drawBitmap(target, 0, 0, p);

        // 在左边的中间位置开始添加水印
        canvas.drawText(mark, w/2, h / 2, p);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }
}
