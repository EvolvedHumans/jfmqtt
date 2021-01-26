package dti.org.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.yangf.pub_libs.GsonYang;
import com.yangf.pub_libs.Log4j;

import java.util.LinkedList;
import java.util.List;

import dti.org.adapter.DisposeAdapter;
import dti.org.base.BasePresenter;
import dti.org.config.SharedPreferenceConfig;
import dti.org.controller.DisposeController;
import dti.org.dao.Dispose;
import dti.org.dao.DisposeGroup;
import dti.org.dao.DisposeObtain;
import dti.org.item.DisposeItemDecoration;
import dti.org.views.DisposeView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 20日 13时 06分
 * @Data： 设备信息选择交互层
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DisposePresenter extends BasePresenter<DisposeView> implements DisposeController {

    static CompositeDisposable compositeDisposable = new CompositeDisposable();

    static List<DisposeGroup> disposeGroups = new LinkedList<>();

    //wheel数据源 List<Dispose>
    static List<Dispose> disposes = new LinkedList<>();

    //按钮组数据源
    static List<String> list = new LinkedList<>();

    //按钮锁
    static volatile boolean lock = false;

    /**
     * 绘制Dispose界面
     */
    @Override
    public void drawDispose() {
        //总数据 List<DisposeGroup>
        String content = getView().exportStringCache(SharedPreferenceConfig.Setout_OnClick, SharedPreferenceConfig.NO);
        Log4j.d("获取上个界面返回信息",content);
        if (GsonYang.IsJson(content)) {
            DisposeObtain disposeObtain = GsonYang.JsonObject(content, DisposeObtain.class);
            if (judgeDisposeObtain(disposeObtain)) {
                if (disposeObtain.getRt() == 1) {
                    disposeGroups = disposeObtain.getData();
                    int i =0;
                    for (DisposeGroup disposeGroup : disposeGroups) {
                        if (judgeDisposeGroup(disposeGroup)) {
                            list.add(disposeGroup.getName());
                        }
                    }
                }
            }
        }
        getView().setButtonGroup(new DisposeAdapter(getView().getContext(), list), new DisposeItemDecoration(40, 40, 20, 0));
    }

    /**
     * 锁机制
     */
    @Override
    public void lockMechanism() {
        //事件发射器
        Observable<String> observable = Observable.create(emitter -> {
            while (!lock);
            emitter.onNext("");
            emitter.onComplete();
        });

        //调度到主线程
        DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String response) {
                if(isViewAttached()){
                    getView().startOptional();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onComplete() { }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

        compositeDisposable.add(disposableObserver);

    }

    /**
     * Dispose下Item点击事件，需要获取List<DisposeGroup>下的选择滚动器的数据源，并进行填充
     */
    @Override
    public List<Dispose> itemDatabase(int position) {
        try {
            for (Dispose dispose : disposeGroups.get(position).getDetails()) {
                if (judgeDispose(dispose)) {
                    return disposeGroups.get(position).getDetails();
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 选择滚动器视图页面中的点击事件，返回对应按钮的item的下标,当所有下标都返回过一次后，则让按钮处于可点击状态
     */
    @Override
    public void itemOptional(int position) {
        //1.设置为点击状态
        disposeGroups.get(position).setSelected(true);

        //2.判断是否全部点击过
        for (DisposeGroup group : disposeGroups) {
            if (!group.isSelected()) {
                return;
            }
        }
        Log4j.d("选择滚动器视图页面中的点击事件", "当所有下标都返回过一次后，则让按钮处于可点击状态");
        lock = true;
    }

    /**
     * 判断该按钮是否为选择配置按钮
     */
    @Override
    public boolean itemConfig(int position){
        return disposeGroups.get(position).getType() == 2;
    }


    /**
     * 判断该对象DisposeObtain下是否有属性为null
     */
    @Override
    public boolean judgeDisposeObtain(DisposeObtain disposeObtain) {
        return disposeObtain.getRt() != null && disposeObtain.getData() != null;
    }

    /**
     * 判断该镀锡下DisposeGroup下是否有属性为null
     */
    @Override
    public boolean judgeDisposeGroup(DisposeGroup disposeGroup) {
        return disposeGroup.getType() != null && disposeGroup.getName() != null && disposeGroup.getDetails() != null;
    }

    /**
     * 判断Dispose下是否有属性为null
     */
    @Override
    public boolean judgeDispose(Dispose dispose) {
        return dispose.getId() != null && dispose.getName() != null && dispose.getType() != null;
    }

    @Override
    public void release() {
        disposeGroups.clear();
        disposes.clear();
        list.clear();
        lock = false;
    }

    /*
  销毁页面时，取消订阅
   */
    @Override
    protected void finalize() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        Log4j.d("~~~~~~~~~~~~~~~~~~~~~~~~~~~~","页面销毁！！！！！");
    }

}
