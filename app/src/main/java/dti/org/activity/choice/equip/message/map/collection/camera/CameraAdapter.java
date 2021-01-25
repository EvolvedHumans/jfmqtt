package dti.org.activity.choice.equip.message.map.collection.camera;

import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.yangf.pub_libs.DimensionImage;

import java.util.List;

import dti.org.R;
import dti.org.activity.PhotoActivity;
import dti.org.activity.choice.equip.message.map.collection.Dti;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 15日 15时 08分
 * @Data： 添加拍照数据适配器
 * 疑点问题:
 * 有人问我RecyclerView 为什么item和头布局或者脚布局不居中，都在屏幕的左边。
 *
 * 然后解决了这个问题，把解决方式写下：
 *
 * 以头布局为例：很多时候获取布局后，直接添加到RecyclerView中
 *
 * inflate = LayoutInflater.from(getContext()).inflate(R.layout.activity_headceshi, null);
 *
 * 这样写的话，就会出现不居中 ，原因i是因为inflater在inflate一个xml时，需要知道parent的类型，才能生成对应的LayoutParams，才可以把xml根节点的attrs（如layout_width）读进去，代码如下：
 *
 * inflate = LayoutInflater.from(getContext()).inflate(R.layout.activity_headceshi, dui_listview,false);这样写就可以解决不居中的问题。
 *
 * 如果parent传进去为null，生成的View的LayoutParams为null，在RecyclerView.addView时，发现LayoutParams为null，则生成默认的LayoutParams。
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.VH> {

    public static class VH extends RecyclerView.ViewHolder {

        ImageButton imageButton;

        public VH(@NonNull View itemView) {
            super(itemView);
            //写入内部布局，这里仅仅只是获取布局id
            imageButton = itemView.findViewById(R.id.image);
        }
    }

    private List<Camera> list;

    private Context context;

    private CameraInformation cameraInformation;

//    private int mScreenWidth; //屏幕宽度 单位px
//
//    private int mPadding = 50; //照片距离屏幕右边\左边的宽,初始化单位dp,需要的是px
//
//    private int imageWidth; //图片宽度 单位px
//
//    private int imageHeight; //图片高度 单位px

    public CameraAdapter(List<Camera> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /*
    初始化布局
     */
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //items布局获取和items控件获取
        //item元素不居中显示的原因:
        // 是因为inflater在inflate一个xml时，需要知道parent的类型，
        // 才能生成对应的LayoutParams，才可以把xml根节点的attrs（如layout_width）读进去，代码如下：
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.camera_image,parent,false);
        VH vh = new VH(view);

        /**
         * 相片的长度和高度安装系统屏幕尺寸进行计算
         */
        /**
         * 宽度:距离屏幕50dp*2并转化成px单位，宽度的1/3
         */


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        //imagebutton的高度按照获取到的width宽度进行动态的变化，高/宽 = 4/5
        holder.imageButton.post(() -> {
            //获取宽度
            Integer width = holder.imageButton.getWidth();
            Integer height = 7*width/10;

            //高度根据高度自定义适配
            Bitmap bitmap = DimensionImage.zoomBitmap(list.get(position).getBitmap(),width,height);

            //截取一部分图片
            holder.imageButton.setImageBitmap(bitmap);

            //截取一部分图片，并加载到对应位置
            holder.imageButton.setImageBitmap(bitmap);

        });

//        //判断当前是否需要贴图,true需要贴图
//        // 截图 Bitmap
//        Bitmap bitmap = DimensionImage.zoomBitmap(
//                list.get(position).getBitmap(),
//                holder.imageButton.getMaxWidth(),
//                holder.imageButton.getMaxHeight()
//        );


        holder.imageButton.setOnClickListener(v -> {
            //1.点击按钮，跳转界面
            Intent intent = new Intent(context, PhotoActivity.class);
            //2.传递文件名
            intent.putExtra(Dti.FILE_NAME,list.get(position).getFileName());
            //3.传递文件编号，那么接到响应编号后需要+1
            intent.putExtra(Dti.IMG_CODE,position);
            //4.跳转返回,postion是对应照片序列号
            ((AppCompatActivity) context).startActivityForResult(intent,Dti.PHOTO_CODE);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
