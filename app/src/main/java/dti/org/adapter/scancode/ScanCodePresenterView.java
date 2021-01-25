package dti.org.adapter.scancode;

import dti.org.adapter.scancode.ScanCodeHolder;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 19时 08分
 * @Data： 扫码业务层接口
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
public interface ScanCodePresenterView {

    void onBindScanCode(ScanCodeHolder holder, int position);

    int getCount();
}
