package com.zzu.ehome.ehomefordoctor.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.entity.CholHistoryBean;
import com.zzu.ehome.ehomefordoctor.utils.CommonUtils;
import com.zzu.ehome.ehomefordoctor.utils.DateUtils;


import java.util.List;

/**
 * Created by Administrator on 2016/5/9.
 */
public class CholChatAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    public List<CholHistoryBean> getList() {
        return list;
    }

    public void setList(List<CholHistoryBean> list) {
        this.list = list;
    }

    List<CholHistoryBean> list;
    private Context mContext;
    private LayoutInflater mInflater;


    public CholChatAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        CholHistoryBean res = (CholHistoryBean) getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_ua_layout,
                    parent, false);
            holder = new ViewHolder();
            holder.tv_tw = (TextView) convertView
                    .findViewById(R.id.tv_tw);
            holder.tv_status = (TextView) convertView
                    .findViewById(R.id.tv_twjg_msg);
            holder.rl_status = (RelativeLayout) convertView.findViewById(R.id.rl_status);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String tw = "胆固醇:" + res.getCHOL() + "mmol/L";
        int bstart = tw.indexOf("mmol/L");
        int bend = tw.indexOf("胆固醇:");
        SpannableString style = new SpannableString(tw);
        style.setSpan(
                new TextAppearanceSpan(mContext, R.style.styleNormalColor), bend + 3, bstart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.tv_tw.setText(style);

        Double temp = Double.valueOf(res.getCHOL());
        if ( Double.compare(temp, 5.18d) <= 0) {
            holder.tv_status.setText("正常");
            holder.rl_status.setBackgroundResource(R.drawable.btn_yuyue_4);

        } else  {
            holder.tv_status.setText("偏高");
            holder.rl_status.setBackgroundResource(R.drawable.btn_yuyue_2);

        }
        holder.tv_time.setText(DateUtils.StringPattern(res.getMonitorTime(), "yyyy/MM/dd HH:mm:ss", "dd日 HH:mm"));
        return convertView;

    }

    public static class ViewHolder {

        public TextView tv_tw;
        private TextView tv_status;
        public RelativeLayout rl_status;
        public TextView tv_time;

    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        CholHistoryBean res = (CholHistoryBean) getItem(position);
        View headView = mInflater.inflate(R.layout.item_chat_header,
                parent, false);
        TextView tvheader = (TextView) headView.findViewById(R.id.tv_time);
        String date = DateUtils.StringPattern(res.getMonitorTime(), "yyyy/MM/dd HH:mm:ss", "M月yyyy年");
        int bstart = date.indexOf("月");
        SpannableString style = new SpannableString(date);
        style.setSpan(
                new TextAppearanceSpan(mContext, R.style.styleItemColor), 0, bstart, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvheader.setText(style);
        return headView;
    }

    /**
     * 决定header出现的时机，如果当前的headerid和前一个headerid不同时，就会显示
     */
    @Override
    public long getHeaderId(int position) {
        CholHistoryBean res = (CholHistoryBean) getItem(position);
        return CommonUtils.returnLongNew(DateUtils.StringPattern(res.getMonitorTime(), "yyyy/MM/dd HH:mm:ss", "yyyy-MM"));

    }
}
