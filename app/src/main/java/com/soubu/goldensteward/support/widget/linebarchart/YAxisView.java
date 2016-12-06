package com.soubu.goldensteward.support.widget.linebarchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.utils.ConvertUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dingsigang on 16-10-11.
 */
public class YAxisView extends View {
    private Map<Integer, String> map = new HashMap<>();
    private int bottom = -1;
    private int top = -1;
    private int type;
    private final int SCALE_MARGIN = ConvertUtil.dip2px(getContext(), 4);

    public YAxisView(Context context) {
        super(context);
    }

    public YAxisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray tArray = context.obtainStyledAttributes(attrs,
                R.styleable.YAxisView);
        type = tArray.getInt(R.styleable.YAxisView_type, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!map.isEmpty()) {
            drawNumbers(canvas);
        }
        if (top != -1 && bottom != -1) {
            drawLines(canvas);
        }
    }

    public void setParams(Map<Integer, String> map, int top, int bottom) {
        this.map = map;
        this.top = top;
        this.bottom = bottom;
        post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    private void drawLines(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(ConvertUtil.dip2px(getContext(), 2));
        linePaint.setColor(getResources().getColor(R.color.item_line_grey));
        if (type == 0) {
            canvas.drawLine(getWidth(), top, getWidth(), bottom, linePaint);
        } else {
            canvas.drawLine(0, top, 0, bottom, linePaint);
        }
    }

    private void drawNumbers(Canvas canvas) {
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(ConvertUtil.sp2px(getContext(), 12));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(getResources().getColor(R.color.subtitle_grey));
        Rect textBound = new Rect();
        textPaint.getTextBounds("0", 0, "0".length(), textBound);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (type == 0) {
                textPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(entry.getValue(), getWidth() - SCALE_MARGIN, entry.getKey() + textBound.height() / 2, textPaint);
            } else {
                textPaint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(entry.getValue(), 0 + SCALE_MARGIN, entry.getKey() + textBound.height() / 2, textPaint);
            }
        }
    }
}
