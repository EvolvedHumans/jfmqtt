package dti.org.model;

import com.yangf.pub_libs.Log4j;

import java.util.HashMap;

import dti.org.base.BaseCallbcak;
import dti.org.updater.AppUpdater;
import dti.org.updater.net.INetCallBack;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 14时 07分
 * @Data： 智能井盖Model数据层
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class WellModel {
    /**
     * 1.锁二维码
     * 2.检测03或32二维码使用状态二维码接口
     * 3.检测03和32有无使用过接口
     * 4.检测RFID是否被使用
     * 返回JSON字符串
     */
    public static void isTesting(String url,BaseCallbcak<String> callbcak){
        Log4j.d("完整地址",url);
        AppUpdater.getInstance().getOKHttpNetManager().getExecute(url, new INetCallBack() {
            @Override
            public void success(String content) {
                Log4j.d("校验后数据",content);
                callbcak.onSuccess(content);
            }

            @Override
            public void failed(Throwable throwable) {
                callbcak.onError(throwable);
            }

            @Override
            public void onComplete() {
                callbcak.onComplete();
            }
        });
    }

    /**
     * 设备数据录入
     * 智能井盖各类型数据安装接口
     * 入参方式:JSON
     * 请求方式:post
     * configType字段传递根据设备选择信息界面对应Type
     * Type -》 1 ，锁
     * Type -》 2 ，锁+SM32
     * Type -》 3 ，锁+SM03
     * Type -》 4 ，锁+SM01
     * Type -》 5 ，锁+SM31
     * Type -》 6 ，SM32
     * Type -》 7 ，锁+SM03+锁+SM01
     * 入参字段：经度、纬度
     * 返回JSON字符串
     */
    public static void isInput(String url,String json,BaseCallbcak<String> callbcak){

    }


}