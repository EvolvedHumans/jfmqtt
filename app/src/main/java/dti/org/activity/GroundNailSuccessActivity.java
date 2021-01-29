package dti.org.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.yangf.pub_libs.GsonYang;

import dti.org.R;
import dti.org.base.BaseActivity;
import dti.org.config.DestroyActivityConfig;
import dti.org.config.GroundNailConfig;
import dti.org.dao.GroundNailInstall;
import dti.org.databinding.ActivityGroundSuccessfulBinding;
import dti.org.util.DestroyActivityUtil;

public class GroundNailSuccessActivity extends BaseActivity {

    ActivityGroundSuccessfulBinding activityGroundSuccessfulBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranSlucent();
        activityGroundSuccessfulBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_ground_successful);
        //获取道钉界面的数据
        Intent intent = getIntent();
        String groundnail = intent.getStringExtra(GroundNailConfig.GroundNailImportSuccess);
        assert groundnail != null;
        if (GsonYang.IsJson(groundnail)) {
            GroundNailInstall groundNailInstall = GsonYang.JsonObject(groundnail, GroundNailInstall.class);
            if (groundNailInstall != null) {
                String lineType = groundNailInstall.getLineType(); //产品类型
                String stakeUid = groundNailInstall.getStakeUid(); //扫码获取
                String banzuId = groundNailInstall.getBanzuId(); //班组id
                // String sceneType = String.valueOf(groundNailInstall.getSceneType()); //安装场景
                String scene = groundNailInstall.getScene();
                String local = groundNailInstall.getName(); //位置信息
                String name = groundNailInstall.getName(); //产品名称

                if (lineType != null && stakeUid != null && banzuId != null) {
                    //视图刷新展示
                    activityGroundSuccessfulBinding.localText.append(local);
                    activityGroundSuccessfulBinding.nameText.append(name);
                    activityGroundSuccessfulBinding.banzu.append(banzuId);
                    activityGroundSuccessfulBinding.scence.append(scene);
                }
            }
        }
        activityGroundSuccessfulBinding.comeButton.setOnClickListener(v -> {
            //todo 跳转到设备信息选择界面
            Intent intent1 = new Intent(this, DisposeActivity.class);
            startActivity(intent1);
            finish();
        });

        activityGroundSuccessfulBinding.returnButton.setOnClickListener(v -> {
            //todo 跳转到产品信息选择界面,需要销毁上级的界面
            DestroyActivityUtil.destoryActivity(DestroyActivityConfig.DisposeClass);
            Intent intent1 = new Intent(this, SetoutActivity.class);
            startActivity(intent1);
            finish();
        });
    }
}