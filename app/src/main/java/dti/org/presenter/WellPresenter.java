package dti.org.presenter;

import android.annotation.SuppressLint;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Build;

import android.os.FileUtils;

import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.yangf.pub_libs.Date;

import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;
import com.yangf.pub_libs.jxi.ExcelUtils;
import com.yangf.pub_libs.util.FileUtil;
import com.yangf.pub_libs.util.UrlsplicingUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import dti.org.R;

import dti.org.adapter.camera.CameraAdapter;
import dti.org.adapter.camera.CameraPresenter;
import dti.org.adapter.scancode.ScanCodeAdapter;
import dti.org.adapter.scancode.ScanCodePresenter;
import dti.org.base.BaseCallbcak;
import dti.org.base.BasePresenter;
import dti.org.config.CameraConfig;
import dti.org.config.DisposeConfig;
import dti.org.config.SharedPreferenceConfig;
import dti.org.config.UrlConfig;
import dti.org.config.WellConfig;
import dti.org.dao.CameraGroup;
import dti.org.dao.CameraList;
import dti.org.dao.Dispose;
import dti.org.dao.GroundNailInstall;
import dti.org.dao.InstallObtain;
import dti.org.dao.LoginGroup;
import dti.org.dao.MapObtain;
import dti.org.dao.PhotoObtain;
import dti.org.dao.ScanCode;
import dti.org.dao.UidTest;
import dti.org.dao.WellInstall;
import dti.org.model.WellModel;
import dti.org.views.WellView;
import lombok.EqualsAndHashCode;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 15时 11分
 * @Data： 智能井盖扫码数据层
 * 需要对不同设备进行数据处理
 * 需要数据
 * 1.锁二维码
 * 2.检测03或32二维码使用状态
 * 3.检测01和31
 * 4.RFID是否被使用
 * <p>
 * 5.  /**
 * * 智能井盖各类型数据安装接口
 * * 入参方式:JSON
 * * 请求方式:post
 * * configType字段传递根据设备选择信息界面对应Type
 * * Type -》 1 ，锁
 * * Type -》 2 ，锁+SM32
 * * Type -》 3 ，锁+SM03
 * * Type -》 4 ，锁+SM01
 * * Type -》 5 ，锁+SM31
 * * Type -》 6 ，SM32
 * * Type -》 7 ，锁+SM03+锁+SM01
 * * 入参字段：经度、纬度
 * <p>
 * 启动界面时，需要通过产品和产品下从设备调用相应的接口
 */
@EqualsAndHashCode(callSuper = true)
public class WellPresenter extends BasePresenter<WellView> {

    private final static String TAG = "dti.org.presenter";

    private static MapObtain mapObtain;

    @SuppressLint("StaticFieldLeak")
    private static ScanCodePresenter scanCodePresenter;

    @SuppressLint("StaticFieldLeak")
    private static CameraPresenter cameraPresenter;

    private static CameraAdapter cameraAdapter;

    private static ScanCodeAdapter scanCodeAdapter;

    private static Uri uri;

    private static int type = 0; //照片对应的type

    private static boolean lock = false;

    /**
     * 用于校验mapObtain
     */
    private boolean isCheck(MapObtain obtain) {
        //Log4j.e("用于校验mapObtain", obtain.toString());
        if (obtain != null) {
            if (obtain.getBaseType() != null && obtain.getType() != null && obtain.getLongitude() != null
                    && obtain.getLatitude() != null && obtain.getAddress() != null) {
                mapObtain = obtain;
                lock = true;
            }
        }
        return lock;
    }

    /**
     * @return 文件地址转Uri
     */
    private Uri fileToUri() {
        /**
         * 开启拍照功能，并储存到SD卡下
         */
        //创建file对象,存储拍下来的图片
        File file = new File(getView().getContext().getExternalCacheDir(), mapObtain.getAddress() + Date.timestamp() + ".jpg");

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
            Log.e("点击确认进行拍照失败", Objects.requireNonNull(e.getMessage()));
        }

        //进行版本验证，Android7.0系统开始，直接使用本地真是路径的Uri会抛出异常，FileProvvider是
        //一种特殊的内容提供其，可将封装过的Uri对外部进行共享，提高安全性
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            //这里使用了特殊的ContentProvider类似的机制来对数据进行保护，
            //可以选择性地将封装过的Uri共享给外部，就不会出错了
            uri = FileProvider.getUriForFile
                    (getView().getContext(), "dti.org", file);
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

