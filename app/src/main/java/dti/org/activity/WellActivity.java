package dti.org.activity;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;

import com.yangf.pub_libs.Log4j;
import com.yzq.zxinglibrary.common.Constant;

import dti.org.R;

import dti.org.adapter.camera.CameraAdapter;
import dti.org.base.BaseActivity;
import dti.org.config.CameraConfig;
import dti.org.config.MapConfig;
import dti.org.dao.MapObtain;

import dti.org.databinding.ActivityWellBinding;
import dti.org.presenter.WellPresenter;
import dti.org.views.WellView;

/**
 * 1.智能井盖
 * BaseType -> 2
 * <p>
 * 2.智能井盖下配置设备
 * Type1 -> 1 锁
 * Type2 ->锁+SM32
 * Type3 ->锁+SM03
 * Type4 ->锁+SM01
 * Type5 ->锁+SM31
 * Type6 ->SM32
 * Type7 ->锁+SM03+锁+SM01
 */
public class WellActivity extends BaseActivity implements WellView {

    ActivityWellBinding activityWellBinding;
    WellPresenter wellPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranSlucent();
        activityWellBinding = DataBindingUtil.setContentView(this, R.layout.activity_well);
        activityWellBinding.camera.buttonAddCamera.setOnClickListener(v -> {
            //todo 开始拍照
            wellPresenter.startCamera();
        });
        wellPresenter = new WellPresenter();
        wellPresenter.attachView(this);
        wellPresenter.drawWell();//todo 填充适配器，刷新视图层
        //沟盖板需要建立该分支
//        //todo 添加节点网关
//        activityWellBinding.scancode.buttonAdd.setOnClickListener(v -> {
//            wellPresenter.addScanCode();
//        });
        activityWellBinding.finish.buttonCollectionFinish.setOnClickListener(v -> {
            Intent intent = new Intent(this, FailActivity.class);
            startActivity(intent);
        });
        activityWellBinding.title.imageIconReturn.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, MapActivity.class);
            startActivity(intent1);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wellPresenter.detachView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //后退键销毁当前页面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent1 = new Intent(this, MapActivity.class);
            startActivity(intent1);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 跳转结果：
     * requestCode大于0且小于等于9999，则为添加节点
     * requestCode大于10000，则为添加照片
     *
     * @param requestCode 发送的标号 0
     * @param resultCode  返回的标号 当返回标号为 1时，则添加节点
     * @param data        返回的intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log4j.e("int requestCode", String.valueOf(requestCode));
        Log4j.e("int resultCode", String.valueOf(resultCode));
        //扫码成功后的回调
        if (resultCode == CameraConfig.RESULT_Well_OK) {
            //todo 1.改写节点网关内容
            if (requestCode >= 0 && requestCode <= 100) {
                assert data != null;
                wellPresenter.updateScanCode(data.getStringExtra(Constant.CODED_CONTENT), requestCode);
            }
            //todo 2.添加照片
            if (requestCode == CameraConfig.PHOTO_REQUEST_CODE) {
                //todo 拍照功能无法获取uri，需要手动设置
                wellPresenter.addPhoto();
            }
            //todo 3.删除照片
            if (requestCode == CameraConfig.PHOTO_Activity) {
                Log4j.d("删除照片", "CameraConfig.PHOTO_Activity");
                assert data != null;
                int integer = data.getIntExtra(CameraConfig.PHOTO_DELETE, CameraConfig.PHOTO_Activity);
//                Intent intent = getIntent();
//                Integer integer = intent.getIntExtra(CameraConfig.PHOTO_DELETE,CameraConfig.PHOTO_Activity);
                Log4j.d("获取type", String.valueOf(integer));
                if (integer != CameraConfig.PHOTO_Activity) {
                    //todo 删除对应照片
                    Log4j.d("删除对应照片", "CameraConfig.PHOTO_Activity");
                    wellPresenter.removeCamera(integer);
                }
            }
        }
    }

    @Override
    public MapObtain getMapContainer() {
        //获取上一个activity中传过来的intent
        Intent intent = getIntent();
        Log4j.d("返回过来的信息", ((MapObtain) intent.getSerializableExtra(MapConfig.MAP)).toString());
        return (MapObtain) intent.getSerializableExtra(MapConfig.MAP);
    }

    @Override
    public void drawScanCode(dti.org.adapter.scancode.ScanCodeAdapter scanCodeAdapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityWellBinding.scancode.recyclerView.setLayoutManager(layoutManager);
        activityWellBinding.scancode.recyclerView.setAdapter(scanCodeAdapter);
    }

    @Override
    public void drawCamera(CameraAdapter cameraAdapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        activityWellBinding.camera.recyclerviewCamera.setLayoutManager(layoutManager);
        activityWellBinding.camera.recyclerviewCamera.setAdapter(cameraAdapter);
    }

    /**
     * 改写为写入设备名
     *
     * @param userTips 设置用户区域提示内容
     */
    @Override
    public void setUserTips(String userTips) {
        activityWellBinding.title.nameText.setText(userTips);
    }

    /**
     * 改写为写入当前位置
     *
     * @param passwordTips 设置密码区域提示内容
     */
    @Override
    public void setPasswordTips(String passwordTips) {
        activityWellBinding.title.localExitText.setText(passwordTips);
    }

    /**
     * @return 工井ID获取
     */
    @Override
    public String wellId() {
        return activityWellBinding.title.idText.getText().toString();
    }

    /**
     * @return 获取设备名
     */
    @Override
    public String getUserName() {
        return activityWellBinding.title.nameText.getText().toString();
    }

    /**
     * @return 获取当前位置
     */
    @Override
    public String getPassword() {
        return activityWellBinding.title.localExitText.getText().toString();
    }

    /**
     * 工井ID警告
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void idWarning() {
        activityWellBinding.title.localLayout.setBackground(getDrawable(R.drawable.lock_err));
    }

    /**
     * 设备名称警告
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void nameWarning() {
        activityWellBinding.title.nameLayout.setBackground(getDrawable(R.drawable.lock_err));
    }

    /**
     * 位置信息警告
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void localWarning() {
        activityWellBinding.title.localLayout.setBackground(getDrawable(R.drawable.lock_err));
    }

    /**
     * @param uri 入参
     */
    @Override
    public void cameraIntent(Uri uri, int request) {
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //不指定uri路径，则返回不会为null
        ((Activity) getContext()).startActivityForResult(intent, request);
    }

    @Override
    public void jump() {

    }

}