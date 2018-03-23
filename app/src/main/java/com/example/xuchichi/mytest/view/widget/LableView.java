package com.example.xuchichi.mytest.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchichi on 2018/3/14.
 * 1.测量子控件大小，测量自身大小
 * 2.设置自身宽高
 * 3.对子view进行布局
 */

public class LableView extends ViewGroup {
    /**
     * new的时候调用
     *
     * @param context
     */
    public LableView(Context context) {
        super(context, null);
    }

    /**
     * 在xml中，没使用自定义属性时调用该方法
     *
     * @param context
     * @param attrs
     */
    public LableView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    /**
     * 在xml中，使用自定义属性时调用该方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */

    public LableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量子view的宽和高 为子view设置测量规格确定其宽高
     * 设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //wart_content时控件的宽高
        int width = 0;
        int height = 0;

        int lineWidth = 0;//记录每一行的宽度与高度
        int lineHeight = 0;

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子view占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //子view的宽度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //换行
            if (lineWidth + childHeight > sizeWidth) {
                //对比得到最大的宽度
                width = Math.max(lineWidth, width);
                //重置lineWidth
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;

                lineHeight = childHeight;

            } else {//未换行
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);//得到当前行最大的高度
            }
            if (i == count) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(modeWidth == MeasureSpec.AT_MOST ? width : sizeWidth, modeHeight == MeasureSpec.AT_MOST ? height : sizeHeight);
    }

    /**
     * 存储所有的view，一行一行的存储
     */
    private List<List<View>> mAllViews = new ArrayList<>();
    /**
     * 每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<>();

    /**
     * 确定子view的位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        //当前viergroup的宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            /**
             * 如果需要换行
             */
            if (childHeight+lineWidth+lp.leftMargin+lp.rightMargin>width){
                //记录lineHeight
                mLineHeight.add(lineHeight);
                //记录当前行的view
                mAllViews.add(lineViews);
                //重置行宽和行高
                lineWidth=0;
                lineHeight=childHeight+lp.topMargin+lp.bottomMargin;
                lineViews=new ArrayList<>();
            }else{
                lineWidth+=childWidth+lp.leftMargin+lp.rightMargin;
                lineHeight=Math.max(lineHeight,childHeight);
                lineViews.add(child);
            }

        }//for end
        /**
         * 处理最后一行
         */
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);
        /**
         * 设置子view的位置
         */
        int left;
        int top;
        int lineNumber=mAllViews.size();
        for (int i = 0; i < lineNumber; i++) {
           View view= lineViews.get(i);
           lineHeight=mLineHeight.get(i);
        }


    }

    /**
     * 与当前viewgroup对应的LayoutParams
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
