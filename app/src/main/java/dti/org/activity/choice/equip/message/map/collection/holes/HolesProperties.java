package dti.org.activity.choice.equip.message.map.collection.holes;

import com.yangf.pub_libs.DensityUtil;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 17日 23时 45分
 * @Data：孔洞布局属性
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class HolesProperties {

    //（1）屏幕的宽度
    public static final Integer SCREEN_WIDTH = DensityUtil.getScreenWidth();

    //（2）孔洞布局两边的边距。单位(dp)
    public static final Integer HOLES_MAGIN = 28;

    //（3）孔洞布局中按钮的宽、高。单位(sp)
    public static final Integer BUTTON_ITEM = 50;

    //（4）首个、尾个孔洞按钮的边距。单位(dp)
    public static final Integer ITEM_PADDING = 50;

    //（5）中间的孔洞按钮的边距,包括所有孔洞按钮的上边距和下边距。单位(dp)
    public static final Integer ITEMS_CENTER_PADDING = 36;


    /**
     * （2）HOLES_MAGIN 孔洞布局两边的边距。单位(DP)
     * <p>
     * 转化后
     *
     * @return 返回孔洞布局两边的边距，单位(px)
     */
    public static final Integer dpToPx_HOLES_MAGIN() {
        return DensityUtil.dpToPx(HOLES_MAGIN);
    }


    /**
     * （3）BUTTON_ITEM 孔洞布局中按钮的宽、高。单位(dp)。
     * <p>
     * 转化后
     *
     * @return 返回孔洞布局中按钮的宽、高，单位(px)
     */
    public static final Integer spToPx_BUTTON_ITEM() {
        return DensityUtil.spToPx(BUTTON_ITEM);
    }


    /**
     * （5）ITEM_PADDING中间的孔洞按钮的边距,包括所有孔洞按钮的上边距和下边距。单位(dp)
     * <p>
     * 转化后
     *
     * @return 返回中间的孔洞按钮的边距, 包括所有孔洞按钮的上边距和下边距。，单位(px)
     */
    public static final Integer dpToPx_ITEM_PADDING() {
        return DensityUtil.dpToPx(ITEM_PADDING);
    }


    /**
     * （5）ITEMS_CENTER_PADDING中间的孔洞按钮的边距,包括所有孔洞按钮的上边距和下边距。单位(dp)
     * <p>
     * 转化后
     *
     * @return 返回中间的孔洞按钮的边距, 包括所有孔洞按钮的上边距和下边距，单位(px)
     */
    public static final Integer dpToPx_ITEMS_CENTER_PADDING() {
        return DensityUtil.dpToPx(ITEMS_CENTER_PADDING);
    }


    /**
     * 孔洞布局的最小宽度
     * <p>
     * <p>
     * 都是以PX单位进行换算的
     *
     * @return 返回孔洞布局的宽度，单位(px)
     */
    public static final Integer dpToPx_HOLES_WIDTH() {
        return SCREEN_WIDTH - 2 * dpToPx_HOLES_MAGIN();
    }


    /**
     * 孔洞布局的实时变化宽度,跟最大的列数有关,不随屏幕分辨率变化
     * <p>
     * 假设有五列孔洞：
     * 1.五列孔洞中间有四个间隙
     * 2.五列孔洞中有两个边距间隙
     * 3.不管它有多少孔洞边距间隙只有两个！
     * <p>
     * 假设有N列孔洞
     * 得到Holes宽度计算公式为:
     * (N-1)*按钮中间间隙+N*按钮宽度+2*边距宽度
     * <p>
     * 都是以PX单位进行换算的
     * <p>
     * 形式参数:当前孔洞列数
     *
     * @return 返回孔洞布局的宽度，单位(px)
     */
    public static final Integer dpToPx_HOLES_WIDTH_CHANGE(Integer column) {
        return (column - 1) * dpToPx_ITEMS_CENTER_PADDING() + column * spToPx_BUTTON_ITEM() + 2 * dpToPx_ITEM_PADDING();
    }


    /**
     * 使用孔洞布局的宽度判断！
     * （1）.当dpToPx_HOLES_WIDTH_CHANGE()方法得到的值小于dpToPx_HOLES_WIDTH()
     * 采用dpToPx_HOLES_WIDTH()方法
     * <p>
     * （2）.否则！
     * 采用dpToPx_HOLES_WIDTH_CHANGE()来得到宽度
     *
     * @return 最终所使用的孔洞布局，返回Integet类型参数
     */
    public static final Integer mHOLES_WIDTH(Integer column) {

        //自由变化宽度
        Integer a = dpToPx_HOLES_WIDTH_CHANGE(column);

        //最小宽度
        Integer b = dpToPx_HOLES_WIDTH();

        if (a < b) {
            return b;
        } else {
            return a;
        }

    }


}
