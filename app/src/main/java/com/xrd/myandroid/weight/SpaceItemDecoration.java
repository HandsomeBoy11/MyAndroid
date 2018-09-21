package com.xrd.myandroid.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.xrd.myandroid.R;

/**
 * Created by user on 2018/9/19.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private  DisplayMetrics mDisplayMetrics;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private int mLineSpace;
    private Paint mDividerPaint=new Paint();
    private Context mContext;
    private int mLineColor;

    public SpaceItemDecoration(Context context,int left, int top, int right, int bottom, int lineSpace,int lineColor) {
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.mContext=context;
        this.mLineColor=lineColor;
        this.left = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, left, mDisplayMetrics) + 0.5f);
        this.top = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, top, mDisplayMetrics) + 0.5f);
        this.right = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, right, mDisplayMetrics) + 0.5f);
        this.bottom = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottom, mDisplayMetrics) + 0.5f);
        this.mLineSpace=(int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineSpace, mDisplayMetrics) + 0.5f);;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        if(layoutManager instanceof GridLayoutManager){
            if(position%2!=0){
                outRect.left=top;
                outRect.right=right;
            }else{
                outRect.left=left;
                outRect.right=bottom;
            }
            outRect.top=top;
            outRect.bottom=bottom;

        }else if(layoutManager instanceof LinearLayoutManager){
            if(position==0){
                outRect.top=top;
            }
            outRect.right=right;
            outRect.left=left;
            outRect.bottom=bottom;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
            int orientation = manager.getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                final int dleft = parent.getPaddingLeft()+left;
                final int dright = parent.getWidth() - parent.getPaddingRight()-right;

                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                            .getLayoutParams();
                    final int dtop = child.getBottom() + params.bottomMargin +
                            Math.round(ViewCompat.getTranslationY(child))+(int)(top/2);
                    final int dbottom = dtop + mLineSpace;
                    mDividerPaint.setColor(mContext.getResources().getColor(mLineColor));
                    c.drawRect(dleft, dtop, dright, dbottom, mDividerPaint);
                }

            }
        }

    }
}
