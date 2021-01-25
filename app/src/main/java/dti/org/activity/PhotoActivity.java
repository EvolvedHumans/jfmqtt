package dti.org.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yangf.pub_libs.DimensionImage;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dti.org.R;
import dti.org.activity.choice.equip.message.map.collection.Dti;
import dti.org.databinding.ActivityPhotoBinding;

/*
这个界面是专门用来接收图片展示的，只能作为临时跳转界面
 */
public class PhotoActivity extends AppCompatActivity {

    //文件地址
    File file;

    //对应编号
    Integer code;

    //跳转函数
    Intent intent;

    ActivityPhotoBinding activityPhotoBinding;

    /*
    跳转函数Intent
     */
    @SuppressLint("SetTextI18n")
    public void init() {

        String fileName;

        //跳转函数
        intent = getIntent();

        //接收上一个界面的返回值,有两个需要接收的参数
        //1.文件名
        fileName = intent.getStringExtra(Dti.FILE_NAME);
        //2.文件编号
        code = intent.getIntExtra(Dti.IMG_CODE, Dti.FILE_CODE);


        if (!code.equals(Dti.FILE_CODE)) {
            //标题栏
            activityPhotoBinding.photo.text.setText("照片" + (Integer) (code + 1));

            //线程获取高和框，如果直接获取的话,高和宽都会等于零，因为图片还没绘画完毕
            /**
             * 原理：
             * （1）View.post(Runnable) 内部会自动分两种情况处理，
             * 当 View 还没 attachedToWindow 时，会先将这些 Runnable 操作缓存下来；
             * 否则就直接通过 mAttachInfo.mHandler 将这些 Runnable 操作 post 到主线程的 MessageQueue 中等待执行。
             *
             * （2）如果 View.post(Runnable) 的 Runnable 操作被缓存下来了，
             * 那么这些操作将会在 dispatchAttachedToWindow() 被回调时，
             * 通过 mAttachInfo.mHandler.post() 发送到主线程的 MessageQueue 中等待执行。
             */

            if (file == null) {
                //图片地址
                file = new File(getExternalCacheDir().getAbsolutePath(), fileName);

                //获取图片Bitmap
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                activityPhotoBinding.photo.imageIcon.post(() -> {
                    int width = activityPhotoBinding.photo.imageIcon.getWidth();
                    int height = activityPhotoBinding.photo.imageIcon.getHeight();
                    activityPhotoBinding.photo.imageIcon.setImageBitmap(DimensionImage.zoomBitmap(bitmap, width, height));
                });

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPhotoBinding = DataBindingUtil.setContentView(this,R.layout.activity_photo);
        init();

    }

    //todo 退回按键，退出并销毁页面。 删除按键，删除底目录下的文件，并跳转修改recyclerView
    @OnClick({R.id.image_icon_return, R.id.image_icon_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_icon_return: {
                //点击退出返回
                finish();
            }
            break;

            case R.id.image_icon_delete: {
                //点击删除返回并刷新CollectionActivity，且删除对应路径的图片
                //删除对应路径的文件
                if (file != null) {
                    //测试此抽象路径名表示的文件或目录是否存在,如果存在则删除。
                    if (file.exists()) {
                        file.delete();
                    }
                }
                setResult(Dti.RESULT_PHOTO,intent);
                finish();
            }
            break;
        }
    }
}