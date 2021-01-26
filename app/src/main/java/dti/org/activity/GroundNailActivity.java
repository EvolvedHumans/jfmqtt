package dti.org.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import dti.org.R;
import dti.org.activity.choice.equip.message.map.collection.Dti;
import dti.org.base.BaseActivity;
import dti.org.config.MapConfig;
import dti.org.dao.MapObtain;
import dti.org.databinding.ActivityGroundNailBinding;

public class GroundNailActivity extends BaseActivity {

    ActivityGroundNailBinding activityGroundNailBinding;

    final Integer position = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigAtion();
        activityGroundNailBinding = DataBindingUtil.setContentView(this, R.layout.activity_ground_nail);
        activityGroundNailBinding.scancode.buttonScanCode.setText("扫码地钉二维码");
        activityGroundNailBinding.scancode.buttonScanCode.setOnClickListener(v -> {
            ZxingConfig zxingConfig = new ZxingConfig();
            zxingConfig.setPlayBeep(true);
            zxingConfig.setShake(true);
            zxingConfig.setDecodeBarCode(true);
            zxingConfig.setReactColor(R.color.touming);
            zxingConfig.setFrameLineColor(R.color.touming);
            zxingConfig.setScanLineColor(R.color.colorAccent);
            zxingConfig.setFullScreenScan(true);
            Intent intent = new Intent(GroundNailActivity.this, CaptureActivity.class);
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, zxingConfig);
            ((Activity) getContext()).startActivityForResult(intent, position);
        });
        Intent intent = getIntent();
        MapObtain mapObtain = (MapObtain) intent.getSerializableExtra(MapConfig.MAP);
        activityGroundNailBinding.localText.append(mapObtain.getAddress());
        activityGroundNailBinding.localExitText.append(mapObtain.getAddress());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Dti.RESULT_CODE_SCAN && requestCode == position) {
            assert data != null;
            activityGroundNailBinding.scancode.textScanCode.setText
                    ("扫描的地钉是：" + data.getStringExtra(Constant.CODED_CONTENT));
        }
    }
}