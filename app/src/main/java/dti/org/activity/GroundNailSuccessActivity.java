package dti.org.activity;

import android.os.Bundle;

import dti.org.R;
import dti.org.base.BaseActivity;

public class GroundNailSuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigAtion();
        setContentView(R.layout.activity_ground_successful);
    }
}