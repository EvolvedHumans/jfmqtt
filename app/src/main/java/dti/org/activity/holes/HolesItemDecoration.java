package dti.org.activity.holes;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 17日 23时 29分
 * @Data： 继承RecyclerView.ItemDecoration方法，设置item间距
 * 1.原RecyclerView使用网格布局，这里需要获取网格数，进行动态适配items间距
 * 2.需要获取items的宽度，动态设置item的高度。高度 = 宽度
 * 所有的长度单位统一为px（像素）
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */

public class HolesItemDecoration extends HolesRecyclerView.ItemDecoration {


    /*
    每行有多少个item
     */
    private int mColumnItem;

    /**
     * 获取设置各部分间距，已知条件:
     * 1.孔洞布局的宽、高相等
     * 2.上边距等于下边距
     * 3.已经每行有多少个items
     *
     * item上边距 = (13/292) * 孔洞布局高度
     * item下边距 = item上边距
     * 中间item的左右边距 = item上边距、下边距
     * 第一个item的左边距 = 12/292 *  孔洞布局高度
     * 最后一个item的右边距 = 12/292 * 孔洞布局高度
     * 第一个item的左边距 = 最后一个item的边距
     *
     *
     */
    public HolesItemDecoration(int mColumnItem){

        //每行item数量
        this.mColumnItem = mColumnItem;

    }





    /**
     * ItemDecoration 允许应用给具体的View添加具体的图画或者layout的偏移，
     * 对于绘制View之间的分割线，视觉分组边界等等是非常有用的。
     * 所有的ItemDecorations按照被添加的顺序在itemview之前（如果通过重写`onDraw()`）
     * 或者itemview之后,如果通过重写 `onDrawOver(Canvas, RecyclerView, RecyclerView.State)`）绘制。
     * parent.getChildPosition(view)方法:获取view在Adapter中的position。
     *
     *
     *
     * @param outRect 设置边距
     * @param view item视图获取
     * @param parent 父容器parent(RecyclerView)
     * @param state ！！！未知
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //1.获取当前item->view 对应的position
        int position = parent.getChildAdapterPosition(view);

        //2.根据行数计算当前view所处的列数
        int column = position%mColumnItem;

        /**
         * 设置各个items边距
         */
        // 1.每行一个item边距设置
        if(column == 1){{
            //(1).左边距设置
            outRect.left = HolesProperties.ITEM_PADDING;

            //(2).右边距设置
            outRect.right = HolesProperties.ITEMS_CENTER_PADDING;

            //(3).上边距设置
            outRect.top = HolesProperties.ITEMS_CENTER_PADDING;

            //(4).下边距设置
            outRect.bottom = HolesProperties.ITEMS_CENTER_PADDING;
        }}

        //2.每行最后一个item边距设置
        else if(column == 0){
            //(1).左边距设置
            outRect.left = HolesProperties.ITEMS_CENTER_PADDING;

            //(2).右边距设置
            outRect.right = HolesProperties.ITEM_PADDING;

            //(3).上边距设置
            outRect.top = HolesProperties.ITEMS_CENTER_PADDING;

            //(4).下边距设置
            outRect.bottom = HolesProperties.ITEMS_CENTER_PADDING;
        }

        //3.每行中间item边距设置
        else {
            //(1).左边距设置
            outRect.left = HolesProperties.ITEMS_CENTER_PADDING;

            //(2).右边距设置
            outRect.right = HolesProperties.ITEMS_CENTER_PADDING;

            //(3).上边距设置
            outRect.top = HolesProperties.ITEMS_CENTER_PADDING;

            //(4).下边距设置
            outRect.bottom = HolesProperties.ITEMS_CENTER_PADDING;
        }


    }
}
