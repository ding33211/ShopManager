package com.soubu.goldensteward.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soubu.goldensteward.R;

/**
 *  recyclerView分割线
 *
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDrawable;
    private static final int DEFAULT_ORIENTATION = LinearLayoutManager.VERTICAL;
    private int mOrientation;
    private int mHeight;

    /**
     * 自定义recyclerView分割线
     * @param context
     * @param orientation
     * @param height  定义分割线高度
     */
    public DividerItemDecoration(Context context, int orientation, int height) {
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL) {
            this.mOrientation = DEFAULT_ORIENTATION;
        } else {
            this.mOrientation = orientation;
        }
        mDrawable = context.getResources().getDrawable(R.drawable.divider_home_recyclerview);
        mHeight = height;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontal(c, parent);
        } else {
            drawVertical(c, parent);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View chileView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) chileView.getLayoutParams();

            int left = chileView.getRight() + params.rightMargin;
            int right = left + mHeight;
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);

        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        //最后一根分割线不画
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mHeight;
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDrawable.getIntrinsicWidth(), 0);
        }
    }
}
