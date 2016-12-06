package com.soubu.goldensteward.support.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lakers on 16/11/3.
 */

public class ProgressImageView extends ImageView {

    private Paint mPaint;// 画笔
    Context context = null;
    int progress = 100;
    boolean finish = false;

    public ProgressImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressImageView(Context context, AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPaint = new Paint();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.FILL);


        mPaint.setColor(Color.parseColor("#70000000"));// 半透明
        canvas.drawRect(0, 0, getWidth(), getHeight() - getHeight() * progress
                / 100, mPaint);

        mPaint.setColor(Color.parseColor("#00000000"));// 全透明
        canvas.drawRect(0, getHeight() - getHeight() * progress / 100,
                getWidth(), getHeight(), mPaint);

        mPaint.setTextSize(50);
        mPaint.setColor(Color.parseColor("#FFFFFF"));
        mPaint.setStrokeWidth(2);
        Rect rect = new Rect();
        mPaint.getTextBounds("100%", 0, "100%".length(), rect);// 确定文字的宽度
        if(progress != 100){
            canvas.drawText(progress + "%", getWidth() / 2 - rect.width() / 2,
                    getHeight() / 2, mPaint);
        }
//        if(finish){
//            canvas.drawText("已上传", getWidth() / 2 - rect.width() / 2,
//                    getHeight() / 2, mPaint);
//        }


    }

    public void setProgress(int progress) {
        this.progress = progress;
//        if(progress == 100){
//            finish = true;
//        } else if(finish){
//            finish = false;
//        }
        postInvalidate();
    }

}
