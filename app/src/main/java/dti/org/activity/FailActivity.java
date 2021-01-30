package dti.org.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yangf.pub_libs.Log4j;

import dti.org.R;
import dti.org.base.BaseActivity;
import dti.org.dao.Dispose;
import dti.org.databinding.ActivityFailBinding;
import dti.org.presenter.FailPresenter;
import dti.org.presenter.LoginPresenter;
import dti.org.views.FailView;

public class FailActivity extends BaseActivity implements FailView {
    private final static String TAG = "dti.org.FailActivity";
    ActivityFailBinding activityFailBinding;
    //todo presenter层，API接口
    FailPresenter failPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranSlucent();
        activityFailBinding = DataBindingUtil.setContentView(this, R.layout.activity_fail);
        failPresenter = new FailPresenter();
        failPresenter.attachView(this);
        //Toast.makeText(this, "数据导入后台功能尚未完成", Toast.LENGTH_LONG).show();
        activityFailBinding.comeButton.setOnClickListener(v -> {
            //todo 点击推送请求，如果请求成功，则继续安装
            failPresenter.retryRequest();
        });
        activityFailBinding.returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SetoutActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        failPresenter.detachView();
    }

    @Override
    public String getStringIntent(String key) {
        //根据产品类型做不同的重新请求方式
        String json = getIntent().getStringExtra(key);
        Log4j.d(TAG, json);
        return json;
    }

    @Override
    public int getIntIntent(String key) {
        return getIntent().getIntExtra(key,0);
    }

    @Override
    public void jump() {
        Intent intent = new Intent(this, DisposeActivity.class);
        startActivity(intent);
        finish();
    }

}