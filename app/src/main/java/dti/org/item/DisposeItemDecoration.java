package dti.org.item;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangf.pub_libs.DensityUtil;
import com.yangf.pub_libs.Log4j;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 15日 12时 45分
 * @Data： 继承RecyclerView.ItemDecoration
 * 修改 Dispose界面Recycle自定义子控件位置
 * 特点：所有子控件默认使用一种比例宽、高，保持队列统一形式
 * 入参单位：DP
 * 实际设置：PX
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class DisposeItemDecoration extends RecyclerView.ItemDecoration {

    private int start;
    private int end;
    private int top;
    private int buttom;

    public DisposeItemDecoration(int start,int end,int top,int buttom){
        this.start = start;
        this.end = end;
        this.top = top;
        this.buttom = buttom;
    }

    public int getStart() {
        return DensityUtil.dpToPx(start);
    }

    public int getEnd() {
        return DensityUtil.dpToPx(end);
    }

    public int getTop() {
        return DensityUtil.dpToPx(top);
    }

    public int getButtom() {
        return DensityUtil.dpToPx(buttom);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = getButtom();
        outRect.top = getTop();
        outRect.right = getEnd();
        outRect.left = getStart();
    }
}


















