package dti.org.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yzq.zxinglibrary.common.Constant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dti.org.R;
import dti.org.activity.choice.equip.message.map.collection.Dti;
import dti.org.activity.choice.equip.message.map.collection.camera.Camera;
import dti.org.activity.choice.equip.message.map.collection.camera.CameraAdapter;
import dti.org.activity.choice.equip.message.map.collection.camera.CameraInformation;
import dti.org.activity.choice.equip.message.map.collection.scancode.FixedInformation;
import dti.org.activity.choice.equip.message.map.collection.scancode.ScanCode;
import dti.org.activity.choice.equip.message.map.collection.scancode.ScanCodeAdapter;

/*
沟盖板设备信息采集
 */
public class CameraActivity extends AppCompatActivity {

    //扫码list
    List<ScanCode> list = new ArrayList<>();

    //拍照list
    List<Camera> cameraList = new ArrayList<>();

    //拍照适配器
    CameraAdapter cameraAdapter;

    //扫码适配器
    ScanCodeAdapter scanCodeAdapter;

    FixedInformation fixedInformation;

    CameraInformation cameraInformation;

    @BindView(R.id.recycler_view)
    RecyclerView recycler;
    @BindView(R.id.local_exit_text)
    TextView localExitText;
    @BindView(R.id.name_text)
    TextView nameText;
    @BindView(R.id.id_text)
    TextView idText;
    @BindView(R.id.button_add)
    Button buttonAdd;
    @BindView(R.id.button_add_camera)
    Button buttonAddCamera;
    @BindView(R.id.recyclerview_camera)
    RecyclerView recyclerViewCamera;
    @BindView(R.id.button_collection_finish)
    Button buttonCollectionFinish;


    /**
     * 开启拍照功能，并储存到SD卡下
     */
    /*
    点击确认进行拍照,给出跳转函数
     */
    public Uri photo(File file) {
//        //创建file对象,存储拍下来的图片
//        file = new File(context.getExternalCacheDir(),"output_image.jpg");

        //测试此抽象路径名表示的文件或目录是否存在,如果存在则删除。
        if (file.exists()) {
            file.delete();
        }
        //createNewFile()；返回值为 boolean；
        //方法介绍：当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件
        //这里不判断了，创建失败也没办法
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.e("点击确认进行拍照失败", e.getMessage());
        }

        //进行版本验证，Android7.0系统开始，直接使用本地真是路径的Uri会抛出异常，FileProvvider是
        //一种特殊的内容提供其，可将封装过的Uri对外部进行共享，提高安全性
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            //这里使用了特殊的ContentProvider类似的机制来对数据进行保护，
            //可以选择性地将封装过的Uri共享给外部，就不会出错了
            uri = FileProvider.getUriForFile
                    (this, "dti.org", file);
        } else {
            //判断设备的系统版本，若低于Android7.0则将File对象转换成Uri对象，否则调用FileProvider的
            //getUriForFile方法将File对象转换成封装过得Uri对象
            //这里先不进行版本判断，只用第一种方法
            // 把文件地址转换成Uri格式
            //这种方法在Android7.0以上报错
            uri = Uri.fromFile(file);
        }


//        //构建Intent对象，指定action,指定uri（指定存储位置）
//        /**
//         * Intent在由以下几个部分组成：
//         * 动作（action），
//         * 数据（data），
//         * 分类（Category），
//         * 类型（Type），
//         * 组件（Component），
//         * 和扩展信息（Extra）。
//         */
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
////        ((Activity)context).startActivityForResult(intent,TAKE_PHOTO);

        return uri;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate);
        ButterKnife.bind(this);

        //状态栏 @ 顶部
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//A

        //设置顶部状态栏为透明
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        //初始化扫码按钮数字
        fixedInformation = FixedInformation.getInstance();
        //拍照文件目录获取
        cameraInformation = CameraInformation.getInstance(CameraActivity.this);

        /************** 添加拍照功能 *************/
        //线性布局
        LinearLayoutManager linearLayoutManager_camera = new LinearLayoutManager(this);
        //垂直摆放
        linearLayoutManager_camera.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewCamera.setLayoutManager(linearLayoutManager_camera);
        cameraAdapter = new CameraAdapter(cameraList, CameraActivity.this);
        recyclerViewCamera.setAdapter(cameraAdapter);


        /*************** 添加扫码按钮************/
        LinearLayoutManager linearLayoutManager_scan_code = new LinearLayoutManager(this);
        linearLayoutManager_scan_code.setOrientation(LinearLayoutManager.VERTICAL);

        //cameraList.add(new Camera(null,null));

        for (int i = 0; i < 2; i++) {
            list.add(new ScanCode(fixedInformation.node_Button(),
                    fixedInformation.node_Text(fixedInformation.getFrequency(), "")
            ));
        }

        //垂直摆放
        recycler.setLayoutManager(linearLayoutManager_scan_code);
        scanCodeAdapter = new ScanCodeAdapter(list, CameraActivity.this);
        recycler.setAdapter(scanCodeAdapter);
    }


    @OnClick({R.id.button_add, R.id.button_add_camera, R.id.button_collection_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_add: {
                synchronized (this) {
                    //此处还没有扫码，所以这里应该要是空的
                    list.add(new ScanCode(fixedInformation.node_Button(),
                            fixedInformation.node_Text(fixedInformation.getFrequency(), "")
                    ));
                    scanCodeAdapter.notifyDataSetChanged();
                }
                break;
            }
            case R.id.button_add_camera: {
                //开启拍照
                //Intent intent = photo(cameraInformation.getFile());
                //startActivity(intent);

                //跳转返回Activity,调用拍照返回
//                startActivityForResult(intent, Dti.PHOTO_CODE);

                break;
            }
            case R.id.button_collection_finish:{
                //点击按钮完成，将所有采集的数据全部上传到后台
                break;
            }
        }
    }


    /**
     * 跳转结果：
     * requestCode大于0且小于等于9999，则为添加节点
     * requestCode大于10000，则为添加照片
     *
     * @param requestCode 发送的标号 0
     * @param resultCode  返回的标号
     * @param data        返回的intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Dti.RESULT_CODE_SCAN) {
            if (requestCode == Dti.PHOTO_CODE) {
                Bitmap bitmap = BitmapFactory.decodeFile(cameraInformation.getFile().getAbsolutePath());
                synchronized (this) {
                    cameraList.add(new Camera(cameraInformation.getStringFile(), bitmap));
                    cameraInformation.finish();
                    cameraAdapter.notifyDataSetChanged();
                }
            } else {
                //先获取返回的值
                assert data != null;
                String str = data.getStringExtra(Constant.CODED_CONTENT);
                synchronized (this) {
                    //返回之后更改list对应下标集合，并刷新适配器
                    //这里需要requestCode+1，原因很简单，下标+1
                    list.get(requestCode).setCode(fixedInformation.node_Text(requestCode + 1, str));
                    scanCodeAdapter.notifyDataSetChanged();
                }
            }
        }

        if (resultCode == Dti.RESULT_PHOTO) {
            //当data不为null时，往下运行
            assert data != null;
            int position = data.getIntExtra(Dti.IMG_CODE, Dti.FILE_CODE);
            synchronized (this) {
                cameraList.remove(position);
                cameraAdapter.notifyDataSetChanged();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}