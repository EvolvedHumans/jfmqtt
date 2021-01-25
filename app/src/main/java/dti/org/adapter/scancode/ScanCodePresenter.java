package dti.org.adapter.scancode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dti.org.R;
import dti.org.dao.ScanCode;
import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 18时 01分
 * @Data： 扫码模块，中间层处理,将扫码所需数据全部导入ScanCodePresenter
 * startActivityForResult中的参数在Activity中返回，对应item下标根据position来计算
 * 该方法连接ScanCodeAdapter，用于数据存储
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class ScanCodePresenter implements ScanCodePresenterView {

    private Context context;
    private List<ScanCode> list;

    public ScanCodePresenter(Context context, List<ScanCode> list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindScanCode(@NotNull ScanCodeHolder holder, int position) {
        String code = list.get(position).getCode();
        String text = list.get(position).getText();
        Drawable drawable = list.get(position).getDrawable();
        holder.setContent(code);
        holder.setText(text);
        holder.setLinearLayoutColor(drawable);
        holder.setOnClickListener(v -> {
            ZxingConfig zxingConfig = new ZxingConfig();
            zxingConfig.setPlayBeep(true);
            zxingConfig.setShake(true);
            zxingConfig.setDecodeBarCode(true);
            zxingConfig.setReactColor(R.color.touming);
            zxingConfig.setFrameLineColor(R.color.touming);
            zxingConfig.setScanLineColor(R.color.colorAccent);
            zxingConfig.setFullScreenScan(true);
            Intent intent = new Intent(context, CaptureActivity.class);
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, zxingConfig);
            ((Activity) context).startActivityForResult(intent, position);
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
