package dti.org.config;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 22日 17时 49分
 * @Data： 拍照Config
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class CameraConfig {
    /*
    拍照和扫码的resultCode的回调参数,拍照完成后的参数
     */
    public static final Integer RESULT_Well_OK = -1;

    /*
    拍照和扫码失败后resultCode返回的回调参数，为拍照的回调参数
     */
    public static final Integer RESULT_WELL_ERR = 0;

    /*
    int requestCode 跳转时入参和拍照回调参数
     */
    public static final Integer PHOTO_REQUEST_CODE = 1234;

    /*
    跳转照片放大功能界面，需要放大的照片的页面Position下标对应的页面数
     */
    public static final String PHOTO_Enlarge = "PHOTO_Enlarge";

    /*
    resultCode照片放大界面跳转
     */
    public static final int PHOTO_Activity = 1000;

    /**
     * 删除拍照后返回type的回调
     */
    public static final String PHOTO_DELETE = "PHOTO_DELETE";

}
