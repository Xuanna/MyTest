package com.example.xuchichi.mytest.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchichi on 2018/9/2.
 * 根据自身空间大小，确定子view的MerasurSpec，
 * 如果子view的宽度大于试图宽度则换行
 * 高度则为每行最高的view相加
 */

public class FlowLayout extends ViewGroup {

    //存储所有子View
    private List<List<View>> mAllChildViews = new ArrayList<>();
    //每一行的高度
    private List<Integer> mLineHeight = new ArrayList<>();


    public FlowLayout(Context context) {
        super(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 对ViewGrop进行测量，首先要拿到ViewGrop的尺寸，以及测量模式
     * 先测量子view，获取viewgroup的宽高，设置宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //获取测量的模式和尺寸大小

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //记录ViewGroup真实的测量宽高

        int measureWidth = 0;
        int measureHeight = 0;

        int currentLineWidth = 0;
        int currentLineHeight = 0;

        int count = getChildCount();

        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);

//            child.measure(widthMeasureSpec, heightMeasureSpec);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;

            int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            if (currentLineWidth + childWidth > widthSize) {//换行
                //对比得到最大的宽度
                measureWidth = Math.max(currentLineWidth, measureWidth);
                //重置currentLineWidth
                currentLineWidth = childWidth;

                measureHeight += currentLineHeight;

                //记录行高
                currentLineHeight = childHeight;


            } else {
                currentLineWidth += childWidth;
                currentLineHeight = Math.max(currentLineHeight, childHeight);
            }
            if (i == count) {
                measureWidth = Math.max(currentLineWidth, measureWidth);
                measureHeight += currentLineHeight;
            }

        }
        Log.e("", "measureWidth:" + measureWidth);
        Log.e("", "measureHeight:" + measureHeight);
        setMeasuredDimension(
                widthMode == MeasureSpec.EXACTLY ? widthSize : measureWidth,
                heightMode == MeasureSpec.EXACTLY ? heightSize : measureHeight
        );


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllChildViews.clear();
        mLineHeight.clear();
        //获取当前ViewGroup的宽度
        int width = getWidth();
//
        int lineWidth = 0;
        int lineHeight = 0;
        //记录当前行的view
        List<View> lineViews = new ArrayList<View>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width) {
                //记录LineHeight
                mLineHeight.add(lineHeight);
                //记录当前行的Views
                mAllChildViews.add(lineViews);
                //重置行的宽高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                //重置view的集合
                lineViews = new ArrayList();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllChildViews.add(lineViews);
//
        //设置子View的位置
        int left = 0;
        int top = 0;
        //获取行数
        int lineCount = mAllChildViews.size();
        for (int i = 0; i < lineCount; i++) {
            //当前行的views和高度
            lineViews = mAllChildViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                //判断是否显示
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cLeft = left + lp.leftMargin;
                int cTop = top + lp.topMargin;
                int cRight = cLeft + child.getMeasuredWidth();
                int cBottom = cTop + child.getMeasuredHeight();
                //进行子View进行布局
                child.layout(cLeft, cTop, cRight, cBottom);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    /**
     * （控件与控件之间的距离外边距margin）
     * 在原生的viewgroup里是不支持margin的，需要让viewgroup去认识这个标签
     * viewgroup里有两个内部类分别是
     * ViewGroup.LayoutParams和ViewGroup.MarginLayoutParams
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
