package dti.org.base;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 22日 17时 03分
 * @Data： 适配器视图层，抽象视图
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface BaseAdapterView<V> {

    void onBindView(V holder,int position);

    int getCount();

}
