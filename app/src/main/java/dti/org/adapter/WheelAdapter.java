package dti.org.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.wheelview.adapter.BaseWheelAdapter;

import dti.org.R;
import dti.org.dao.Dispose;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 15日 16时 26分
 * @Data： Wheel滚动适配器
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WheelAdapter extends BaseWheelAdapter<Dispose> {

    private Context mContext;

    public WheelAdapter(Context context) {
        mContext = context;
    }

    @SuppressLint("InflateParams")
    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        WheelAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new WheelAdapter.ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.wheel, null);
            viewHolder.textView = convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (WheelAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
