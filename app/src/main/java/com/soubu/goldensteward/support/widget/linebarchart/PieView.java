package com.soubu.goldensteward.support.widget.linebarchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.soubu.goldensteward.R;
import com.soubu.goldensteward.support.utils.ConvertUtil;

import java.util.ArrayList;

/**
 * Created by Dacer on 9/26/14.
 */
public class PieView extends View {

    public interface OnPieClickListener {
        void onPieClick(int index);
    }

    private Paint cirPaint;
    private Paint whiteInnerCirclePaint;

    //一层透明遮罩。。。
    private Paint white2InnerCirclePaint;

    private Paint whiteLinePaint;
    private Point pieCenterPoint;
    private Paint textPaint;
    private Paint pointTextPaint;
    private RectF cirRect;
    private RectF cirUnSelectedRect;
    private RectF whiteInnerCirRect;
    private RectF whiteInner2CirRect;
    private RectF cirSelectedRect;

    private int mViewWidth;
    private int mViewHeight;
    private int margin;
    private int pieRadius;

    private String line1;
    private String line2;


    private OnPieClickListener onPieClickListener;

    private ArrayList<PieHelper> pieHelperList;
    private int selectedIndex = NO_SELECTED_INDEX;

    private boolean showPercentLabel = true;
    public static final int NO_SELECTED_INDEX = -999;
    private final int[] DEFAULT_COLOR_LIST = {Color.parseColor("#33B5E5"),
            Color.parseColor("#AA66CC"),
            Color.parseColor("#99CC00"),
            Color.parseColor("#FFBB33"),
            Color.parseColor("#FF4444")};


    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            boolean needNewFrame = false;
            for (PieHelper pie : pieHelperList) {
                pie.update();
                if (!pie.isAtRest()) {
                    needNewFrame = true;
                }
            }
            if (needNewFrame) {
                postDelayed(this, 10);
            }
            invalidate();
        }
    };

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);

        pieHelperList = new ArrayList<PieHelper>();
        cirPaint = new Paint();
        cirPaint.setAntiAlias(true);
        cirPaint.setColor(Color.GRAY);
        whiteInnerCirclePaint = new Paint(cirPaint);
        whiteInnerCirclePaint.setColor(Color.parseColor("#FFFFFF"));
        white2InnerCirclePaint = new Paint(whiteInnerCirclePaint);
        white2InnerCirclePaint.setAlpha(50);
        whiteLinePaint = new Paint(cirPaint);
        whiteLinePaint.setColor(Color.WHITE);
        whiteLinePaint.setStrokeWidth(20f);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(ConvertUtil.sp2px(getContext(), 13));
        textPaint.setStrokeWidth(5);
        textPaint.setTextAlign(Paint.Align.CENTER);
        pointTextPaint = new Paint(textPaint);
        pointTextPaint.setColor(getResources().getColor(R.color.subtitle_grey));
        pieCenterPoint = new Point();
        cirRect = new RectF();
        cirSelectedRect = new RectF();
        whiteInnerCirRect = new RectF();
        whiteInner2CirRect = new RectF();
        cirUnSelectedRect = new RectF();
    }

    public void showPercentLabel(boolean show) {
        showPercentLabel = show;
        postInvalidate();
    }

    public void setOnPieClickListener(OnPieClickListener listener) {
        onPieClickListener = listener;
    }

    public void setDate(ArrayList<PieHelper> helperList, String line1, String line2) {
        this.line1 = line1;
        this.line2 = line2;
        initPies(helperList);
        pieHelperList.clear();
        removeSelectedPie();

        if (helperList != null && !helperList.isEmpty()) {
            for (PieHelper pieHelper : helperList) {
                pieHelperList.add(new PieHelper(pieHelper.getStartDegree(), pieHelper.getStartDegree(), pieHelper));
            }
        } else {
            pieHelperList.clear();
        }

        removeCallbacks(animator);
        post(animator);

//        pieHelperList = helperList;
        postInvalidate();
    }

    /**
     * Set startDegree and endDegree for each PieHelper
     *
     * @param helperList
     */
    private void initPies(ArrayList<PieHelper> helperList) {
        float totalAngel = 270;
        for (PieHelper pie : helperList) {
            pie.setDegree(totalAngel, totalAngel + pie.getSweep());
            totalAngel += pie.getSweep();
        }
    }

    public void selectedPie(int index) {
        selectedIndex = index;
        if (onPieClickListener != null) onPieClickListener.onPieClick(index);
        postInvalidate();
    }

    public void removeSelectedPie() {
        selectedIndex = NO_SELECTED_INDEX;
        if (onPieClickListener != null) onPieClickListener.onPieClick(NO_SELECTED_INDEX);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (pieHelperList.isEmpty()) {
            return;
        }

        int index = 0;
        for (PieHelper pieHelper : pieHelperList) {
            boolean selected = (selectedIndex == index);
            RectF rect;
            switch (pieHelper.getType()) {
                case PieHelper.TYPE_SMALL:
                    rect = cirUnSelectedRect;
                    break;
                case PieHelper.TYPE_NORMAL:
                    rect = cirRect;
                    break;
                case PieHelper.TYPE_BIG:
                    rect = cirSelectedRect;
                    break;
                default:
                    rect = cirRect;
                    break;
            }
            if (pieHelper.isColorSetted()) {
                cirPaint.setColor(pieHelper.getColor());
            } else {
                cirPaint.setColor(DEFAULT_COLOR_LIST[index % 5]);
            }
            canvas.drawArc(rect, pieHelper.getStartDegree(), pieHelper.getSweep(), true, cirPaint);
            canvas.drawArc(whiteInner2CirRect, pieHelper.getStartDegree(), pieHelper.getSweep(), true, white2InnerCirclePaint);
            canvas.drawArc(whiteInnerCirRect, pieHelper.getStartDegree(), pieHelper.getSweep(), true, whiteInnerCirclePaint);

//            drawPercentText(canvas, pieHelper);
            drawLineBesideCir(canvas, pieHelper.getStartDegree(), selected);
            drawLineBesideCir(canvas, pieHelper.getEndDegree(), selected);
            drawPointText(canvas);
            index++;
        }

    }


    private void drawPointText(Canvas canvas) {
        canvas.drawText(line1, pieCenterPoint.x, pieCenterPoint.y - ConvertUtil.dip2px(getContext(), 4), pointTextPaint);
        canvas.drawText(line2, pieCenterPoint.x, pieCenterPoint.y + ConvertUtil.dip2px(getContext(), 12), pointTextPaint);

    }

    private void drawLineBesideCir(Canvas canvas, float angel, boolean selectedCir) {
//        int sth2 = selectedCir ? mViewHeight / 2 : pieRadius; // Sorry I'm really don't know how to name the variable.
        //此处把白线长度写死
        int sth2 = mViewHeight / 2;
        int sth = 1;                                       // And it's
        if (angel % 360 > 180 && angel % 360 < 360) {
            sth = -1;
        }
        float lineToX = (float) (mViewHeight / 2 + Math.cos(Math.toRadians(-angel)) * sth2);
        float lineToY = (float) (mViewHeight / 2 + sth * Math.abs(Math.sin(Math.toRadians(-angel))) * sth2);
        canvas.drawLine(pieCenterPoint.x, pieCenterPoint.y, lineToX, lineToY, whiteLinePaint);
    }

    private void drawPercentText(Canvas canvas, PieHelper pieHelper) {
        if (!showPercentLabel) return;
        float angel = (pieHelper.getStartDegree() + pieHelper.getEndDegree()) / 2;
        int sth = 1;
        if (angel % 360 > 180 && angel % 360 < 360) {
            sth = -1;
        }
        float x = (float) (mViewHeight / 2 + Math.cos(Math.toRadians(-angel)) * pieRadius / 2);
        float y = (float) (mViewHeight / 2 + sth * Math.abs(Math.sin(Math.toRadians(-angel))) * pieRadius / 2);
        canvas.drawText(pieHelper.getPercentStr(), x, y, textPaint);
    }

    private void drawText(Canvas canvas, PieHelper pieHelper) {
        if (pieHelper.getTitle() == null) return;
        float angel = (pieHelper.getStartDegree() + pieHelper.getEndDegree()) / 2;
        int sth = 1;
        if (angel % 360 > 180 && angel % 360 < 360) {
            sth = -1;
        }
        float x = (float) (mViewHeight / 2 + Math.cos(Math.toRadians(-angel)) * pieRadius / 2);
        float y = (float) (mViewHeight / 2 + sth * Math.abs(Math.sin(Math.toRadians(-angel))) * pieRadius / 2);
        canvas.drawText(pieHelper.getTitle(), x, y, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            selectedIndex = findPointAt((int) event.getX(), (int) event.getY());
            if (onPieClickListener != null) {
                onPieClickListener.onPieClick(selectedIndex);
            }
            postInvalidate();
        }

        return true;
    }

    /**
     * find pie index where point is
     *
     * @param x
     * @param y
     * @return
     */
    private int findPointAt(int x, int y) {
        double degree = Math.atan2(x - pieCenterPoint.x, y - pieCenterPoint.y) * 180 / Math.PI;
        degree = -(degree - 180) + 270;
        int index = 0;
        for (PieHelper pieHelper : pieHelperList) {
            if (degree >= pieHelper.getStartDegree() && degree <= pieHelper.getEndDegree()) {
                return index;
            }
            index++;
        }
        return NO_SELECTED_INDEX;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);
        margin = mViewWidth / 16;
        pieRadius = (mViewWidth) / 2 - margin;
        int innerPieRadius = pieRadius / 3;
        int inner2PieRadius = pieRadius / 10 * 4;
        int unSelectedPieRadius = pieRadius / 10 * 9;
