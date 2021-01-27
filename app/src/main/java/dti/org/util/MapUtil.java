package dti.org.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;

import dti.org.R;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 17日 12时 59分
 * @Data： 地图工具类
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class MapUtil {

    /**
     * 控件交互
     */
    public static void getUiSettings(AMap aMap) {
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false); //缩放按钮隐藏
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        uiSettings.setLogoBottomMargin(-150); //图标隐藏
    }

    /**
     * 定位参数获取
     *
     * @return 自定义定位参数，API接口获取
     */
    public static AMapLocationClientOption getAMapLocationClientOption() {
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption(); //设置定位参数
        locationClientOption.setOnceLocation(true); //设置单次定位
//        locationClientOption.setOnceLocationLatest(true); //返回启动定位时，3s内精度最高的一次定位结果
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy); //高精度定位
        return locationClientOption;
    }

    /**
     * 定位蓝点模式获取
     *
     * @param context 上下文
     * @return 自定义定位蓝点模式，API接口获取
     */
    public static MyLocationStyle getMyLocationStyle(Context context) {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(context.getResources(), R.mipmap.location)
                )
        );
        //精度圆圈自定义,去掉边框圆
        //1.定位蓝点精度圆圈的边框颜色的方法
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        //2.定位蓝点精度圆圈圈的填充颜色的方法
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));

        return myLocationStyle;
    }
}
