package com.zzu.ehome.ehomefordoctor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.entity.StaticBean;

import java.util.List;

/**
 * Created by Mersens on 2016/10/26.
 */

public class ECGStaticAadapter extends BaseListAdapter<StaticBean> {
    private List<StaticBean> mList;
    private Context mContext;

    public ECGStaticAadapter(Context context, List<StaticBean> objects) {
        super(context, objects);
        this.mList = objects;
        this.mContext = context;
    }

    @Override
    public View getGqView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(R.layout.dynamic_item, null);
            holder.tvtitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvtitle.setText("静态心电报告");
        final StaticBean item = getItem(position);
        holder.time.setText(item.getReportTime());
        return convertView;
    }

    public static class ViewHolder {
        private TextView tvtitle;
        private TextView time;
    }

}
