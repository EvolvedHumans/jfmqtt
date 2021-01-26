package dti.org.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import dti.org.R;
import dti.org.base.BaseActivity;
import dti.org.databinding.ActivityFailBinding;

public class FailActivity extends BaseActivity {
    ActivityFailBinding activityFailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFailBinding = DataBindingUtil.setContentView(this, R.layout.activity_fail);
        setTranSlucent();
        Toast.makeText(this, "数据导入后台功能尚未完成", Toast.LENGTH_LONG).show();
        activityFailBinding.comeButton.setOnClickListener(v -> {
            Toast.makeText(this, "数据导入后台功能尚未完成", Toast.LENGTH_LONG).show();
        });
        activityFailBinding.returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, DisposeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}