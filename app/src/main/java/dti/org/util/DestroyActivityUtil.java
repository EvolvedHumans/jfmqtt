package dti.org.util;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.util
 * 描述：      TODO 销毁Activity
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 01月 29日 10时 00分
 */
public class DestroyActivityUtil {

    private static Map<String, Activity> destoryMap = new HashMap<>();

    //将Activity添加到队列中
    public static void addDestoryActivityToMap(String activityName, Activity activity) {
        destoryMap.put(activityName, activity);
    }

    //根据名字销毁制定Activity
    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
//        LogUtil.i(keySet.size());
        if (keySet.size() > 0) {
            for (String key : keySet) {
                if (activityName.equals(key)) {
                    Objects.requireNonNull(destoryMap.get(key)).finish();
                }
            }
        }
    }

}
