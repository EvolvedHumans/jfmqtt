package dti.org.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import dti.org.R;
import dti.org.base.BaseActivity;
import dti.org.databinding.ActivityFailBinding;

public class FailActivity extends BaseActivity {
    ActivityFailBinding activityFailBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFailBinding = DataBindingUtil.setContentView(this,R.layout.activity_fail);
        setTranSlucent();
    }
}