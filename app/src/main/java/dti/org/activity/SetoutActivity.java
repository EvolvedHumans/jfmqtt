package dti.org.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.yangf.pub_libs.DensityUtil;
import com.yangf.pub_libs.image.url.UrlImageView;

import dti.org.R;

import dti.org.adapter.SetoutAdapter;
import dti.org.base.BaseActivity;

import dti.org.databinding.ActivityEquipBinding;
import dti.org.listener.SetoutPageChange;
import dti.org.presenter.SetoutPresenter;
import dti.org.views.SetoutView;

/**
 * 设备展示选择界面，跳转至设备信息选择
 */
public class SetoutActivity extends BaseActivity implements SetoutView {

    private final static String TAG = "dti.org.activity.SetoutActivity";

    SetoutPresenter setoutPresenter;

    ActivityEquipBinding activityEquipBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigAtion();
        activityEquipBinding = DataBindingUtil.setContentView(this,R.layout.activity_equip);
        setoutPresenter = new SetoutPresenter();
        setoutPresenter.attachView(this);
        activityEquipBinding.imageIconReturn.setOnClickListener(v -> {
            Intent intent = new Intent(this,ChoiceActivity.class);
            startActivity(intent);
            finish();
        });
        //todo 绘制界面
        setoutPresenter.drawSetout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setoutPresenter.detachView();
    }

    @Override
    public View toPager(String name, String picture) {
        @SuppressLint("InflateParams")
        View view = this.getLayoutInflater().inflate(R.layout.setout, null);

        TextView textView = view.findViewById(R.id.text);//文本框
        textView.setText(name);

        UrlImageView urlImageView = view.findViewById(R.id.urlImage);//刷新图片
        int imageWeight = (int) (0.6 * DensityUtil.getScreenWidth());

        //根据需求动态设置urlImageView的宽与高
        ViewGroup.LayoutParams params = urlImageView.getLayoutParams();
        params.height=imageWeight;
        params.width =imageWeight;
        urlImageView.setLayoutParams(params);

        //所需图片宽度占屏幕的总宽度的0.56
        urlImageView.setImageURL(picture, imageWeight, imageWeight);

        return view;
    }

    @Override
    public void setViewPager(SetoutAdapter setoutAdapter, SetoutPageChange setoutPageChange) {
        activityEquipBinding.viewPager.setAdapter(setoutAdapter);
        activityEquipBinding.viewPager.addOnPageChangeListener(setoutPageChange);
        //添加按钮左翻页
        activityEquipBinding.imageIconLeft.setOnClickListener
                (v -> activityEquipBinding.viewPager.arrowScroll(View.FOCUS_LEFT));
        //添加按钮右翻页
        activityEquipBinding.imageIconRight.setOnClickListener
                (v -> activityEquipBinding.viewPager.arrowScroll(View.FOCUS_RIGHT));
        activityEquipBinding.button.setOnClickListener(v -> {
            //todo 登录，跳转设备选择界面并请求下个界面信息
            setoutPresenter.login();
        });
    }

    /**
     *
     * 物理按键点击回调
     *
     * @param keyCode 按键编码
     * @param event 按键事件
     * @return 返回true表示不再响应系统动作，返回false表示继续响应系统动作
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //后退键销毁当前页面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this,ChoiceActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 导入产品type
     * @param key 键
     * @param value 值
     */
    @Override
    public void importIntegerCache(String key, Integer value) {
        getEditor().putInt(key,value);
    }


    @Override
    public void jump() {
        Intent intent = new Intent(this,DisposeActivity.class);
        startActivity(intent);
        finish();
    }
}