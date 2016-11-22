package com.soubu.goldensteward.widget.linebarchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.NinePatchDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.soubu.goldensteward.R;
import com.soubu.goldensteward.utils.ConvertUtil;
import com.soubu.goldensteward.utils.MathDoubleUtil;
import com.soubu.goldensteward.utils.RegularUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;
import static java.lang.Math.log;
import static java.lang.Math.pow;

public class LineView extends View {
    //存储各个底部栏的日期
    private List<Date> mDates;
    //底部栏的size
    private int mBottomSize;
    //单位
    private String mUnit;
    //x轴坐标显示格式
    private String mFromat;


    private ArrayList<ArrayList<String>> mContentList = new ArrayList<>();

    private int mViewHeight;
    private int bottomLineY;
    private int verticalGridNum = -1;
    private YAxisView leftAxisView;
    private YAxisView rightAxisView;
    private Map<Integer, String> leftScaleMap = new HashMap<>();
    private Map<Integer, String> rightScaleMap = new HashMap<>();
    //drawBackground
    private boolean autoSetDataOfGird = true;
    private boolean autoSetGridWidth = true;
    private int dataOfAGridRight = -1;
    private int dataOfAGirdLeft = -1;
    private int bottomTextHeight = 0;
    private ArrayList<String> bottomTextList = new ArrayList<String>();

    private ArrayList<ArrayList<Integer>> dataLists;
    private ArrayList<Integer> dataList;

    private ArrayList<ArrayList<Float>> percentList;
    private ArrayList<ArrayList<Float>> targetPercentList;
    private ArrayList<ArrayList<Integer>> barList;
    private ArrayList<Integer> barColorList;

    private ArrayList<Integer> xCoordinateList = new ArrayList<Integer>();
    private ArrayList<Integer> yCoordinateList = new ArrayList<Integer>();

    private ArrayList<ArrayList<Dot>> drawDotLists = new ArrayList<ArrayList<Dot>>();
    private ArrayList<Dot> drawDotList = new ArrayList<Dot>();

    private ArrayList<Rect> bars = new ArrayList<>();
    //用来保存触摸区域的bars
    private ArrayList<Rect> touchBars = new ArrayList<>();

    private Paint bottomTextPaint = new Paint();
    private int bottomTextDescent;

    //    //popup
    private Paint popupTextPaint = new Paint();

    private Paint fgPaint;

    private final int bottomTriangleHeight = 12;
    public boolean showPopup = true;

    private Dot pointToSelect;
    private Dot selectedDot;

    private int barToSelect = -1;
    private int selectedBarIndex = -1;
    private int selectedBarListIndex = -1;

    private int topLineLength = ConvertUtil.dip2px(getContext(), 20); // | | ←this
    //-+-+-
    private int sideLineLength = ConvertUtil.dip2px(getContext(), 45) / 3 * 2;// --+--+--+--+--+--+--
    //  ↑this
    private int backgroundGridWidth = ConvertUtil.dip2px(getContext(), 45);

    //Constants
    private final int popupTopPadding = ConvertUtil.dip2px(getContext(), 2);
    private final int popupBottomMargin = ConvertUtil.dip2px(getContext(), 5);
    private final int bottomTextTopMargin = ConvertUtil.sp2px(getContext(), 0);
    private final int bottomLineLength = ConvertUtil.sp2px(getContext(), 10);
    private final int DOT_INNER_CIR_RADIUS = ConvertUtil.dip2px(getContext(), 2);
    private final int DOT_OUTER_CIR_RADIUS = ConvertUtil.dip2px(getContext(), 5);
    private final int MIN_TOP_LINE_LENGTH = ConvertUtil.dip2px(getContext(), 16);
    private final int MIN_VERTICAL_GRID_NUM = 4;
    private final int MIN_HORIZONTAL_GRID_NUM = 1;
    private final int BAR_SIDE_MARGIN = ConvertUtil.dip2px(getContext(), 18);
    private final int BACKGROUND_LINE_COLOR = getResources().getColor(R.color.item_line_grey);
    private final int BOTTOM_TEXT_COLOR = Color.parseColor("#9B9A9B");
    private final int barWidth = ConvertUtil.dip2px(getContext(), 26);

