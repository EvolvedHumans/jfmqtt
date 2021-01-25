package dti.org.adapter.scancode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yangf.pub_libs.Log4j;

import dti.org.R;
import lombok.AllArgsConstructor;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 21日 17时 51分
 * @Data： 扫码适配器，添加子元素数量，需要及时调用适配器中的notifyDataSetChanged()
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@AllArgsConstructor
public class ScanCodeAdapter extends RecyclerView.Adapter<ScanCodeHolder> {
    private ScanCodePresenter scanCodePresenter;
    @NonNull
    @Override
    public ScanCodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scancode, parent, false);
        return new ScanCodeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScanCodeHolder holder, int position) {
        //todo 调用presenter刷新视图
        scanCodePresenter.onBindScanCode(holder, position);
    }

    /**
     * 打印log getItemCount() > 0
     * <p>
     * RecyclerViewAdapter 构造方法执行了
     * <p>
     * RecyclerView 基本的都设置了，还是不行。
     *
     * @return 返回getCount()
     */
    @Override
    public int getItemCount() {
        //Log4j.d("getItemCount()", "是否大于0" + scanCodePresenter.getCount());
        return scanCodePresenter.getCount();
    }
}
