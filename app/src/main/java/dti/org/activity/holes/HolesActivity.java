package dti.org.activity.holes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dti.org.R;


public class HolesActivity extends AppCompatActivity {

    //位置信息
    @BindView(R.id.local_exit_text)
    TextView localExitText;
    //设备名称
    @BindView(R.id.name_text)
    TextView nameText;
    //网关按钮
    @BindView(R.id.button_scan_code)
    Button buttonScanCode;
    //网关节点文本框
    @BindView(R.id.text_scan_code)
    TextView textScanCode;
    @BindView(R.id.holes_recycler)
    RecyclerView holesRecycler;

    //创建适配器
    HolesAdapter holesAdapter;

    //每个控件最大列数
    Integer column;

    //孔洞集合
    List<HolesData> list = new ArrayList<>();

    public void init() {

        //状态栏 @ 顶部
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//A

        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        buttonScanCode.setText("扫描网关二维码");
        textScanCode.setText(HolesConfig.TEXT_CODE_SCAN);

        holesRecycler.post(() -> {

            column = 6;

            holesRecycler.setMinimumHeight(HolesProperties.dpToPx_HOLES_WIDTH());
            holesRecycler.setMinimumWidth(HolesProperties.mHOLES_WIDTH(column));

            for (int i = 0; i < 100; i++) {
                list.add(new HolesData(i, R.drawable.holes_bg_null));
            }

            //添加数据适配器
            if (holesAdapter == null) {
                holesAdapter = new HolesAdapter(list, this);
            }

            //网格流布局,一排最多十个
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, column);

            //向孔洞控件中添加网格布局样式
            holesRecycler.setLayoutManager(gridLayoutManager);

            holesRecycler.addItemDecoration(new HolesItemDecoration(column));

            holesRecycler.setAdapter(holesAdapter);

        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_holes);
        ButterKnife.bind(this);
        init();
    }

    @OnClick(R.id.button_scan_code)
    public void onViewClicked() {
    }

    /*
    拍照返回值获取
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //确定是拍照的返回回调
        if (requestCode == HolesConfig.RESULT_CODE_SCAN) {
            //获取拍照参数,先获取返回的值
            assert data != null;
            String str = data.getStringExtra(Constant.CODED_CONTENT);

            //TODO 这个地方需要每获取一个参数都需要经过后台校验,后台校验通过则变为绿色,否则变为红色
            //这里应该需要加个判断:


        }


        super.onActivityResult(requestCode, resultCode, data);

    }


}