    /**
     * 刷新视图
     * 1.获取地图端返回数据
     * 2.绘制设备名、当前位置信息
     * 3.通过判断产品和子产品填充刷新对应适配器
     * BaseType : 1 选择的产品是智能井盖
     * Tyep  : 表示的NB从设备
     */
    public void drawWell() {
        //todo 1.获取地图端返回数
        if (isCheck(getView().getMapContainer())) {
            //todo 2.设备名、当前位置信息
            getView().setUserTips(mapObtain.getAddress());
            getView().setPasswordTips(mapObtain.getAddress());
            //todo 3.判断产品类型和从设备类型刷新视图
            if (mapObtain.getBaseType() == 1 && mapObtain.getRfidType() != null) {
                switch (mapObtain.getType()) {
                    //只有锁
                    case 1: {
                        Log4j.e("WellConfig.lock()在此位置上", "------------------------------------------");
                        scanCodePresenter = new ScanCodePresenter
                                (getView().getContext(), WellConfig.lock(mapObtain.getRfidType(), getView().getContext()));
                        break;
                    }
                    case 2: {
                        Log4j.e("WellConfig.lockOrSm32()在此位置上", "------------------------------------------");
                        scanCodePresenter = new ScanCodePresenter
                                (getView().getContext(), WellConfig.lockOrSm32(mapObtain.getRfidType(), getView().getContext()));
                        break;
                    }
                    case 3: {
                        Log4j.e("WellConfig.lockOrSm03()在此位置上", "------------------------------------------");
                        scanCodePresenter = new ScanCodePresenter
                                (getView().getContext(), WellConfig.lockOrSm03(mapObtain.getRfidType(), getView().getContext()));
                        break;
                    }
                    case 4: {
                        Log4j.e("WellConfig.lockOrSm01()在此位置上", "------------------------------------------");
                        scanCodePresenter = new ScanCodePresenter
                                (getView().getContext(), WellConfig.lockOrSm01(mapObtain.getRfidType(), getView().getContext()));
                        break;
                    }
                    case 5: {
                        Log4j.e("WellConfig.lockOrSm31()在此位置上", "------------------------------------------");
                        scanCodePresenter = new ScanCodePresenter
                                (getView().getContext(), WellConfig.lockOrSm31(mapObtain.getRfidType(), getView().getContext()));
                        break;
                    }
                    case 6: {
                        Log4j.e("WellConfig.lockOrSm32()在此位置上", "------------------------------------------");
                        scanCodePresenter = new ScanCodePresenter
                                (getView().getContext(), WellConfig.sm32(mapObtain.getRfidType(), getView().getContext()));
                        break;
                    }
                    case 7: {
                        Log4j.e("WellConfig.lockOrSm32()在此位置上", "------------------------------------------");
                        scanCodePresenter = new ScanCodePresenter
                                (getView().getContext(), WellConfig.lockOrSm03orSm01(mapObtain.getRfidType(), getView().getContext()));
                        break;
                    }
                    default: {
                        getView().showErr("发现了外星人");
                        break;
                    }
                }
                if (scanCodePresenter != null) {
                    scanCodeAdapter = new ScanCodeAdapter(scanCodePresenter);
                    getView().drawScanCode(scanCodeAdapter);
                } else {
                    getView().showErr("错误");
                }
            }
        }
    }

