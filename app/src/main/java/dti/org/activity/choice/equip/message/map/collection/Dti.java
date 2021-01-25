package dti.org.activity.choice.equip.message.map.collection;

/**
 * @name： 杨帆
 * @Time： 2020年 11月 25日 15时 29分
 * @Data：公用实参
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public class Dti {

    //拍照后文件名传递
    public static final String FILE_NAME = "FILE_NAME";

    //对应图片编号
    public static final String IMG_CODE = "IMAGE_CODE";

    //拍照、扫码差值
    public static final Integer DATA = 1000;

    //跳转文件编号获取，若无，则如下:
    public static final Integer FILE_CODE = -1;

    //拍照参数
    public static final Integer PHOTO_CODE = 1234;

    //拍照回调参数
    //Can only use lower 16 bits for requestCode
    public static final int RESULT_PHOTO = 1;

    //拍照和扫码的回调
    public static final int RESULT_CODE_SCAN = -1;

    //网关节点二维码按钮点击后的必出文本
    public static final String TEXT_CODE_SCAN = "扫描的网关是：";

}
