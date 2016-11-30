package com.soubu.goldensteward.widget.recyclerviewdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dingsigang on 16-11-28.
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    private int mSizeGridSpacingPx;
    private int mGridSize;

    private boolean mNeedLeftSpacing = false;

    public DividerGridItemDecoration(int gridSpacingPx, int gridSize) {
        mSizeGridSpacingPx = gridSpacingPx;
        mGridSize = gridSize;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int frameWidth = (int) ((parent.getWidth() - (float) mSizeGridSpacingPx * (mGridSize - 1)) / mGridSize);
        int padding = parent.getWidth() / mGridSize - frameWidth;
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        //由于项目需求，暂时改为top也有间距
//        if (itemPosition < mGridSize) {
//            outRect.top = 0;
//        } else {
        outRect.top = mSizeGridSpacingPx;
//        }
        if (itemPosition % mGridSize == 0) {
            outRect.left = 0;
            outRect.right = padding;
            mNeedLeftSpacing = true;
        } else if ((itemPosition + 1) % mGridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.right = 0;
            outRect.left = padding;
        } else if (mNeedLeftSpacing) {
            mNeedLeftSpacing = false;
            outRect.left = mSizeGridSpacingPx - padding;
            if ((itemPosition + 2) % mGridSize == 0) {
                outRect.right = mSizeGridSpacingPx - padding;
            } else {
                outRect.right = mSizeGridSpacingPx / 2;
            }
        } else if ((itemPosition + 2) % mGridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.left = mSizeGridSpacingPx / 2;
            outRect.right = mSizeGridSpacingPx - padding;
        } else {
            mNeedLeftSpacing = false;
            outRect.left = mSizeGridSpacingPx / 2;
            outRect.right = mSizeGridSpacingPx / 2;
        }
        //项目需求，底部也改为padding
        outRect.bottom = mSizeGridSpacingPx;
    }
}
