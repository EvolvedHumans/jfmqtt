package dti.org.pseudo;

import com.yangf.pub_libs.GsonYang;

import java.util.LinkedList;
import java.util.List;

import dti.org.dao.SetoutObtain;
import dti.org.dao.Setout;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 12日 12时 05分
 * @Data： 设备选择界面假数据生成器
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public final class EquipPseudo {
    public static String getJsonEquip(){
        List<Setout> list = new LinkedList<>();
        list.add(new Setout("1","智能井盖",
                "http://www.yyj2857.cn/wp-content/uploads/2020/12/d.png",1));
        list.add(new Setout("2","道钉",
                "http://www.yyj2857.cn/wp-content/uploads/2020/12/b.png",2));
        SetoutObtain setoutObtain = new SetoutObtain(1,"msg","服务器返回成功",list);
        return GsonYang.JsonString(setoutObtain);
    }
}
