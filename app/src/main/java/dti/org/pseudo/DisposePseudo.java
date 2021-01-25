package dti.org.pseudo;

import com.yangf.pub_libs.GsonYang;

import java.util.LinkedList;
import java.util.List;

import dti.org.dao.Dispose;
import dti.org.dao.DisposeGroup;
import dti.org.dao.DisposeObtain;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 13日 14时 38分
 * @Data： 配置界面假数据生成器
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class DisposePseudo {
    public static String getJsonDispose(){
        DisposeObtain disposeObtain = new DisposeObtain();
        disposeObtain.setRt(1);
        disposeObtain.setComments("返回comments");

        List<Dispose> list = new LinkedList<>();
        for (int i=0;i<10;i++){
            Dispose dispose = new Dispose();
            dispose.setId(String.valueOf(i));
            dispose.setName("类型"+i);
            dispose.setType(i);
            list.add(dispose);
        }

        List<DisposeGroup> groupList = new LinkedList<>();
        for (int i=0;i<5;i++){
            DisposeGroup disposeGroup = new DisposeGroup();
            disposeGroup.setName("井盖"+i);
            disposeGroup.setType(i);
            disposeGroup.setTopic("标题"+i);
            disposeGroup.setDetails(list);
            groupList.add(disposeGroup);
        }
        disposeObtain.setData(groupList);

        return GsonYang.JsonString(disposeObtain);

    }
}
