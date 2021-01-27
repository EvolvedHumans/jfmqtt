package dti.org.listener;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.yangf.pub_libs.DensityUtil;

import dti.org.R;
import dti.org.databinding.ActivityMapBinding;
import dti.org.util.MapUtil;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 17日 11时 52分
 * @Data： 拉动地图到当前定位点，并绘制标记点图案
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class AMapLoactionChange implements AMapLocationListener {

    ActivityMapBinding activityMapBinding;

    volatile AMap aMap;

    Context context;

    LatLng latLng;

    Marker mGPSMarker;//中心点坐标

    GeocodeSearch geocodeSearch; //逆地理编译包

    public AMapLoactionChange(Context context, ActivityMapBinding activityIMapBinding) {
        this.context = context;
        this.aMap = activityIMapBinding.map.getMap();
        this.activityMapBinding = activityIMapBinding;
        MapUtil.getUiSettings(getAMap());//设置地图容器样式
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

                //获取屏幕中心点像素
                setMarket(cameraPosition.target, DensityUtil.getScreenWidth() / 2, (int) getMapHeight());
                activityIMapBinding.longitude.setText(String.valueOf(cameraPosition.target.longitude));
                activityIMapBinding.latitude.setText(String.valueOf(cameraPosition.target.latitude));
                //   activityIMapBinding.address.setText();
                //逆地理编码获取
                getAddressByLatlng(cameraPosition.target);
            }
        });
        geocodeSearch = new GeocodeSearch(getContext());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
                String formatAddress = regeocodeAddress.getFormatAddress();
                activityIMapBinding.address.setText(formatAddress);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {

                setLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));

                //设置定位坐标和缩放比例
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLatLng(), 19));

                //描点
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(getLatLng());
                markerOptions.visible(true);
                //设置图标
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                        (getContext().getResources(), R.mipmap.location))).anchor(0.5f, 0.7f);
                aMap.addMarker(markerOptions);
            }
        }
    }


    private void getAddressByLatlng(LatLng latLng) {
        //逆地理编码查询条件：逆地理编码查询的地理坐标点、查询范围、坐标类型。
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500f, GeocodeSearch.AMAP);
        //异步查询
        geocodeSearch.getFromLocationAsyn(query);
    }


    /**
     * 获取地图中心点坐标点高度单位px
     */
    private double getMapHeight() {
        float height = DensityUtil.getScreenWidth() - DensityUtil.spToPx(12) - DensityUtil.dpToPx(20);
        return 0.29 * height;
    }


    /**
     * 通过拖动地图进行位置定位改变，且mark位于屏幕中间不变
     *
     * @param latLng 中心点坐标
     * @param width  所处屏幕的横向位置
     * @param height 所处屏幕的纵向位置
     */
    private void setMarket(LatLng latLng, int width, int height) {
        if (mGPSMarker != null) {
            mGPSMarker.remove();
        }

        MarkerOptions markOptions = new MarkerOptions();
        markOptions.draggable(true);//设置Marker可拖动
        markOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                (getContext().getResources(), R.mipmap.coordinate)));
        //设置一个角标
        mGPSMarker = aMap.addMarker(markOptions);
        //设置marker在屏幕的像素坐标
        mGPSMarker.setPosition(latLng);

//        mGPSMarker.setTitle(topic);  //设置标题
//        mGPSMarker.setSnippet("拖动地图精确定位"); //设置图标
        //设置像素坐标
        mGPSMarker.setPositionByPixels(width, height);

        mGPSMarker.showInfoWindow();

        //mGPSMarker.setRotateAngle();
//        //关闭infowindow显示
//        if (!TextUtils.isEmpty(content))
//        {
//            mGPSMarker.showInfoWindow();
//        }

        activityMapBinding.map.invalidate();
    }

}
