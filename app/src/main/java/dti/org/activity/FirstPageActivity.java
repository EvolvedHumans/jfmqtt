package dti.org.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.yangf.pub_libs.Log4j;
import com.yangf.pub_libs.jxi.ExcelUtils;
import com.yangf.pub_libs.util.FileUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dti.org.R;
import dti.org.base.BaseActivity;

/**
 * 展示界面跳转到登录界面
 */
public class FirstPageActivity extends BaseActivity {

    private final static String TAG = "dti.org.FirstPageActivity";

    Boolean timer = false;

    Timer time_login = new Timer();

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavigAtion();
        setContentView(R.layout.activity_first);

        XXPermissions.with(this)
                .permission(Permission.CAMERA)
                .permission(Permission.ACCESS_FINE_LOCATION) //获取精确位置
                .permission(Permission.ACCESS_COARSE_LOCATION) //获取粗略位置
                .permission(Permission.ACCESS_BACKGROUND_LOCATION) //在后台获取位
                .permission(Permission.ACCESS_MEDIA_LOCATION) //读取照片中的地理位置置
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            Toast.makeText(FirstPageActivity.this, "授权通过", Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Toast.makeText
                                    (FirstPageActivity.this, "部分权限未授予", Toast.LENGTH_LONG)
                                    .show();
                        }
                        timer = true;
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean never) {
                        if (never)
                            //全部授权失败，跳转到设置界面，用户手动授权
                            XXPermissions.startPermissionActivity
                                    (FirstPageActivity.this, denied);
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        time_login.schedule(new TimerTask() {
            @Override
            public void run() {
                if (timer) {
                    startActivity(new Intent(FirstPageActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 3000, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time_login.cancel();
    }
}