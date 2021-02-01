package com.example.fileinfo;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

/*
保存信息到SD卡
 */
//本实例主要使用了File类中的File()方法和FileOutputStream类中的FileOutStream()方法，FileInputStream类
//中的FileInputStream()方法、FileInputStream类中的FileInputStream方法、AlertDialog类中的Builder()方法。
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String FILENAME = "filedemo";

    TextView text;
    EditText edit;
    Button buttonPanel;

    File file = null;

    /**
     * 将信息存储到SD卡上,无法实现异步
     * 不同操作系统可能会出现乱码情况，客户端和服务端统一格式UTF-8
     * @File 文件路径
     * @Byte[] 字节流
     */
    public synchronized boolean write(File file,byte[] bytes){
        //先判断是否有SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //确保file不为null
            if(file!=null){
                try {
                    //写入文件流
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(bytes);
                    //全部写完
                    outputStream.flush();
                    //关闭写入流
                    outputStream.close();
                    return true;
                }catch (Exception e){
                    //Objects.requireNonNull 判断一个对象为null
                    Log.e("SD卡->write错误原因", Objects.requireNonNull(e.getMessage()));
                }

            }
        }
        return false;
    }

    /**
     * 读取存储在SD卡上的信息，无法实现异步
     * 可能出现乱码情况，需要保持读、存数据格式的一致
     * @File 文件路径
     */
    public synchronized byte[] read(File file){
        //先判断是否有SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //确保file不为null
            if(file!=null){
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] bytes = new byte[inputStream.available()];
                    //读取文件流
                    inputStream.read(bytes);
                    //关闭读取流
                    inputStream.close();
                    return bytes;
                }catch (Exception e){
                    //读取失败
                    //Objects.requireNonNull 判断一个对象为null
                    Log.e("SD卡->read错误原因", Objects.requireNonNull(e.getMessage()));
                }
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        edit = findViewById(R.id.edit);
        buttonPanel = findViewById(R.id.buttonPanel);

        //获取权限
        XXPermissions.with(this)
                .permission(Permission.READ_EXTERNAL_STORAGE)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.REQUEST_INSTALL_PACKAGES)
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPanel:{
                //初始化文件 在根目录创建目录为filedemo的txt文件,
                //Environment.getExternalStorageDirectory()获取sd卡目录
                if(file == null){
                    file = new File(Environment.getExternalStorageDirectory(),FILENAME);
                }

                if(!edit.getText().equals("")){
                   if(write(file,edit.getText().toString().getBytes())){
                       Toast.makeText
                               (MainActivity.this,"存储成功",Toast.LENGTH_SHORT)
                               .show();
                   }
                }
                break;
            }
            case R.id.buttonPanell:{
                if(file == null){
                    file = new File(Environment.getExternalStorageDirectory(),FILENAME);
                }
                byte[] bytes = read(file);

                if(bytes!=null){
                    Log.e("1","读取了"+new String(bytes));
                }
                break;
            }
        }
    }
}
