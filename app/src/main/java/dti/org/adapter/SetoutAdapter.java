package dti.org.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2020年 11月 29日 23时 49分
 * @Data： ViewPager适配器
 * @TechnicalPoints： 改写PagerAdapter的方法, 加载ImageView和TextView
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@AllArgsConstructor
public class SetoutAdapter extends PagerAdapter {

    private List<View> list;

    @Override
    public int getCount() {
        return list.size();
    }

    //用于判断pager的一个view是否和instantiateItem方法返回的object是否相关联
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //初始化添加视图
        container.addView(list.get(position));
        return list.get(position);
    }

    /**
     * 异常完整信息：java.lang.UnsupportedOperationException: Required method destroyItem was not overridden
     * <p>
     * 异常原因：在使用ViewPager滑动到最后一个子View的时候出现此异常。
     * <p>
     * 解决方法：在PagerAdapter的destroyItem()方法中移除当前的View对象，代码如下
     *
     * @Override
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //移除视图
        if (list.get(position) != null) {
            container.removeView(list.get(position));
        }
    }
}