    private Boolean drawDotLine = false;

    private String[] colorArray = {"#e74c3c", "#2980b9", "#1abc9c"};

    private int[] popupColorArray = {R.drawable.popup_red, R.drawable.popup_orange};

    // onDraw optimisations
    private final Point tmpPoint = new Point();

    public void setDrawDotLine(Boolean drawDotLine) {
        this.drawDotLine = drawDotLine;
    }

    private Runnable lineAnimator = new Runnable() {
        @Override
        public void run() {
            boolean needNewFrame = false;
            for (ArrayList<Dot> data : drawDotLists) {
                for (Dot dot : data) {
                    dot.update();
                    if (!dot.isAtRest()) {
                        needNewFrame = true;
                    }
                }
            }
            if (needNewFrame) {
                postDelayed(this, 25);
            }
            invalidate();
        }
    };

    private Runnable barAnimator = new Runnable() {
        @Override
        public void run() {
            boolean needNewFrame = false;
            ArrayList<Float> aTargetPercentList;
            ArrayList<Float> aPercentList;
            for (int j = 0; j < targetPercentList.size(); j++) {
                aTargetPercentList = targetPercentList.get(j);
                aPercentList = percentList.get(j);
                for (int i = 0; i < aTargetPercentList.size(); i++) {
                    if (aPercentList.get(i) < aTargetPercentList.get(i)) {
                        aPercentList.set(i, aPercentList.get(i) + 0.02f);
                        needNewFrame = true;
                    } else if (aPercentList.get(i) > aTargetPercentList.get(i)) {
                        aPercentList.set(i, aPercentList.get(i) - 0.02f);
                        needNewFrame = true;
                    }
                    if (Math.abs(aTargetPercentList.get(i) - aPercentList.get(i)) < 0.02f) {
                        aPercentList.set(i, aTargetPercentList.get(i));
                    }
                }
            }
            if (needNewFrame) {
                postDelayed(this, 20);
            }
            invalidate();
        }
    };

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        popupTextPaint.setAntiAlias(true);
        popupTextPaint.setColor(getResources().getColor(R.color.colorPrimary));
        popupTextPaint.setTextSize(ConvertUtil.sp2px(getContext(), 13));
        popupTextPaint.setStrokeWidth(5);
        popupTextPaint.setTextAlign(Paint.Align.CENTER);

        bottomTextPaint.setAntiAlias(true);
        bottomTextPaint.setTextSize(ConvertUtil.sp2px(getContext(), 12));
        bottomTextPaint.setTextAlign(Paint.Align.CENTER);
        bottomTextPaint.setStyle(Paint.Style.FILL);
        bottomTextPaint.setColor(BOTTOM_TEXT_COLOR);

