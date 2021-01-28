package dti.org.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yangf.pub_libs.Log4j;

import java.util.LinkedList;
import java.util.List;

import dti.org.R;
import dti.org.adapter.DisposeAdapter;
import dti.org.base.BaseActivity;

import dti.org.dao.Dispose;
import dti.org.dao.DisposeGroup;
import dti.org.databinding.ActivityDisposeBinding;

import dti.org.item.DisposeItemDecoration;
import dti.org.presenter.DisposePresenter;
import dti.org.views.DisposeView;
import lombok.SneakyThrows;


/**
 * 设备信息选择界面
 */
public class DisposeActivity extends BaseActivity implements DisposeView {

    private final static String TAG = "dti.org.activity.DisposeActivity";

    ActivityDisposeBinding activityDisposeBinding;
    DisposePresenter disposePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranSlucent();
        activityDisposeBinding = DataBindingUtil.setContentView(this, R.layout.activity_dispose);
        activityDisposeBinding.buttonIntent.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            // finish();
        });
        activityDisposeBinding.imageIconReturn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SetoutActivity.class);
            startActivity(intent);
            finish();
        });
        disposePresenter = new DisposePresenter();
        disposePresenter.attachView(this);
        disposePresenter.clearHistory();
        disposePresenter.drawDispose();
        disposePresenter.lockMechanism();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposePresenter.release();
        disposePresenter.detachView();
        //销毁
        disposePresenter = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //后退键销毁当前页面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, SetoutActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param disposeAdapter        适配器
     * @param disposeItemDecoration item位置布局
     */
    @Override
    public void setButtonGroup(DisposeAdapter disposeAdapter, DisposeItemDecoration disposeItemDecoration) {
        if (disposePresenter.isViewAttached()) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            activityDisposeBinding.recyclerView.setLayoutManager(linearLayoutManager);
            activityDisposeBinding.recyclerView.addItemDecoration(disposeItemDecoration);
            activityDisposeBinding.recyclerView.setAdapter(disposeAdapter);
        }
    }


    @Override
    public void startOptional() {
        if (disposePresenter.isViewAttached()) {
            activityDisposeBinding.buttonIntent.setEnabled(true);
            activityDisposeBinding.buttonIntent.setBackgroundResource(R.drawable.dispose_check);
        }
    }

}