//        int selectPieRadius = pieRadius / 3 * 4;
        pieCenterPoint.set(pieRadius + margin, pieRadius + margin);
        cirRect.set(pieCenterPoint.x - pieRadius,
                pieCenterPoint.y - pieRadius,
                pieCenterPoint.x + pieRadius,
                pieCenterPoint.y + pieRadius);
        whiteInnerCirRect.set(pieCenterPoint.x - innerPieRadius,
                pieCenterPoint.y - innerPieRadius,
                pieCenterPoint.x + innerPieRadius,
                pieCenterPoint.y + innerPieRadius);
        whiteInner2CirRect.set(pieCenterPoint.x - inner2PieRadius,
                pieCenterPoint.y - inner2PieRadius,
                pieCenterPoint.x + inner2PieRadius,
                pieCenterPoint.y + inner2PieRadius);
        cirUnSelectedRect.set(pieCenterPoint.x - unSelectedPieRadius,
                pieCenterPoint.y - unSelectedPieRadius,
                pieCenterPoint.x + unSelectedPieRadius,
                pieCenterPoint.y + unSelectedPieRadius);
        cirSelectedRect.set(2, //minor margin for bigger circle
                2,
                mViewWidth - 2,
                mViewHeight - 2);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec) {
        int preferred = 3;
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec) {
        int preferred = mViewWidth;
        return getMeasurement(measureSpec, preferred);
    }

    private int getMeasurement(int measureSpec, int preferred) {
        int specSize = MeasureSpec.getSize(measureSpec);
        int measurement;

        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:
                measurement = specSize;
                break;
            case MeasureSpec.AT_MOST:
                measurement = Math.min(preferred, specSize);
                break;
            default:
                measurement = preferred;
                break;
        }
        return measurement;
    }
}
