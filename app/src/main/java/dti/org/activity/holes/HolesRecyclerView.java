package dti.org.activity.holes;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 17日 19时 13分
 * @Data： 自定义孔洞布局
 * @ RecyclerView自定义控件,孔洞的宽高都是固定的，按照10/9的格式
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class HolesRecyclerView extends RecyclerView {

//    Integer column;
//
//    public void setColumn(Integer column) {
//        this.column = column;
//    }

    public HolesRecyclerView(@NonNull Context context) {
        super(context);
    }

    public HolesRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    对HolesRecyclerView的宽高进行修改
     */
//    @Override
//    protected void onMeasure(int widthSpec, int heightSpec) {
//        if(column!=null){
//            setMeasuredDimension(HolesProperties.mHOLES_WIDTH(column),HolesProperties.dpToPx_HOLES_WIDTH());
//        }
//
//        else {
//            setMeasuredDimension(widthSpec,heightSpec);
//        }
//
//    }
}
