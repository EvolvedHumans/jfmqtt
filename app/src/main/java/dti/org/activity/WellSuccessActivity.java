package dti.org.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.KeyEvent;

import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import java.util.LinkedList;
import java.util.List;

import dti.org.R;
import dti.org.adapter.exhibition.Exhibition;
import dti.org.adapter.exhibition.ExhibitionAdapter;

import dti.org.base.BaseActivity;

import dti.org.config.DestroyActivityConfig;
import dti.org.config.WellConfig;
import dti.org.dao.WellInstall;
import dti.org.databinding.ActivityWellSuccessBinding;
import dti.org.util.DestroyActivityUtil;

public class WellSuccessActivity extends BaseActivity {

    private final static String TAG = "dti.org.activity.WellSuccessActivity";

    ActivityWellSuccessBinding activityWellSuccessBinding;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranSlucent();
        activityWellSuccessBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_well_success);

        Intent intent = getIntent();
        String data = intent.getStringExtra(WellConfig.WellImportSuccess);
        Log4j.d(TAG, data);
        //获取数数据并展示
        WellInstall wellInstall = GsonYang.JsonObject(data, WellInstall.class);
        if (wellInstall != null) {
            //数据信息
            List<Exhibition> list = new LinkedList<>();
            if (wellInstall.getName() != null) {
                list.add(new Exhibition("设备名称:",wellInstall.getName()));
            }
            if (wellInstall.getLineType() != null) {
                list.add(new Exhibition("产品类型:",wellInstall.getLineType()));
            }
            if (wellInstall.getDepartmentId() != null) {
                list.add(new Exhibition("公司id:",wellInstall.getDepartmentId()));
            }
            if (wellInstall.getUid() != null) {
                list.add(new Exhibition("工井id:",wellInstall.getUid()));
            }
            if (wellInstall.getConfig() != null) {
                list.add(new Exhibition("配置名:",wellInstall.getConfig()));
            }
            if (wellInstall.getBanzuId() != null) {
                list.add(new Exhibition("班组id:",wellInstall.getBanzuId()));
            }
            if (wellInstall.getScene() != null) {
                list.add(new Exhibition("场景名:",wellInstall.getScene()));
            }
            if (wellInstall.getOuterWell() != null) {
                list.add(new Exhibition("井盖名:",wellInstall.getOuterWell()));
            }
            if (wellInstall.getPedestal() != null) {
                list.add(new Exhibition("基座名:",wellInstall.getPedestal()));
            }
            if (wellInstall.getLockUid() != null) {
                list.add(new Exhibition("锁二维码:",wellInstall.getLockUid()));
            }
            if (wellInstall.getLockUid03()!=null){
                list.add(new Exhibition("锁03二维码:",wellInstall.getLockUid03()));
            }
            if (wellInstall.getRfid() != null) {
                list.add(new Exhibition("rfid二维码:",wellInstall.getRfid()));
            }
            if (wellInstall.getPickproofUid() != null) {
                list.add(new Exhibition("SM01二维码:",wellInstall.getPickproofUid()));
            }
            if (wellInstall.getMonitoringUid() != null) {
                list.add(new Exhibition("SM03、31、32:",wellInstall.getMonitoringUid()));
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            activityWellSuccessBinding.recyclerExhibition.setLayoutManager(layoutManager);
            activityWellSuccessBinding.recyclerExhibition.setAdapter(new ExhibitionAdapter(list));
        }


//        //图片展示
//        List<Photograph> list1 = new LinkedList<>();
//        for (int i = 0; i < 10; i++) {
//            Photograph photograph = new Photograph();
//            photograph.setBitmap(((BitmapDrawable) getResources().getDrawable(R.mipmap.cloice_equip)).getBitmap());
//            list1.add(photograph);
//        }
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        activityWellSuccessBinding.recyclerGraph.setLayoutManager(layoutManager1);
//        activityWellSuccessBinding.recyclerGraph.setAdapter(new PhotographAdapter(list1));

        activityWellSuccessBinding.comeButton.setOnClickListener(v -> {
            //todo 跳转到设备信息选择界面
            Intent intent1 = new Intent(this, DisposeActivity.class);
            startActivity(intent1);
            finish();
        });

        activityWellSuccessBinding.returnButton.setOnClickListener(v -> {
            //todo 跳转到产品信息选择界面,需要销毁上级的界面
            DestroyActivityUtil.destoryActivity(DestroyActivityConfig.DisposeClass);
            Intent intent1 = new Intent(this, SetoutActivity.class);
            startActivity(intent1);
            finish();
        });
    }

    /**
     * 物理按键点击回调
     *
     * @param keyCode 按键编码
     * @param event   按键事件
     * @return 返回true表示不再响应系统动作，返回false表示继续响应系统动作
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //后退键销毁当前页面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DestroyActivityUtil.destoryActivity(DestroyActivityConfig.DisposeClass);
            Intent intent = new Intent(this, SetoutActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}




























