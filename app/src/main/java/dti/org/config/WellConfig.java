package dti.org.config;

import java.util.LinkedList;
import java.util.List;

import dti.org.dao.ScanCode;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 22时 23分
 * @Data： 智能井盖下，从产品的配置
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
// * Type -》 2 ，锁+SM32
//         * * Type -》 3 ，锁+SM03
//         * * Type -》 4 ，锁+SM01
//         * * Type -》 5 ，锁+SM31
//         * * Type -》 6 ，SM32
//         * * Type -》 7 ，锁+SM03+锁+SM01
    //Type -> 8.新增的设备
public final class WellConfig {

    public static String Lock = "扫描锁具二维码"; //1
    public static String SM01 = "扫描SM01二维码"; //2
    public static String SM03 = "扫描SM03二维码"; //3
    public static String SM31 = "扫描SM31二维码"; //4
    public static String SM32 = "扫描SM32二维码"; //5

    //锁
    public static List<ScanCode> lock() {
        List<ScanCode> list = new LinkedList<>();
        ScanCode scanCode = new ScanCode();
        scanCode.setCode(Lock);
        scanCode.setType(1);
        list.add(scanCode);
        return list;
    }

    //锁+SM32
    public static List<ScanCode> lockOrSm32() {
        List<ScanCode> list = new LinkedList<>();
        ScanCode scanCode = new ScanCode();
        scanCode.setCode(Lock);
        scanCode.setType(1);

        ScanCode scanCode1 = new ScanCode();
        scanCode1.setCode(SM32);
        scanCode1.setType(5);



        list.add(scanCode);
        list.add(scanCode1);

        return list;
    }

    //锁+SM03
    public static List<ScanCode> lockOrSm03() {
        List<ScanCode> list = new LinkedList<>();
        ScanCode scanCode = new ScanCode();
        scanCode.setCode(Lock);
        scanCode.setType(1);

        ScanCode scanCode1 = new ScanCode();
        scanCode1.setCode(SM03);
        scanCode1.setType(3);

        list.add(scanCode);
        list.add(scanCode1);

        return list;
    }

    //锁+SM01
    public static List<ScanCode> lockOrSm01() {
        List<ScanCode> list = new LinkedList<>();

        ScanCode scanCode = new ScanCode();
        scanCode.setCode(Lock);
        scanCode.setType(1);

        ScanCode scanCode1 = new ScanCode();
        scanCode1.setCode(SM01);
        scanCode1.setType(2);

        list.add(scanCode);
        list.add(scanCode1);

        return list;
    }

    //锁+SM31
    public static List<ScanCode> lockOrSm31() {
        List<ScanCode> list = new LinkedList<>();
        ScanCode scanCode = new ScanCode();
        scanCode.setCode(Lock);
        scanCode.setType(1);
        ScanCode scanCode1 = new ScanCode();
        scanCode1.setCode(SM31);
        scanCode1.setType(4);
        list.add(scanCode);
        list.add(scanCode1);
        return list;
    }

    //SM32
    public static List<ScanCode> sm32() {
        List<ScanCode> list = new LinkedList<>();
        ScanCode scanCode1 = new ScanCode();
        scanCode1.setCode(SM32);
        scanCode1.setType(5);
        list.add(scanCode1);
        return list;
    }

    //锁+SM03+锁+SM01
    public static List<ScanCode> lockOrSm03orSm01() {
        List<ScanCode> list = new LinkedList<>();
        ScanCode scanCode1 = new ScanCode();
        scanCode1.setCode(Lock);
        scanCode1.setType(1);

        ScanCode scanCode2 = new ScanCode();
        scanCode2.setCode(SM03);
        scanCode2.setType(3);

        ScanCode scanCode3 = new ScanCode();
        scanCode3.setCode(Lock);
        scanCode3.setType(1);

        ScanCode scanCode4 = new ScanCode();
        scanCode4.setCode(SM01);
        scanCode4.setType(2);

        list.add(scanCode1);
        list.add(scanCode2);
        list.add(scanCode3);
        list.add(scanCode4);

        return list;

    }

}
