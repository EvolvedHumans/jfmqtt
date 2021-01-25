package dti.org.activity.choice.equip.message.map.collection.camera;

import android.content.Context;

import com.yangf.pub_libs.Date;

import java.io.File;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 15日 14时 57分
 * @Data： CameraInformation 配置信息
 * @TechnicalPoints：
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class CameraInformation {

    public static CameraInformation cameraInformation;

    private Context context;

    private Integer fr;

    public CameraInformation(Context context){
        fr = 0;
        this.context = context;
    }

    public synchronized static CameraInformation getInstance(Context context){
        if(cameraInformation == null){
            cameraInformation = new CameraInformation(context);
        }
        return cameraInformation;
    }

    //文件名
    public String getStringFile(){
        return fr+".jpg";
    }

    //完整文件
    public File getFile(){
        return new File(context.getExternalCacheDir(), fr+".jpg");
    }

    public void finish(){
        ++fr;
    }
}
