package dti.org.listener;

import androidx.viewpager.widget.ViewPager;

import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 13日 12时 28分
 * @Data： Equip界面View视图改变监听
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class SetoutPageChange implements ViewPager.OnPageChangeListener {

    private int position = 0;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
