package com.example.xuchichi.mytest.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xuchichi on 2018/3/14.
 * 1.测量子控件大小，测量自身大小
 * 2.设置自身宽高
 * 3.对子view进行布局
 */

public class LableView extends ViewGroup {

    public LableView(Context context) {
        super(context);
    }

    public LableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //测量宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        int maxWidth=MeasureSpec.getSize(widthMeasureSpec)-getPaddingRight()-getPaddingLeft();

        int childCount=getChildCount();

        for (int i=0;i<childCount;i++){

           View child=getChildAt(i);

           measureChild(child,widthMeasureSpec,heightMeasureSpec);

       }

    }
    private int getwidthSize(int size,int measureSpec){

        int specMode=MeasureSpec.getMode(measureSpec);
        int specSize=MeasureSpec.getMode(measureSpec);

        int resultWidth=Math.min(size,specSize);


        if (specMode==MeasureSpec.EXACTLY){
            resultWidth=size;
        }else if (specMode==MeasureSpec.AT_MOST){
            resultWidth=size;
        }else  if (specMode==MeasureSpec.UNSPECIFIED){
            resultWidth=specSize;
        }
        return resultWidth;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
