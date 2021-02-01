package com.example.qqmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 10日 09时 47分
 * @Data： QQ右侧菜单
 * @TechnicalPoints： 布局组成，该控件下包含一个LinearLayout子控件，
 * LinearLayout下有两个视图，一个是菜单视图，一个是主视图
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class Menu_QQ extends HorizontalScrollView {

    private LinearLayout linearLayout;
    private ViewGroup mMenu;
    private ViewGroup mContent;

    private int mScreenWidth; //屏幕的宽度

    private int rightPadding = 50; //菜单距离屏幕右边的宽,初始化单位dp,需要的是px

    private int mMenuWidth; //菜单宽度

    //主视图和菜单视图配置，只允许一次！
    private Boolean call = false;

    //在XML文件中调用该方法时进行初始化
    public Menu_QQ(Context context, AttributeSet attrs) {
        super(context, attrs);
        //1.获取窗口管理器服务
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        //2.创建显示尺寸对象
        DisplayMetrics metrics = new DisplayMetrics();

        //3.获取当前屏幕的宽和高
        windowManager.getDefaultDisplay().getMetrics(metrics);

        //4.调用DisplayMetrics对象获取屏幕宽度,单位px
        mScreenWidth = metrics.widthPixels;

        //5.将右边距宽度转化为px,并转成整形
        rightPadding = (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP,rightPadding,context.getResources().getDisplayMetrics());
    }

    //重写onMeasure方法，设置滚动视图与子视图的宽和高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!call){
            //1.先获取Menu_QQ下的linearLayout布局
            linearLayout = (LinearLayout) getChildAt(0);

            //2.获取linearLayout布局下的两个子视图
            //(1).第一个是菜单视图
            mMenu = (ViewGroup)linearLayout.getChildAt(0);
            //(2).第二个是主视图，占满屏幕
            mContent = (ViewGroup)linearLayout.getChildAt(1);

            //3.设置两个子视图的宽
            //(1).菜单的宽度 = 屏幕宽度 - 右边距,并记录
            mMenuWidth = mScreenWidth - rightPadding;
            //设置
            mMenu.getLayoutParams().width = mMenuWidth;

            //(2).主视图宽度宽度等于屏幕宽度，不需要记录，设置就行
            mContent.getLayoutParams().width = mScreenWidth;

            call = true;

        }

    }

    //重写onLayout方法，在该方法中设置偏移量实现菜单隐藏,在这里让整体Menu_QQ布局右移
    //mMenuWidth个单位，从而实现隐藏菜单的效果
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //如果屏幕尺寸改变
        if(changed){
            //屏幕宽度
            this.scrollTo(mMenuWidth,0); //主显示区域向右移动mMenuWidth单位
        }
    }

    //监听手势动作，更改菜单栏位置
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            //手势抬起
            case MotionEvent.ACTION_UP:{
                int xScroll = getScrollX(); //获取当前手指的X轴坐标
                //如果滑动超过了一半
                if(xScroll>=mScreenWidth/2){
                    //将主视图右移
                    scrollTo(mMenuWidth,0);
                }
                else {
                    //只显示主视图
                    scrollTo(0,0);
                }
            }
        }
        return super.onTouchEvent(ev);
    }
}
