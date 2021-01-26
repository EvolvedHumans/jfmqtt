package dti.org.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import dti.org.R;
import dti.org.activity.choice.equip.message.map.collection.Dti;
import dti.org.adapter.camera.CameraAdapter;
import dti.org.adapter.scancode.ScanCodeAdapter;
import dti.org.base.BaseActivity;
import dti.org.config.MapConfig;
import dti.org.dao.MapObtain;
import dti.org.databinding.ActivityGroundNailBinding;
import dti.org.presenter.GroundNailPresenter;
import dti.org.views.GroundNailView;

public class GroundNailActivity extends BaseActivity implements GroundNailView{

    ActivityGroundNailBinding activityGroundNailBinding;

    GroundNailPresenter groundNailPresenter;

    final Integer position = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigAtion();
        groundNailPresenter = new GroundNailPresenter();
        groundNailPresenter.attachView(this);
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
        activityGroundNailBinding.finish.buttonCollectionFinish.setOnClickListener(v -> {
            Intent intent1 = new Intent(GroundNailActivity.this,FailActivity.class);
            startActivity(intent1);
            finish();
        });
        activityGroundNailBinding.imageIconReturn.setOnClickListener(v -> {
            Intent intent1 = new Intent(this,MapActivity.class);
            startActivity(intent1);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        groundNailPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Dti.RESULT_CODE_SCAN && requestCode == position) {
            assert data != null;
            //刷新扫描
            groundNailPresenter.updateScancode(data.getStringExtra(Constant.CODED_CONTENT));
        }
    }

    /**
     * 绘制地钉文本框
     */
    @Override
    public void setUserTips(String userTips) {
        activityGroundNailBinding.scancode.textScanCode.setText("扫描的地钉是："+userTips);
    }

    @Override
    public void idWarning() {
        activityGroundNailBinding.scancode.listItem.setBackground(getDrawable(R.drawable.lock_err));
    }

    @Override
    public void nameWarning() {
        activityGroundNailBinding.localText.setBackground(getDrawable(R.drawable.lock_err));
    }

    @Override
    public void localWarning() {
        activityGroundNailBinding.localExitText.setBackground(getDrawable(R.drawable.lock_err));
    }


    @Override
    public MapObtain getMapContainer() {
        return null;
    }

    @Override
    public void drawScanCode(ScanCodeAdapter scanCodeAdapter) {

    }

    @Override
    public void drawCamera(CameraAdapter cameraAdapter) {

    }

    @Override
    public String wellId() {
        return null;
    }

    @Override
    public void cameraIntent(Uri uri, int requestCode) {

    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPasswordTips(String passwordTips) {

    }

    @Override
    public void jump() {

    }
}