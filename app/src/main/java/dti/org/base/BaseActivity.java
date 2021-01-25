package dti.org.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import dti.org.config.SharedPreferenceConfig;
import dti.org.dialog.LoadDialog;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 16日 19时 16分
 * @Data： BaseActivity主要是负责实现BaseView中通用的UI逻辑方法，如此这些通用的方法就不用每个Activity都要去实现一遍了。
 * 实现功能：
 * 1.不需要刷新
 * 2.写完数据不需要再单独写apply提交
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class BaseActivity extends Activity implements BaseView {

    private LoadDialog loadDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * 设置为灰色 状态栏 @ 顶部
     */
    public void setTranSlucent() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 设置为透明 状态栏 @ 顶部
     */
    public void setNavigAtion() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    /**
     *
     * @return 读取器
     */
    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }


    /**
     *
     * @return 画笔
     */
    public SharedPreferences.Editor getEditor(){
        return editor;
    }


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDialog = new LoadDialog(this); //加载
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.apply();
    }

    @Override
    public void showLoading() {
        if (!loadDialog.isShowing()) {
            loadDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadDialog.isShowing()) {
            loadDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErr(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void importStringCache(String key, String value) {
        getEditor().putString(key, value);
    }

    @Override
    public void importIntegerCache(String key, Integer value) {
        getEditor().putInt(key,value);
    }

    @Override
    public void importBooleanCache(String key, boolean value) {
        getEditor().putBoolean(key, value);
    }

    @Override
    public String exportStringCache(String key, String value) {
        return getSharedPreferences().getString(key, value);
    }

    @Override
    public int exportIntegerCache(String key, int value) {
        return getSharedPreferences().getInt(key, value);
    }

    @Override
    public boolean exportBooleanCache(String key, boolean value) {
        return getSharedPreferences().getBoolean(key, value);
    }

    @Override
    public void clearCache() {
        getEditor().clear();
        getEditor().commit();
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }
}
