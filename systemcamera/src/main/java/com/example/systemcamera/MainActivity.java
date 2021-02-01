package com.example.systemcamera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.yangf.pub_libs.DimensionImage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
调用系统相机获取图像
 */
//本实例主要使用Intent调用系统相机进行拍照然后剪裁，另外，还使用AlertDialog弹窗提示用户操作。
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int TAKE_PHOTO = 200;

    Uri uri = null;

    File file;

    TextView textView;
    ImageView imageView;
    ImageButton imageButton;

    /*
    未点击前
     */
    @SuppressLint("ResourceAsColor")
    public void unTouch(){
        textView.setText("单击按钮添加图片");
        textView.setTextColor(R.color.colorBlack);
        imageView.setBackgroundResource(R.drawable.mrkj_add_image);
        imageButton.setBackgroundResource(R.drawable.mrkj_button_false);
    }

    /*
    点击后
     */
    public void Touch(String text, Bitmap bitmap){
        if(text!=null){
            textView.setText(text);
        }
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }
        imageButton.setBackgroundResource(R.drawable.mrkj_button_true);
    }

    /*
    点击提示框
     */
    public void OnClickDialog(){
        //创建提示窗口
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否使用系统相机进行拍照？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText
                        (MainActivity.this,"单击了确认",Toast.LENGTH_SHORT)
                        .show();
                uri = Photo();
                Touch("1",null);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText
                        (MainActivity.this,"单击了取消",Toast.LENGTH_SHORT)
                        .show();
                unTouch();
            }
        });
        builder.create(); //创建窗口
        builder.show(); //显示弹窗
    }

    /*
    点击确认进行拍照
     */
    public Uri Photo(){
        //创建file对象,存储拍下来的图片
        file = new File(getExternalCacheDir(),"output_image.jpg");
        //测试此抽象路径名表示的文件或目录是否存在,如果存在则删除。
        if(file.exists()){
            file.delete();
        }
        //createNewFile()；返回值为 boolean；
        //方法介绍：当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件
        //这里不判断了，创建失败也没办法
        try {
            file.createNewFile();
        }catch (IOException e){
            Log.e("Photo",e.getMessage());
        }

        //进行版本验证，Android7.0系统开始，直接使用本地真是路径的Uri会抛出异常，FileProvvider是
        //一种特殊的内容提供其，可将封装过的Uri对外部进行共享，提高安全性
        Uri uri;
        if(Build.VERSION.SDK_INT>=24){
            //这里使用了特殊的ContentProvider类似的机制来对数据进行保护，
            //可以选择性地将封装过的Uri共享给外部，就不会出错了
            uri = FileProvider.getUriForFile
                    (MainActivity.this,"com.example.systemcamera",file);
        }
        else {
            //判断设备的系统版本，若低于Android7.0则将File对象转换成Uri对象，否则调用FileProvider的
            //getUriForFile方法将File对象转换成封装过得Uri对象
            //这里先不进行版本判断，只用第一种方法
            // 把文件地址转换成Uri格式
            //这种方法在Android7.0以上报错
            uri = Uri.fromFile(file);
        }



        //构建Intent对象，指定action,指定uri（指定存储位置）
        /**
         * Intent在由以下几个部分组成：
         * 动作（action），
         * 数据（data），
         * 分类（Category），
         * 类型（Type），
         * 组件（Component），
         * 和扩展信息（Extra）。
         */
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent,TAKE_PHOTO);

        return uri;

    }

    /*
    初始化创建
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        imageButton = findViewById(R.id.buttonPanel);

        XXPermissions.with(this)
                .permission(Permission.CAMERA)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if(all){
                            Toast.makeText
                                    (MainActivity.this,"授权通过",Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean never) {
                        if(never){
                            XXPermissions.startPermissionActivity
                                    (MainActivity.this,denied);
                        }
                    }
                });

        unTouch();

    }

    /*
    跳转返回
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //在这里只需要从文件目录下读取就可以了

        Log.e("跳转返回","拍照"+resultCode);
        //先不判断返回号码
        switch (requestCode){
            case TAKE_PHOTO:{
                if(uri!=null){
                    imageView.setImageBitmap(DimensionImage.zoomBitmap(BitmapFactory.decodeFile
                            (file.getAbsolutePath()),imageView.getWidth(),imageView.getHeight()));
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeStream
//                                (getContentResolver().openInputStream(uri));
//                        imageView.setImageBitmap(DimensionImage.zoomBitmap(bitmap,imageView.getWidth(),imageView.getHeight()));
//                    }catch (Exception e){
//                        Log.e("1","没有找到指定路径");
//                    }

                }
                break;
            }
        }

    }

    /*
        按钮点击事件
         */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPanel:{
                OnClickDialog();

                break;
            }
        }
    }

}
