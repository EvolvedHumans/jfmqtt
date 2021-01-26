package dti.org.presenter;

import android.view.View;

import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dti.org.adapter.SetoutAdapter;
import dti.org.base.BaseCallbcak;
import dti.org.base.BasePresenter;
import dti.org.config.SharedPreferenceConfig;
import dti.org.config.UrlConfig;
import dti.org.dao.DisposeGroup;
import dti.org.dao.DisposeObtain;
import dti.org.dao.Login;
import dti.org.dao.LoginGroup;
import dti.org.dao.Setout;
import dti.org.listener.SetoutPageChange;
import dti.org.model.Model;

import dti.org.updater.AppUpdater;
import dti.org.updater.net.INetCallBack;
import dti.org.views.SetoutView;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 18日 13时 10分
 * @Data： 数据层，继承BasePresenter，产品选择窗口
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SetoutPresenter extends BasePresenter<SetoutView> {

    private List<Setout> list;
    private SetoutPageChange setoutPageChange = new SetoutPageChange();

    /**
     * 产品选择界面UI绘制
     * 1.显示加载
     * 1.发起网络请求
     * 2.请求结果做出相应操作
     * 4.隐藏加载
     */
    public void drawSetout() {

        //1.显示加载
        getView().showLoading();

        //TODO 2.
        Model.SetoutModel(UrlConfig.Setout, new BaseCallbcak<List<Setout>>() {
            @Override
            public void onSuccess(List<Setout> data) {
                //3.获取List<Setout>
                List<View> viewList = new LinkedList<>();
                setList(data);
                //4.将List<Setout> 逐个转换成List<View> 集合
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) != null) {
                        String name = list.get(i).getName();
                        String picture = list.get(i).getPicture();
                        if (name != null && picture != null) {
                            viewList.add(getView().toPager(name, picture));
                        } else {
                            getView().showErr("获取Setout中的" + name + "和" + picture);
                            // return;
                        }
                    } else {
                        getView().showErr("将List<Setout> 逐个转换成List<View> 集合捕捉到异常数据");
                        return;
                    }
                }
                SetoutAdapter setoutAdapter = new SetoutAdapter(viewList);//5.初始化设配器
                getView().setViewPager(setoutAdapter, setoutPageChange);//6.放入适配器当中
            }

            @Override
            public void onFailure(String msg) {
                getView().showErr(msg);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                getView().showErr(throwable.toString());
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }


    /**
     * 开始安装 面向adapter适配器编写函数
     * 1.获取当前界面的信息
     * 2.存储当前界面信息
     * (1).当前产品的type
     * 目前定义参数：
     * type:1-》智能井盖
     * type:2-》道钉
     * 3.跳转
     */
    public void login() {

        getView().showLoading();

        HashMap<String, String> header = new HashMap<>();
        HashMap<String, String> body = new HashMap<>();

        if (!getView().exportStringCache(SharedPreferenceConfig.APP_LOGIN, SharedPreferenceConfig.NO).equals(SharedPreferenceConfig.NO)) {

            LoginGroup loginGroup = GsonYang.JsonObject(getView().exportStringCache
                    (SharedPreferenceConfig.APP_LOGIN, SharedPreferenceConfig.NO), LoginGroup.class);

            if (loginGroup != null) {
                //3.获取当前选中页的信息
                int position = setoutPageChange.getPosition();

                Setout setout = getList().get(position);
                String departmentId = loginGroup.getUser().getDepartmentId();

                if (position <= getList().size() && null != setout && null != departmentId && null != setout.getType()) {
                    body.put("departmentId", departmentId);
                    body.put("type", String.valueOf(setout.getType()));
                    Log4j.d("departmentId", departmentId);
                    Log4j.d("type", String.valueOf(setout.getType()));
                }
            }
        } else {
            getView().showErr("账号过期");
            return;
        }

        Model.SetoutEndModel(UrlConfig.SetoutConfigList, header, body, new BaseCallbcak<String>() {
            @Override
            public void onSuccess(String data) {

                Log4j.e("获取到的数据",data);

                //存储设备ID号
                getView().importIntegerCache(SharedPreferenceConfig.Setout_TYPE,
                        getList().get(setoutPageChange.getPosition()).getType());

                //存储返回数据(服务器返回的JSON数据)
                getView().importStringCache(SharedPreferenceConfig.Setout_OnClick,data);

                //跳转
                getView().jump();
            }

            @Override
            public void onFailure(String msg) {
                getView().showErr(msg);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                getView().showErr(throwable.toString());
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }


}
