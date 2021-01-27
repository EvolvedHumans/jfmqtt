package dti.org.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dti.org.R;

public class ChoiceActivity extends AppCompatActivity {

    @BindView(R.id.user)
    ImageView user;

    @BindView(R.id.setout)
    ImageView setout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //后退键销毁当前页面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.user, R.id.setout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user: {
                startActivity(new Intent(ChoiceActivity.this, LoginOutActivity.class));
                finish();
                break;
            }

            case R.id.setout: {
                startActivity(new Intent(ChoiceActivity.this, SetoutActivity.class));
                finish();
                break;
            }

        }
    }
}