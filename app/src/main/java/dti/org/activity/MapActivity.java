package dti.org.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import androidx.databinding.DataBindingUtil;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.CameraUpdateFactory;
import com.yangf.pub_libs.Log4j;

import org.jetbrains.annotations.NotNull;

import dti.org.R;
import dti.org.base.BaseActivity;
import dti.org.config.MapConfig;
import dti.org.config.SharedPreferenceConfig;
import dti.org.dao.MapObtain;
import dti.org.databinding.ActivityMapBinding;
import dti.org.listener.AMapLoactionChange;
import dti.org.util.MapUtil;

public class MapActivity extends BaseActivity {

    ActivityMapBinding activityMapBinding;

    //地图定位
    AMapLocationClient locationClient;

    //定位监听
    AMapLoactionChange loactionChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranSlucent();
        activityMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        activityMapBinding.map.onCreate(savedInstanceState); //创建地图
        activityMapBinding.address.setSingleLine(false); //允许换行
        activityMapBinding.address.setMovementMethod(ScrollingMovementMethod.getInstance()); //上下滑动

        //定位监听，并将地图容器交由监听类控制
        loactionChange = new AMapLoactionChange(this, activityMapBinding);

        locationClient = new AMapLocationClient(this);  //获取定位API
        locationClient.setLocationOption(MapUtil.getAMapLocationClientOption()); //定位参数入参
        locationClient.setLocationListener(loactionChange); //定位监听开启
        locationClient.startLocation(); //开启定位

        activityMapBinding.point.setOnClickListener(v -> {
            //地图回调到上次定位位置
            loactionChange.getAMap().moveCamera
                    (CameraUpdateFactory.newLatLngZoom(loactionChange.getLatLng(), 19));
        });

        activityMapBinding.button.setOnClickListener(v -> {
            //TODO 假设此产品是智能井盖。获取产品Type，获取对应NB产品Type
            int baseType = getSharedPreferences().getInt(SharedPreferenceConfig.Setout_TYPE, SharedPreferenceConfig.TYPE_NO);

            //todo 假设该产品是智能井盖，获取智能井盖下配置对应NB模块页面
            int type = getSharedPreferences().getInt(SharedPreferenceConfig.NB_Type, SharedPreferenceConfig.TYPE_NO);

            //baseType->1 智能井盖
            //type->1 跳转智能井盖，->2 跳转地钉
            if (type != SharedPreferenceConfig.TYPE_NO) {
                MapObtain mapObtain = new MapObtain();
                mapObtain.setLongitude(activityMapBinding.longitude.getText().toString());
                mapObtain.setLatitude(activityMapBinding.latitude.getText().toString());
                mapObtain.setAddress(activityMapBinding.address.getText().toString());
                mapObtain.setType(type);

                if (baseType == 1) {
                    if (baseType != SharedPreferenceConfig.TYPE_NO) {
                        mapObtain.setBaseType(baseType); //智能井盖，有配置选项
                        Log4j.d("配置信息", mapObtain.toString());
                        Intent intent = new Intent(this, WellActivity.class);
                        intent.putExtra(MapConfig.MAP, mapObtain);
                        startActivity(intent);
                        finish();
                    }
                } else if (baseType == 2) {
                    Log4j.d("配置信息", mapObtain.toString());
                    Intent intent = new Intent(this,GroundNailActivity.class);
                    intent.putExtra(MapConfig.MAP, mapObtain);
                    startActivity(intent);
                    finish();
                }
            } else {
                showErr("缓存被清理，无产品信息");
            }
        });

        activityMapBinding.imageIconReturn.setOnClickListener(v -> {
            Intent intent = new Intent(this, DisposeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /*
    切入前台
     */
    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制地图
        activityMapBinding.map.onResume();
    }

    /*
    切出前台
     */
    @Override
    protected void onPause() {
        super.onPause();
        //暂停绘制地图
        activityMapBinding.map.onResume();
    }

    /*
    意外情况销毁或切出
     */
    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //意外情况，保存当前页面状态
        activityMapBinding.map.onSaveInstanceState(outState);
    }

    /*
    销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁定位客户端
        locationClient.onDestroy();
        //销毁地图
        activityMapBinding.map.onDestroy();
    }

}