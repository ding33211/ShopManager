package com.soubu.goldensteward.support.widget.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.soubu.goldensteward.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 流动布局，根据父控件尺寸，对子控件进行换行处理
 * 这里重写了addView方法，如果子视图最后一个为AutoCompleteTextView，
 * 则新的标签将插入到倒数第二位，这样可以保证最后显示的是AutoCompleteTextView，
 * 其它情况，则与默认保持一致
 */
public class FlowLayout extends ViewGroup {

    private int mMaxTagNum = -1;    //最大容纳view的数量

    public FlowLayout(Context context) {
        super(context);
        init();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 存储所有的子视图,换行记录
     */
    private List<List<View>> mAllViews = null;
    /**
     * 记录每一行最大高度
     */
    private List<Integer> mLineHeight = null;

    /**
     * 初始化
     */
    private void init() {
        mAllViews = new ArrayList<List<View>>();
        mLineHeight = new ArrayList<Integer>();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        if (cCount > 0) {
            int paddingTop = getPaddingTop();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            mAllViews.clear();
            mLineHeight.clear();

            int width = getWidth() - paddingLeft - paddingRight;
            //父控件内部右边缘，去除 padding
            int parentRight = getWidth() - paddingRight;

            int lineWidth = 0;//辅助子控件换行
            int lineHeight = 0;
            //存储每一行的子视图
            List<View> lineViews = new ArrayList<View>();
            View childView = null;
            MarginLayoutParams lp = null;
            int childWidth;
            int childHeight;
            LayoutParams params = null;
            for (int index = 0; index < cCount; index++) {
                childView = getChildAt(index);
                if (childView.getVisibility() != View.GONE) {
                    childWidth = childView.getMeasuredWidth();
                    childHeight = childView.getMeasuredHeight();
                    params = childView.getLayoutParams();
                    if (params != null && (params instanceof MarginLayoutParams)) {
                        lp = (MarginLayoutParams) params;
                        childWidth += lp.leftMargin + lp.rightMargin;
                        childHeight += lp.topMargin + lp.bottomMargin;
                    }

                    if ((lineWidth + childWidth) > width) {//需要换行
                        mLineHeight.add(lineHeight);
                        mAllViews.add(lineViews);
                        lineWidth = 0;//重置行宽
                        lineHeight = 0;//重置行高
                        lineViews = new ArrayList<View>();
                    }
                    lineWidth += childWidth;
                    lineHeight = Math.max(childHeight, lineHeight);
                    lineViews.add(childView);
                }
            }
            mLineHeight.add(lineHeight);
            mAllViews.add(lineViews);

            int left = paddingLeft;
            int top = paddingTop;
            int lineNums = mAllViews.size();
            int lineViewSize = 0;
            int leftside, topside, rightside, bottomside;//子视图左上角与右下角坐标
            lp = null;
            for (int index = 0; index < lineNums; index++) {
                lineViews = mAllViews.get(index);
                lineHeight = mLineHeight.get(index);
                lineViewSize = lineViews.size();
                for (int j = 0; j < lineViewSize; j++) {
                    childView = lineViews.get(j);
                    if (childView.getVisibility() != View.GONE) {
                        leftside = left;
                        params = childView.getLayoutParams();
                        if (params != null && (params instanceof MarginLayoutParams)) {
                            lp = (MarginLayoutParams) params;
                            leftside += lp.leftMargin;
                        }
                        //让子视图居中显示
                        topside = top + (lineHeight - childView.getMeasuredHeight()) / 2;
                        rightside = leftside + childView.getMeasuredWidth();
                        if (lp != null && rightside > parentRight - lp.rightMargin) {
                            rightside = parentRight - lp.rightMargin;
                        }
                        bottomside = topside + childView.getMeasuredHeight();
                        childView.layout(leftside, topside, rightside, bottomside);
                        if (lp == null) {
                            left = rightside;
                        } else {
                            left = rightside + lp.rightMargin;
                        }

                    }
                }
                left = paddingLeft;
                top += lineHeight;
            }
        }
    }

    /**
     * 交换两个指定的View的位置，
     */
    public void changeBetweenViews(int position) {
        int cCount = getChildCount();
        if (position < cCount && position > -1) {
            View firstView = getChildAt(position);
            removeView(firstView);
            addView(firstView);
        }
    }

    @Override
    public void addView(View child) {
        int childCount = getChildCount();
        if (childCount > 0) {
            View view = getChildAt(childCount - 1);
            if (view instanceof AutoCompleteTextView) {//如果最后一个为AutoCompleteTextView，则在倒数第二个插入新标签
                if (childCount == mMaxTagNum) {
                    super.removeViewAt(mMaxTagNum - 1);
                }
                super.addView(child, childCount - 1);
            } else {
                super.addView(child);
            }
        } else {
            super.addView(child);
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        //这里只支持margin
        return new MarginLayoutParams(getContext(), attrs);
//		return super.generateLayoutParams(attrs);
    }

    public void setMaxTagNum(int num) {
        mMaxTagNum = num;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取得父容器为子视图设置的测量模式和大小
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight;

        int width = 0;
        int height = 0;
        //记录每一行宽度，width不断取最大值
        int lineWidth = 0;
        //记录每一行高度，height不断累加
        int lineHeight = 0;
        //子视图个数
        int cCount = getChildCount();
        View childView = null;
        MarginLayoutParams lp = null;
        int childWidth = 0;
        int childHeight = 0;
        for (int index = 0; index < cCount; index++) {
            childView = getChildAt(index);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            LayoutParams params = childView.getLayoutParams();
            if (childView.getLayoutParams() != null && params instanceof MarginLayoutParams) {
                lp = (MarginLayoutParams) params;
                childWidth += lp.leftMargin + lp.rightMargin;
                childHeight += lp.topMargin + lp.bottomMargin;
            }
            //如果新加入的子视图，使得该行超出父控件最大长度，则需要把该子视换行显示
            if ((lineWidth + childWidth) > sizeWidth) {//需要换行
                width = Math.max(lineWidth, childWidth);
                lineWidth = childWidth;//换行	,行宽重置
                height += lineHeight;//换行，高度累加
                lineHeight = childHeight;
            } else {//不需要换行
                lineWidth += childWidth;
                lineHeight = Math.max(childHeight, lineHeight);
            }
        }
        //最后一行的宽后与之前得到的最大宽度相比
        width = Math.max(width, lineWidth) + paddingLeft + paddingRight;
        //前面采用是累积高度不包括最后一行
        height += lineHeight + paddingBottom + paddingTop;
        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));

    }
}
