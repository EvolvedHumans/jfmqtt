package dti.org.activity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

import com.yangf.pub_libs.Log4j;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import dti.org.R;
import dti.org.adapter.camera.CameraAdapter;
import dti.org.adapter.scancode.ScanCodeAdapter;
import dti.org.base.BaseActivity;
import dti.org.config.CameraConfig;
import dti.org.config.MapConfig;
import dti.org.dao.MapObtain;
import dti.org.databinding.ActivityGroundNailBinding;
import dti.org.dialog.GroundNailDialog;
import dti.org.presenter.GroundNailPresenter;
import dti.org.views.GroundNailView;

public class GroundNailActivity extends BaseActivity implements GroundNailView {

    private final static String TAG = "dti.org.GroundNailActivity";

    ActivityGroundNailBinding activityGroundNailBinding;

    GroundNailPresenter groundNailPresenter;

    //ExportDialog exportDialog;
    GroundNailDialog groundNailDialog;

    final Integer position = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranSlucent();
        groundNailPresenter = new GroundNailPresenter();
        groundNailPresenter.attachView(this);
        //exportDialog = new ExportDialog(this);
        groundNailDialog = new GroundNailDialog(this);
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
//        Intent intent = getIntent();
//        MapObtain mapObtain = (MapObtain) intent.getSerializableExtra(MapConfig.MAP);
//        assert mapObtain != null;
        groundNailPresenter.drawGroundNail();
        activityGroundNailBinding.finish.buttonCollectionFinish.setOnClickListener(v -> {
            groundNailPresenter.clickInstall();
          //  finish();
//            Intent intent1 = new Intent(GroundNailActivity.this, FailActivity.class);
//            startActivity(intent1);
//            finish();
        });
        activityGroundNailBinding.imageIconReturn.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MapActivity.class);
            startActivity(intent1);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        groundNailPresenter.detachView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //后退键销毁当前页面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == CameraConfig.RESULT_Well_OK && requestCode == position) {
            assert data != null;
            //刷新扫描
            groundNailPresenter.updateScancode(data.getStringExtra(Constant.CODED_CONTENT));
        }
    }

    /**
     * 绘制地钉文本框
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void setUserTips(String userTips) {
        Log4j.d(TAG, userTips);
        activityGroundNailBinding.scancode.textScanCode.setText("扫描的地钉是：" + userTips);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void idWarning() {
        activityGroundNailBinding.scancode.listItem.setBackground(getDrawable(R.drawable.lock_err));
    }

    /**
     * 设置正常颜色
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void scancodeNormal() {
        activityGroundNailBinding.scancode.listItem.setBackground(getDrawable(R.drawable.lock_ok));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void nameWarning() {
        activityGroundNailBinding.localText.setBackground(getDrawable(R.drawable.lock_err));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void localWarning() {
        activityGroundNailBinding.localExitText.setBackground(getDrawable(R.drawable.lock_err));
    }

    /**
     * json格式转化类 -> GroundNailInstall
     *
     * @param key
     * @param json
     */
    @Override
    public void installSuccessful(String key, String json) {
        Log4j.d(key, json);
        Intent intent = new Intent(this, GroundNailSuccessActivity.class);
        intent.putExtra(key, json);
        startActivity(intent);
        finish();
    }

    /**
     * json格式转化类 -> GroundNailInstall
     *
     * @param key
     * @param json
     */
    @Override
    public void installFailed(String key, String json) {
        Log4j.e(key, json);
        Intent intent = new Intent(this, FailActivity.class);
        intent.putExtra(key, json);
        startActivity(intent);
        finish();
    }


    /**
     * @return 地图端数据获取
     */
    @Override
    public MapObtain getMapContainer() {
        return (MapObtain) getIntent().getSerializableExtra(MapConfig.MAP);
    }

    /**
     * @param userName 设置位置信息
     */
    @Override
    public void setUserName(String userName) {
        activityGroundNailBinding.localText.append(userName);
    }

    /**
     * @param passwordTips 设置设备名称
     */
    @Override
    public void setPasswordTips(String passwordTips) {
        activityGroundNailBinding.localExitText.append(passwordTips);
    }

    /**
     * 显示弹窗
     *
     * @param title    标题
     * @param resource 弹窗内容
     */
    @Override
    public void showExportPopup(String title, String resource) {

        groundNailDialog.setTitle(title);
        groundNailDialog.setContent(resource);

        if (!groundNailDialog.isShowing()) {
            groundNailDialog.show();
        }

        groundNailDialog.setOnClickProceedListener(v -> {
            //允许重复安装，直接覆盖
            groundNailPresenter.overlap();
            //groundNailPresenter
            groundNailDialog.dismiss();
        });

        groundNailDialog.setOnClickCloseListener(v -> {
            //关闭当前提示
            groundNailDialog.dismiss();
        });
    }

    @Override
    public void drawScanCode(ScanCodeAdapter scanCodeAdapter) {

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
    public void jump() {

    }


}