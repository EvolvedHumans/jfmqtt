package dti.org.controller;

import java.util.List;

import dti.org.dao.Dispose;
import dti.org.dao.DisposeGroup;
import dti.org.dao.DisposeObtain;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.presenter
 * 描述：      TODO Presenter层抽象方法
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 25日 17时 28分
 */
public interface DisposeController {

    void clearHistory();

    void drawDispose();

    void lockMechanism();

    List<Dispose> itemDatabase(int position);

    //获取对应位置的type信息
    int getItemType(int position);

    //获取对应产品类型的type信息
    int getType();

    void itemOptional(int position);

    boolean judgeDisposeObtain(DisposeObtain disposeObtain);

    boolean judgeDisposeGroup(DisposeGroup disposeGroup);

    boolean judgeDispose(Dispose dispose);

    //销毁界面释放静态变量
    void release();
}