        fgPaint = new Paint();
        fgPaint.setAntiAlias(true);
    }

    //由于底部栏的具体长短可能存在异步的情况，因此在此处先将size定义好
    public void setBottomTextSize(int size) {
        mBottomSize = size;
    }

    /**
     * dataList will be reset when called is method.
     *
     * @param dateList The Date ArrayList in the bottom.
     */
    public void setBottomTextList(ArrayList<Date> dateList, String format) {
        this.dataList = null;
        bottomTextList.clear();
        mDates = dateList;
        mFromat = format;
        for (Date date : dateList) {
            bottomTextList.add(ConvertUtil.dateToCustom(date, format));
        }
        Rect r = new Rect();
        int longestWidth = 0;
        String longestStr = "";
        bottomTextDescent = 0;
        for (String s : bottomTextList) {
            bottomTextPaint.getTextBounds(s, 0, s.length(), r);
            if (bottomTextHeight < r.height()) {
                bottomTextHeight = r.height();
            }
            if (autoSetGridWidth && (longestWidth < r.width())) {
                longestWidth = r.width();
                longestStr = s;
            }
            if (bottomTextDescent < (Math.abs(r.bottom))) {
                bottomTextDescent = Math.abs(r.bottom);
            }
        }

        if (autoSetGridWidth) {
            if (backgroundGridWidth < longestWidth) {
                backgroundGridWidth = longestWidth + (int) bottomTextPaint.measureText(longestStr, 0, 1);
            }
            if (sideLineLength < longestWidth / 2) {
                sideLineLength = longestWidth / 2;
            }
        }
        refreshXCoordinateList(getHorizontalGridNum());
    }

    /**
     * @param dataLists The Integer ArrayLists for showing,
     *                  dataList.size() must be smaller than bottomTextList.size()
     */
    public void setDataList(ArrayList<ArrayList<Integer>> dataLists, YAxisView rightAxisView, int dataOfAGridRight) {
        this.rightAxisView = rightAxisView;
        this.dataOfAGridRight = dataOfAGridRight;
        selectedDot = null;
        this.dataLists = dataLists;
        for (ArrayList<Integer> list : dataLists) {
            if (list.size() > bottomTextList.size()) {
                throw new RuntimeException("dacer.LineView error:" +
                        " dataList.size() > bottomTextList.size() !!!");
            }
        }
        int biggestData = 0;
        for (ArrayList<Integer> list : dataLists) {
            if (autoSetDataOfGird) {
                for (Integer i : list) {
                    if (biggestData < i) {
                        biggestData = i;
                    }
                }
            }
        }

        refreshAfterDataChanged();
        showPopup = true;
        setMinimumWidth(0); // It can help the LineView reset the Width,
        // I don't know the better way..
        postInvalidate();
    }

    private void refreshAfterDataChanged() {
        verticalGridNum = getVerticalGridNum();
        refreshTopLineLength(verticalGridNum);
        refreshYCoordinateList(verticalGridNum);
        refreshDrawDotList(verticalGridNum);
    }

    private int getVerticalGridNum() {
        int verticalGridNum = MIN_VERTICAL_GRID_NUM;
        if (dataLists != null && !dataLists.isEmpty()) {
            for (ArrayList<Integer> list : dataLists) {
                for (Integer integer : list) {
                    if (verticalGridNum < (integer + 1)) {
                        verticalGridNum = integer + 1;
                    }
                }
            }
        }
        if (barList != null && !barList.isEmpty()) {
            for (ArrayList<Integer> list : barList) {
                for (Integer integer : list) {
                    if (verticalGridNum < (integer + 1)) {
                        verticalGridNum = integer + 1;
                    }
                }
            }
        }
        return verticalGridNum;
    }

    private int getHorizontalGridNum() {
        int horizontalGridNum = mBottomSize - 1;
        if (horizontalGridNum < MIN_HORIZONTAL_GRID_NUM) {
            horizontalGridNum = MIN_HORIZONTAL_GRID_NUM;
        }
        return horizontalGridNum;
    }

    private void refreshXCoordinateList(int horizontalGridNum) {
        xCoordinateList.clear();
        for (int i = 0; i < (horizontalGridNum + 1); i++) {
            xCoordinateList.add(sideLineLength + backgroundGridWidth * i);
        }

    }

    private void refreshYCoordinateList(int verticalGridNum) {
        yCoordinateList.clear();
        for (int i = 0; i < (verticalGridNum + 1); i++) {
            yCoordinateList.add(topLineLength +
                    ((mViewHeight - topLineLength - bottomTextHeight - bottomTextTopMargin -
                            bottomLineLength - bottomTextDescent) * i / (verticalGridNum)));
        }
    }

    private void refreshDrawDotList(int verticalGridNum) {
        if (dataLists != null && !dataLists.isEmpty()) {
            if (drawDotLists.size() == 0) {
                for (int k = 0; k < dataLists.size(); k++) {
                    drawDotLists.add(new ArrayList<Dot>());
                }
            }
            for (int k = 0; k < dataLists.size(); k++) {
                int drawDotSize = drawDotLists.get(k).isEmpty() ? 0 : drawDotLists.get(k).size();

                for (int i = 0; i < dataLists.get(k).size(); i++) {
                    int x = xCoordinateList.get(i);
                    int y = yCoordinateList.get(verticalGridNum - dataLists.get(k).get(i));
                    if (i > drawDotSize - 1) {
                        drawDotLists.get(k).add(new Dot(x, 0, x, y, dataLists.get(k).get(i), k));
                    } else {
                        drawDotLists.get(k).set(i, drawDotLists.get(k).get(i).setTargetData(x, y, dataLists.get(k).get(i), k));
                    }
                }

                int temp = drawDotLists.get(k).size() - dataLists.get(k).size();
                for (int i = 0; i < temp; i++) {
                    drawDotLists.get(k).remove(drawDotLists.get(k).size() - 1);
                }
            }
        }
        removeCallbacks(lineAnimator);
        post(lineAnimator);
    }

    private void refreshTopLineLength(int verticalGridNum) {
        // For prevent popup can't be completely showed when backgroundGridHeight is too small.
        // But this code not so good.
        if ((mViewHeight - topLineLength - bottomTextHeight - bottomTextTopMargin) /
                (verticalGridNum + 2) < getPopupHeight()) {
            //需要最高的依然可以完整的显式点击数据，因此 + MIN_TOP_LINE_LENGTH
            topLineLength = getPopupHeight() + DOT_OUTER_CIR_RADIUS + DOT_INNER_CIR_RADIUS + 2 + MIN_TOP_LINE_LENGTH;
        } else {
            topLineLength = MIN_TOP_LINE_LENGTH;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackgroundLines(canvas);
        drawRect(canvas);
        drawLines(canvas);
        drawDots(canvas);
        if (showPopup && selectedDot != null) {
            drawPopup(canvas,
                    String.valueOf(selectedDot.data),
                    selectedDot.setupPoint(tmpPoint), popupColorArray[selectedDot.linenumber % 3]);
            selectedDot = null;
        }

        if (showPopup && selectedBarIndex != -1) {
            if (mContentList.size() == 0) {
                drawPopup(canvas,
                        String.valueOf(barList.get(selectedBarListIndex).get(selectedBarIndex)), popupColorArray[1]);
            } else {
                drawPopup(canvas,
                        String.valueOf(mContentList.get(selectedBarListIndex).get(selectedBarIndex)), popupColorArray[1]);
            }
            selectedBarIndex = -1;
            selectedBarListIndex = -1;
        }
        drawAxis();
    }

    private void drawAxis() {
        if (leftAxisView != null) {
            measureLeftScalesMap();
            leftAxisView.setParams(leftScaleMap, getTop(), bottomLineY);
        }
        if (rightAxisView != null) {
            rightAxisView.setParams(rightScaleMap, getTop(), bottomLineY);
        }
    }

    private void measureLeftScalesMap() {
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : rightScaleMap.entrySet()) {
            list.add(entry.getKey());
        }
        Collections.sort(list);
        int j = 0;
        leftScaleMap.clear();
        double a = (double) dataOfAGirdLeft / 100;
        for (int i = list.size() - 1; i >= 0; i--) {
            String space = MathDoubleUtil.mul(a, (double) j) + "";
            j++;
            space = RegularUtil.subZeroAndDot(space);
            leftScaleMap.put(list.get(i), space);
        }

    }

    private void drawRect(Canvas canvas) {
        Rect rect;
        Rect touchRect;
        bars.clear();
        touchBars.clear();
        if (percentList != null && !percentList.isEmpty()) {
            int size = percentList.size();
            List<Integer> leftList = new ArrayList<>();
            List<Integer> rightList = new ArrayList<>();
            switch (size) {
                case 1:
                    int left = sideLineLength - DOT_OUTER_CIR_RADIUS - ConvertUtil.dip2px(getContext(), 8);
                    int right = sideLineLength + DOT_OUTER_CIR_RADIUS + ConvertUtil.dip2px(getContext(), 8);
                    leftList.add(left);
                    rightList.add(right);
                    break;
                case 2:
                    int left2 = sideLineLength - DOT_INNER_CIR_RADIUS - ConvertUtil.dip2px(getContext(), 10);
                    int right2 = sideLineLength - DOT_INNER_CIR_RADIUS;
                    int left3 = sideLineLength + DOT_INNER_CIR_RADIUS;
                    int right3 = sideLineLength + DOT_INNER_CIR_RADIUS + ConvertUtil.dip2px(getContext(), 10);
                    leftList.add(left2);
                    leftList.add(left3);
                    rightList.add(right2);
                    rightList.add(right3);
                    break;
            }
            for (int j = 0; j < percentList.size(); j++) {
                fgPaint.setColor(barColorList.get(j));
                ArrayList<Float> list = percentList.get(j);
                for (int i = 0; i < list.size(); i++) {
                    rect = new Rect();
                    rect.set(backgroundGridWidth * i + leftList.get(j),
                            topLineLength + (int) ((mViewHeight - topLineLength - bottomTextHeight - bottomTextTopMargin -
                                    bottomLineLength - bottomTextDescent) * list.get(i)),
                            backgroundGridWidth * i + rightList.get(j),
                            bottomLineY);
                    bars.add(rect);
                    touchRect = new Rect();
                    touchRect.set(backgroundGridWidth * i + leftList.get(j),
                            topLineLength,
                            backgroundGridWidth * i + rightList.get(j),
                            bottomLineY);
                    touchBars.add(touchRect);
                    canvas.drawRect(rect, fgPaint);
                }
            }

        }
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

    public void setBarDataList(ArrayList<ArrayList<Integer>> list, YAxisView leftAxisView, int dataOfAGirdLeft,
                               ArrayList<Integer> colorList, ArrayList<ArrayList<String>> contentList) {
        if (list == null || list.size() == 0) {
            return;
        }
        mContentList = contentList;
        this.leftAxisView = leftAxisView;
        this.dataOfAGirdLeft = dataOfAGirdLeft;
        barList = list;
        barColorList = colorList;
//        if (verticalGridNum == -1) {
        refreshAfterDataChanged();
//        }

        percentList = new ArrayList<>();
        targetPercentList = new ArrayList<>();
        int max;
        if (dataOfAGridRight != -1) {
            max = verticalGridNum * dataOfAGirdLeft / dataOfAGridRight;
        } else {
            max = verticalGridNum;
        }
        for (int j = 0; j < barList.size(); j++) {
            ArrayList<Float> targetList = new ArrayList<>();
            ArrayList<Float> aPercentList = new ArrayList<>();
            for (Integer integer : barList.get(j)) {
                targetList.add(1 - (float) integer / (float) max);
                aPercentList.add(1f);
            }
            percentList.add(aPercentList);
            targetPercentList.add(targetList);
        }
        removeCallbacks(barAnimator);
        post(barAnimator);
    }

    /**
     * @param canvas The canvas you need to draw on.
     * @param point  The Point consists of the x y coordinates from left bottom to right top.
     *               Like is
     *               <p/>
     *               3
     *               2
     *               1
     *               0 1 2 3 4 5
     */
    private void drawPopup(Canvas canvas, String num, Point point, int PopupColor) {
        boolean singularNum = (num.length() == 1);
        int sidePadding = ConvertUtil.dip2px(getContext(), singularNum ? 8 : 5);
        int x = point.x;
        int y = point.y - ConvertUtil.dip2px(getContext(), 5);
        Rect popupTextRect = new Rect();
        popupTextPaint.getTextBounds(num, 0, num.length(), popupTextRect);
        Rect r = new Rect(x - popupTextRect.width() / 2 - sidePadding,
                y - popupTextRect.height() - bottomTriangleHeight - popupTopPadding * 2 - popupBottomMargin,
                x + popupTextRect.width() / 2 + sidePadding,
                y + popupTopPadding - popupBottomMargin);

        NinePatchDrawable popup = (NinePatchDrawable) getResources().getDrawable(PopupColor);
        popup.setBounds(r);
        popup.draw(canvas);
        canvas.drawText(num, x, y - bottomTriangleHeight - popupBottomMargin * 4 / 3, popupTextPaint);
    }


    private void drawPopup(Canvas canvas, String num, int PopupColor) {
        boolean singularNum = (num.length() == 1);
        int sidePadding = ConvertUtil.dip2px(getContext(), singularNum ? 8 : 5);
        int barIndex = selectedBarListIndex * bottomTextList.size() + selectedBarIndex;
        int x = (bars.get(barIndex).right + bars.get(barIndex).left) / 2;
        int y = bars.get(barIndex).top;
        Rect popupTextRect = new Rect();
        String text;
        if (TextUtils.equals(mFromat, "yy-MM")) {
            text = ConvertUtil.dateToCustom(mDates.get(barIndex), mFromat);
        } else {
            text = ConvertUtil.dateToYYYY_MM_DD(mDates.get(barIndex));
        }
        String text2 = num + mUnit;
        int length = text.length();
        if (text2.length() > length) {
            length = text2.length();
        }
        popupTextPaint.getTextBounds(text, 0, length, popupTextRect);
        Rect r = new Rect(x - popupTextRect.width() / 2 - sidePadding,
                y - popupTextRect.height() * 2 - bottomTriangleHeight - popupTopPadding * 2 - popupBottomMargin * 3,
                x + popupTextRect.width() / 2 + sidePadding,
                y + popupTopPadding - popupBottomMargin);

        NinePatchDrawable popup = (NinePatchDrawable) getResources().getDrawable(PopupColor);
        popup.setBounds(r);
        popup.draw(canvas);
        canvas.drawText(text, x, y - bottomTriangleHeight - popupBottomMargin * 4 / 3 - popupTextPaint.getTextSize() * 4 / 3, popupTextPaint);
        canvas.drawText(text2, x, y - bottomTriangleHeight - popupBottomMargin * 4 / 3 - popupTextPaint.getTextSize() * 1 / 3, popupTextPaint);

    }

    private int getPopupHeight() {
        Rect popupTextRect = new Rect();
        popupTextPaint.getTextBounds("9", 0, 1, popupTextRect);
        Rect r = new Rect(-popupTextRect.width() / 2,
                -popupTextRect.height() - bottomTriangleHeight - popupTopPadding * 2 - popupBottomMargin,
                +popupTextRect.width() / 2,
                +popupTopPadding - popupBottomMargin);
        return r.height();
    }

    private void drawDots(Canvas canvas) {
        Paint bigCirPaint = new Paint();
        bigCirPaint.setAntiAlias(true);
        Paint smallCirPaint = new Paint(bigCirPaint);
        smallCirPaint.setColor(Color.parseColor("#FFFFFF"));
        if (drawDotLists != null && !drawDotLists.isEmpty()) {
            for (int k = 0; k < drawDotLists.size(); k++) {
                bigCirPaint.setColor(Color.parseColor(colorArray[k % 3]));
                for (Dot dot : drawDotLists.get(k)) {
                    canvas.drawCircle(dot.x, dot.y, DOT_OUTER_CIR_RADIUS, bigCirPaint);
                    canvas.drawCircle(dot.x, dot.y, DOT_INNER_CIR_RADIUS, smallCirPaint);
                }
            }
        }
    }

    private void drawLines(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(ConvertUtil.dip2px(getContext(), 2));
        for (int k = 0; k < drawDotLists.size(); k++) {
            linePaint.setColor(Color.parseColor(colorArray[k % 3]));
            for (int i = 0; i < drawDotLists.get(k).size() - 1; i++) {
                canvas.drawLine(drawDotLists.get(k).get(i).x,
                        drawDotLists.get(k).get(i).y,
                        drawDotLists.get(k).get(i + 1).x,
                        drawDotLists.get(k).get(i + 1).y,
                        linePaint);
            }
        }
    }


    private void drawBackgroundLines(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ConvertUtil.dip2px(getContext(), 1f));
        paint.setColor(BACKGROUND_LINE_COLOR);
        PathEffect effects = new DashPathEffect(
                new float[]{10, 5, 10, 5}, 1);
        paint.setPathEffect(effects);
        if (bottomTextList != null) {
            for (int i = 0; i < bottomTextList.size(); i++) {
                canvas.drawText(bottomTextList.get(i), sideLineLength + backgroundGridWidth * i, mViewHeight - bottomTextDescent, bottomTextPaint);
            }
        }
        if (!drawDotLine) {
            //draw solid lines
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < yCoordinateList.size(); i++) {
                int dataOfAGrid = 100;
                if (dataOfAGridRight != -1) {
                    dataOfAGrid = dataOfAGridRight;
                } else if (dataOfAGirdLeft != -1) {
                    dataOfAGrid = dataOfAGirdLeft;
                }
                if ((yCoordinateList.size() - 1 - i) % dataOfAGrid == 0) {
                    canvas.drawLine(0, yCoordinateList.get(i), getWidth(), yCoordinateList.get(i), paint);
                    bottomLineY = yCoordinateList.get(i);
                    list.add(yCoordinateList.get(i));
                }
            }
            if (list.size() > 0) {
                int j = 0;
                rightScaleMap.clear();
                for (int i = list.size() - 1; i >= 0; i--) {
                    rightScaleMap.put(list.get(i), j++ * dataOfAGridRight + "");
                }
            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mViewWidth = measureWidth(widthMeasureSpec);
        mViewHeight = measureHeight(heightMeasureSpec);
        refreshAfterDataChanged();
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    private int measureWidth(int measureSpec) {
        int horizontalGridNum = getHorizontalGridNum();
        int preferred = backgroundGridWidth * horizontalGridNum + sideLineLength * 2;
        return getMeasurement(measureSpec, preferred);
    }

    private int measureHeight(int measureSpec) {
        int preferred = 0;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pointToSelect = findPointAt((int) event.getX(), (int) event.getY());
            if (pointToSelect == null) {
                barToSelect = findBarIndexAt((int) event.getX(), (int) event.getY());
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (pointToSelect != null) {
                selectedDot = pointToSelect;
                pointToSelect = null;
                postInvalidate();
            } else if (barToSelect != -1) {
                selectedBarIndex = barToSelect % bottomTextList.size();
                selectedBarListIndex = barToSelect / bottomTextList.size();
                barToSelect = -1;
                postInvalidate();
            }
        }
        return true;
    }

    private Dot findPointAt(int x, int y) {
        if (drawDotLists.isEmpty()) {
            return null;
        }
        final int width = backgroundGridWidth / 2;
        final Region r = new Region();

        for (ArrayList<Dot> data : drawDotLists) {
            for (Dot dot : data) {
                final int pointX = dot.x;
                final int pointY = dot.y;

                r.set(pointX - width, pointY - width, pointX + width, pointY + width);
                if (r.contains(x, y)) {
                    return dot;
                }
            }
        }

        return null;
    }

    private int findBarIndexAt(int x, int y) {
        if (touchBars.isEmpty()) {
            return -1;
        }
        Region r;
        for (int i = 0; i < touchBars.size(); i++) {
            r = new Region(touchBars.get(i));
            if (r.contains(x, y)) {
                return i;
            }
        }
        return -1;
    }


    class Dot {
        int x;
        int y;
        int data;
        int targetX;
        int targetY;
        int linenumber;
        int velocity = ConvertUtil.dip2px(getContext(), 18);

        Dot(int x, int y, int targetX, int targetY, Integer data, int linenumber) {
            this.x = x;
            this.y = y;
            this.linenumber = linenumber;
            setTargetData(targetX, targetY, data, linenumber);
        }

        Point setupPoint(Point point) {
            point.set(x, y);
            return point;
        }

        Dot setTargetData(int targetX, int targetY, Integer data, int linenumber) {
            this.targetX = targetX;
            this.targetY = targetY;
            this.data = data;
            this.linenumber = linenumber;
            return this;
        }

        boolean isAtRest() {
            return (x == targetX) && (y == targetY);
        }

        void update() {
            x = updateSelf(x, targetX, velocity);
            y = updateSelf(y, targetY, velocity);
        }

        private int updateSelf(int origin, int target, int velocity) {
            if (origin < target) {
                origin += velocity;
            } else if (origin > target) {
                origin -= velocity;
            }
            if (Math.abs(target - origin) < velocity) {
                origin = target;
            }
            return origin;
        }
    }

}
