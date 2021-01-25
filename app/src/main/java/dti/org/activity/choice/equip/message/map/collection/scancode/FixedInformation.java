package dti.org.activity.choice.equip.message.map.collection.scancode;

import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2020年 12月 14日 17时 12分
 * @Data： 所需要的固定信息，单例模式
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class FixedInformation {

    public static FixedInformation fixedInformation;

    private Integer frequency;

    public FixedInformation(){
        frequency = 0;
    }

    public synchronized static FixedInformation getInstance(){
        if(fixedInformation == null){
            fixedInformation = new FixedInformation();
        }
        return fixedInformation;
    }

    /*
    当添加按钮时，用到的是这个，所应该在这里添加frequency
     */
    public String node_Button(){
        ++frequency;
        return "扫描节点"+frequency+"二维码";
    }

    public String node_Text(Integer frequency,String code){
        return "节点"+frequency+"是："+code;
    }

}
