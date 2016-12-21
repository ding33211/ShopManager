package com.soubu.goldensteward.support.widget.recyclerviewdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.soubu.goldensteward.R;

/**
 * recyclerView分割线
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDrawable;
    private static final int DEFAULT_ORIENTATION = LinearLayoutManager.VERTICAL;
    private int mOrientation;
    private int mHeight;
    //是否需要画最后一条分割线
    private boolean mNeedDrawLastOne = true;

    public DividerItemDecoration(Context context, int orientation, int height) {
        this(context, orientation, height, R.drawable.divider_home_recyclerview, true);
    }

    /**
     * 自定义recyclerView分割线
     *
     * @param context
     * @param orientation
     * @param height      定义分割线高度
     */
    public DividerItemDecoration(Context context, int orientation, int height, boolean needDrawLastOne) {
        this(context, orientation, height, R.drawable.divider_home_recyclerview, needDrawLastOne);
    }

    /**
     * 自定义recyclerView分割线
     *
     * @param context
     * @param orientation
     * @param height      定义分割线高度
     */
    public DividerItemDecoration(Context context, int orientation, int height, int drawableRes, boolean needDrawLastOne) {
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL) {
            this.mOrientation = DEFAULT_ORIENTATION;
        } else {
            this.mOrientation = orientation;
        }
        mDrawable = context.getResources().getDrawable(drawableRes);
        mHeight = height;
        mNeedDrawLastOne = needDrawLastOne;
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
        int childCount = parent.getChildCount() - 1;
        if (mNeedDrawLastOne) {
            childCount++;
        }
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
        int childCount = parent.getChildCount() - 1;
        if (mNeedDrawLastOne) {
            childCount++;
        }
        //最后一根分割线不画
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
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
        int position = parent.getChildAdapterPosition(view);
        if (!mNeedDrawLastOne && position == parent.getAdapter().getItemCount() - 1) {
            return;
        }
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mHeight);
        } else {
            outRect.set(0, 0, mHeight, 0);
        }
    }
}