    /**
     * 当监听到onActivityResult返回的数据时，修改对应的List<ScanCode>的数据
     * 具体修改item，修改text字段的值
     * public static String Rfid = "扫描RFID二维码"; //0
     * public static String Lock = "扫描锁具二维码"; //1
     * public static String SM01 = "扫描SM01二维码"; //2
     * public static String SM03 = "扫描SM03二维码"; //3
     * public static String SM31 = "扫描SM31二维码"; //4
     * public static String SM32 = "扫描SM32二维码"; //5
     * <p>
     * 各项锁具二维码存储在 List<ScanCode>集合中
     */
    public void updateScanCode(String text, int position) {

        getView().showLoading(); //加载

        String url = null; //地址
        HashMap<String, String> param = new HashMap<>(); //入参参数

        int type = scanCodePresenter.getList().get(position).getType();

        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable right = getView().getContext().getDrawable(R.drawable.lock_ok);
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable error = getView().getContext().getDrawable(R.drawable.lock_err);

        //todo 先判断它属于哪个设备
        if (type == 0) { //RFID
            param.put("rfid", text);
            url = UrlsplicingUtil.attachHttpGetParams(UrlConfig.RFID, param);
        } else if (type == 1 || type == 6) { //锁具
            param.put("lockUid", text);
            url = UrlsplicingUtil.attachHttpGetParams(UrlConfig.Lock, param);
        } else if (type == 2 || type == 4) { //SM01、SM31
            param.put("monitoringUid", text);
            url = UrlsplicingUtil.attachHttpGetParams(UrlConfig.Sm01Or31, param);
        } else if (type == 3 || type == 5) { //SM03、SM32
            param.put("pickproofUid", text);
            url = UrlsplicingUtil.attachHttpGetParams(UrlConfig.Sm32Or03, param);
        }

        if (url != null) {
            WellModel.isTesting(url, new BaseCallbcak<String>() {
                @Override
                public void onSuccess(String data) {
                    if (GsonYang.IsJson(data)) {
                        UidTest uidTest = GsonYang.JsonObject(data, UidTest.class);
                        if (uidTest.getRt() != null && uidTest.getData() != null) {
                            if (uidTest.getRt() == 1) {
                                if (scanCodePresenter.getCount() >= position) {
                                    if (!uidTest.getData()) {
                                        scanCodePresenter.getList().get(position).setDrawable(right);
                                        scanCodePresenter.getList().get(position).setInstall(1);
                                    } else {
                                        scanCodePresenter.getList().get(position).setDrawable(error);
                                        scanCodePresenter.getList().get(position).setInstall(0);
                                    }
                                    scanCodePresenter.getList().get(position).setText(text);
                                    scanCodeAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(String msg) {
                    //   getView().showErr(msg);
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                    getView().showErr("无法与服务器响应");
                }

                @Override
                public void onComplete() {
                    getView().hideLoading();
                }
            });
        }
    }

    /**
     * 开始拍照
     * 1.利用时间搓，自动生成Uri，并作为存储路径保存
     * 2.CameraConfig.PHOTO_REQUEST_CODE 为跳转Intent入参
     */
    public void startCamera() {
        if (mapObtain != null) {
            if (mapObtain.getAddress() != null) {
                uri = fileToUri();
                Log4j.d("uri", String.valueOf(uri));
                getView().cameraIntent(uri, CameraConfig.PHOTO_REQUEST_CODE);
            } else {
                getView().showErr("请先定位");
            }
        } else {
            getView().showErr("未读到缓存");
        }
    }

    /**
     * 拍照后的回调，刷新UI，填充适配器
     */
    public void addPhoto() {
        try {
            List<CameraGroup> cameraGroups = new LinkedList<>();
            CameraGroup cameraGroup = new CameraGroup();
            //Uri转path, todo 后续还需转file
            String path = uri.getPath();
            cameraGroup.setUri(path);
            cameraGroup.setType(++type);
            Bitmap bitmap = BitmapFactory.decodeStream
                    (getView().getContext().getContentResolver().openInputStream(uri));
            cameraGroup.setBitmap(bitmap);
            //todo uri转File
            File file = uriToFileApiQ(uri);
            getView().showLoading();
            String url = UrlConfig.ImportUploadImage;
            HashMap<String, File> hashMap = new HashMap<>();
            hashMap.put("image_upload", file);
            WellModel.CameraUpdata(url, hashMap, new BaseCallbcak<String>() {
                @Override
                public void onSuccess(String data) {
                    if (GsonYang.IsJson(data)) {
                        PhotoObtain photoObtain = GsonYang.JsonObject(data, PhotoObtain.class);
                        if (photoObtain != null) {
                            if (photoObtain.getUrl() != null && photoObtain.getPath() != null) {
                                if (photoObtain.getRt() == 1) {
                                    getView().showToast("请求成功");
                                    cameraGroup.setUrl(photoObtain.getUrl());
                                    //渲染视图
                                    if (cameraPresenter == null) {
                                        cameraGroups.add(cameraGroup);
                                        cameraPresenter = new CameraPresenter(getView().getContext(), cameraGroups);
                                    } else {
                                        cameraPresenter.getCameraGroups().add(cameraGroup);
                                    }
                                    if (cameraAdapter == null) {
                                        cameraAdapter = new CameraAdapter(cameraPresenter);
                                        getView().drawCamera(cameraAdapter);
                                    } else {
                                        cameraAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    getView().showErr("请求失败了");
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(String msg) {
                    Log4j.d(TAG, msg);
                    getView().showErr(msg);
                }

                @Override
                public void onError(Throwable throwable) {
                    throwable.printStackTrace();
                    getView().showErr("与服务器无响应");
                }

                @Override
                public void onComplete() {
                    getView().hideLoading();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            getView().showErr("异常，拍照失败。");
        }
    }


    public File uriToFileApiQ(Uri uri) {
        File file = null;
        if (uri == null) return null;
        //android10以上转换
        if (Objects.equals(uri.getScheme(), ContentResolver.SCHEME_FILE)) {
            file = new File(Objects.requireNonNull(uri.getPath()));
        } else if (Objects.equals(uri.getScheme(), ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = getView().getContext().getContentResolver();
            String displayName = System.currentTimeMillis() + Math.round((Math.random() + 1) * 1000)
                    + "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri));
            try {
                InputStream is = contentResolver.openInputStream(uri);
                File cache = new File(Objects.requireNonNull
                        (getView().getContext().getExternalCacheDir()).getAbsolutePath(), displayName);
                FileOutputStream fos = new FileOutputStream(cache);
                assert is != null;
                FileUtils.copy(is, fos);
                file = cache;
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 删除对应照片
     */
    public void removeCamera(int type) {
        for (int i = 0; i < cameraPresenter.getCount(); i++) {
            if (cameraPresenter.getCameraGroups().get(i).getType() == type) {
                cameraPresenter.getCameraGroups().remove(i);
                cameraAdapter.notifyDataSetChanged();
            }
        }
//        cameraPresenter.getCameraGroups().ge
    }

    /**
     * 判断当前工井id是否为null
     */
    public boolean wellId() {
        if (getView().wellId().equals("")) {
            getView().showToast("工井id未填写");
            getView().idWarning();
            return false;
        }
        getView().nameWarning();
        return true;
    }

    /**
     * 判断当前有无照片
     */
    public boolean isPhoto() {
        if (cameraPresenter == null) {
            getView().showToast("至少需要拍摄一张照片");
            return false;
        }
        if (cameraPresenter.getCount() > 0) {
            return true;
        } else {
            getView().showToast("至少需要拍摄一张照片");
            return false;
        }
    }

    /**
     * 设备信息导入
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public void clickInstall() {
        if (wellId() && isPhoto()) {
            WellInstall wellInstall = new WellInstall();
            String temp;
            //todo 必选项 , 先获取完整信息
            //1.班组id
            String banzuId = null;
            temp = getView().exportStringCache(DisposeConfig.WellTeam, DisposeConfig.WellTeam);
            if (GsonYang.IsJson(temp)) {
                Dispose dispose = GsonYang.JsonObject(temp, Dispose.class);
                if (dispose != null) {
                    banzuId = dispose.getId();
                }
            }
            //2.配置
            int configType = 0;
            String config = null;
            String configTypeId = null;
            temp = getView().exportStringCache(DisposeConfig.WellConfigure, DisposeConfig.WellConfigure);
            if (GsonYang.IsJson(temp)) {
                Dispose dispose = GsonYang.JsonObject(temp, Dispose.class);
                configTypeId = dispose.getId();
                configType = dispose.getType();
                config = dispose.getName();
            }
            //3.场景
            String sceneTypeId = null; //安装场景ID
            String scene = null; //场景名
            temp = getView().exportStringCache(DisposeConfig.WellScene, DisposeConfig.WellScene);
            if (GsonYang.IsJson(temp)) {
                Dispose dispose = GsonYang.JsonObject(temp, Dispose.class);
                sceneTypeId = dispose.getId();
                scene = dispose.getName();
            }
            //4.外井盖类型
            String outerWellTypeId = null; //外井盖类型ID
            String outerWell = null; //井盖名
            temp = getView().exportStringCache(DisposeConfig.WellOutside, DisposeConfig.WellOutside);
            if (GsonYang.IsJson(temp)) {
                Dispose dispose = GsonYang.JsonObject(temp, Dispose.class);
                outerWellTypeId = dispose.getId();
                outerWell = dispose.getName();
            }
            //5.RFID Type
            int brfid = 0;  //有无rfid
            temp = getView().exportStringCache(DisposeConfig.WellRfid, DisposeConfig.WellRfid);
            if (GsonYang.IsJson(temp)) {
                Dispose dispose = GsonYang.JsonObject(temp, Dispose.class);
                brfid = dispose.getType();
            }
            //6.基座类型
            String pedestalTypeId = null; //基座类型ID
            String pedestal = null; //基座名
            temp = getView().exportStringCache(DisposeConfig.WellPedestal, DisposeConfig.WellPedestal);
            if (GsonYang.IsJson(temp)) {
                Dispose dispose = GsonYang.JsonObject(temp, Dispose.class);
                pedestalTypeId = dispose.getId();
                pedestal = dispose.getName();
            }
            //7.工井id
            String uid = getView().wellId();
            //8.MapActivity页面传过来的讯息(经度、纬度、设备名称、产品类型)
            String lon = mapObtain.getLongitude();
            String lat = mapObtain.getLatitude();
            String name = mapObtain.getAddress();
            String lineType = String.valueOf(mapObtain.getBaseType());
            String url = UrlConfig.ImportWell; //URL地址
            //9.公司id
            String departmentId = null;
            LoginGroup loginGroup = GsonYang.JsonObject(getView().exportStringCache
                    (SharedPreferenceConfig.APP_LOGIN, SharedPreferenceConfig.NO), LoginGroup.class);
            if (loginGroup != null) {
                departmentId = loginGroup.getUser().getDepartmentId();
            }
            wellInstall.setUrl(url); //地址
            wellInstall.setName(name); //设备名称
            wellInstall.setLineType(lineType); //产品type
            wellInstall.setConfigTypeId(configTypeId); // 产品类型中的id
            wellInstall.setLon(lon); //经度
            wellInstall.setLat(lat); //纬度
            wellInstall.setDepartmentId(departmentId); //公司id
            wellInstall.setUid(uid); //工井id
            wellInstall.setBrfid(brfid);
            wellInstall.setConfigType(configType);
            wellInstall.setBanzuId(banzuId); //班组id
            wellInstall.setSceneTypeId(sceneTypeId); //安装场景id
            wellInstall.setOuterWellTypeId(outerWellTypeId); //外井盖类型
            wellInstall.setPedestalTypeId(pedestalTypeId);
            //获取当type为0下的数据
            List<ScanCode> list = scanCodePresenter.getList();
            for (int i = 0; i < list.size(); i++) {
                String content = list.get(i).getText();
                int type = list.get(i).getType();
                if (!content.equals("")) {
                    scanCodePresenter.getList().get(i).setDrawable
                            (getView().getContext().getDrawable(R.drawable.lock_ok));
                    if (type == 0) {//RFID
                        wellInstall.setRfid(content);
                    } else if (type == 1) {//锁具
                        wellInstall.setLockUid(content);
                    } else if (type == 2) {//SM01
                        wellInstall.setMonitoringUid(content); //01专用字段
                    } else if ((type == 3 || type == 4 || type == 5)) { //32、31、03
                        wellInstall.setPickproofUid(content);
                    } else if ((type == 6)) {
                        wellInstall.setLockUid03(content);
                    }
                } else {
                    scanCodePresenter.getList().get(i).setDrawable
                            (getView().getContext().getDrawable(R.drawable.lock_err));
                }
                scanCodeAdapter.notifyDataSetChanged();
            }
            Log4j.d("1.班组id", banzuId);
            Log4j.d("2.配置", config);
            Log4j.d("3.配置类型id", configTypeId);
            Log4j.d("4.场景", scene);
            Log4j.d("5.外井盖类型", outerWell);
            Log4j.d("6.RFID Type", String.valueOf(brfid));
            Log4j.d("7.基座类型", pedestal);
            Log4j.d("8.工井id", uid);
            Log4j.d("9.经度", lon);
            Log4j.d("10.纬度", lat);
            Log4j.d("11.设备名称", name);
            Log4j.d("12.产品type", lineType);
            Log4j.d("13.地址", url);
            Log4j.d("14.公司id", departmentId);
            if (wellInstall.getRfid() != null) {
                Log4j.d("15.rfid", wellInstall.getRfid());
            }
            if (wellInstall.getLockUid() != null) {
                Log4j.d("16.01锁具", wellInstall.getLockUid());
            }
            if (wellInstall.getLockUid03() != null) {
                Log4j.d("17.03锁具", wellInstall.getLockUid03());
            }
            if (wellInstall.getMonitoringUid() != null) {
                Log4j.d("18.SM01", wellInstall.getMonitoringUid());
            }
            if (wellInstall.getPickproofUid() != null) {
                Log4j.d("19.SM32、31、03", wellInstall.getPickproofUid());
            }

            StringBuilder picture = null;
            List<Bitmap> bitmapList = new LinkedList<>();
            for (int i = 0; i < cameraPresenter.getCameraGroups().size(); i++) {
                Bitmap bitmap = cameraPresenter.getCameraGroups().get(i).getBitmap();
                if (bitmap != null) {
                    bitmapList.add(bitmap);
                }
                String url1 = cameraPresenter.getCameraGroups().get(i).getUrl();
                if (picture == null) {
                    picture = new StringBuilder(url1);
                } else {
                    picture.append(",").append(url1);
                }
            }
            assert picture != null;
            Log4j.d("20.图片地址集合", picture.toString());
            wellInstall.setPictures(picture.toString());
            wellInstall.setCameraList(new CameraList(bitmapList));

            //是否需要安装字段
            for (int i = 0; i < scanCodePresenter.getCount(); i++) {
                if (scanCodePresenter.getList().get(i).getInstall() == 0) {
                    wellInstall.setInstall(0);
                    break;
                } else {
                    wellInstall.setInstall(1);
                }
            }

            //表头
            List<String> headerList = new LinkedList<>();
            headerList.add("班组id");
            headerList.add("配置");
            headerList.add("场景");
            headerList.add("外井盖类型");
            headerList.add("RFID Type");
            headerList.add("基座类型");
            headerList.add("工井id");
            headerList.add("经度");
            headerList.add("纬度");
            headerList.add("设备名称");
            headerList.add("产品type");
            headerList.add("公司id");
            headerList.add("rfid");
            headerList.add("01锁具");
            headerList.add("03锁具");
            headerList.add("SM01");
            headerList.add("SM32、31、03");
            headerList.add("有无导入");

            List<String> bodyList = new LinkedList<>();
            bodyList.add(banzuId);
            bodyList.add(config);
            bodyList.add(scene);
            bodyList.add(outerWell);
            bodyList.add(String.valueOf(brfid));
            bodyList.add(pedestal);
            bodyList.add(uid);
            bodyList.add(lon);
            bodyList.add(lat);
            bodyList.add(name);
            bodyList.add(lineType);
            bodyList.add(departmentId);
            bodyList.add(wellInstall.getRfid());
            bodyList.add(wellInstall.getLockUid());
            bodyList.add(wellInstall.getLockUid03());
            bodyList.add(wellInstall.getMonitoringUid());
            bodyList.add(wellInstall.getPickproofUid());

            //转成Json格式数据
            String content = GsonYang.JsonString(wellInstall);
            Log4j.d(TAG, content);

            getView().showLoading();
            WellModel.WellInput(url, content, new BaseCallbcak<String>() {
                @Override
                public void onSuccess(String data) {
                    if (GsonYang.IsJson(data)) {
                        InstallObtain installObtain = GsonYang.JsonObject(data, InstallObtain.class);
                        if (installObtain != null) {
                            if (installObtain.getRt() != null && installObtain.getMsg() != null) {
                                if (installObtain.getRt() == 1) {
                                    //todo 跳转到安装成功界面
                                    bodyList.add("导入成功");
                                    ExcelUtils.createFile(FileUtil.getExcelFileName("智能井盖" + Date.timestamp()), getView().getContext());
                                    ExcelUtils.createExcel(headerList, bodyList);
                                    getView().showToast(installObtain.getMsg());
                                    getView().installSuccessful(WellConfig.WellImportSuccess, content);
                                } else {
                                    //todo 是否需要继续安装
                                    getView().showExportPopup("提示", installObtain.getMsg());
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(String msg) {
                    getView().showToast("失败" + msg);
                    Log4j.d(TAG, msg);
                }

                @Override
                public void onError(Throwable throwable) {
                    bodyList.add("导入失败");
                    ExcelUtils.createFile(FileUtil.getExcelFileName("智能井盖" + Date.timestamp()), getView().getContext());
                    ExcelUtils.createExcel(headerList, bodyList);
                    getView().showErr("与服务器无响应");
                    throwable.printStackTrace();
                    //todo 跳转到安装失败的界面
                    getView().installFailed(WellConfig.WellImportFail, content);

                }

                @Override
                public void onComplete() {
                    getView().hideLoading();
                }
            });

        }
    }

    /**
     * 覆盖,变为可安装状态
     */
    public void overlap() {
        for (int i = 0; i < scanCodePresenter.getCount(); i++) {
            scanCodePresenter.getList().get(i).setInstall(1);
        }
    }

    public void release() {
        cameraPresenter = null;
        cameraAdapter = null;
        scanCodeAdapter = null;
        scanCodePresenter = null;
    }
}




















