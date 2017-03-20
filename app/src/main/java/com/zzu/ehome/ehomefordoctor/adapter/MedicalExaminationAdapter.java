package com.zzu.ehome.ehomefordoctor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.activity.MedicalExaminationDesActivity;
import com.zzu.ehome.ehomefordoctor.entity.MedicalBean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/18.
 */
public class MedicalExaminationAdapter extends BaseAdapter {


    public void setmList(List<MedicalBean> mList) {
        this.mList = mList;
    }

    private List<MedicalBean> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MedicalExaminationAdapter(Context context, List<MedicalBean> objects) {

        this.mList = objects;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public List<MedicalBean> getmList() {
        return mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        MedicalBean itme = (MedicalBean) getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.examination_report_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvtime = (TextView) convertView.findViewById(R.id.tv_time);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvtime.setText(itme.getCheckTime().split(" ")[0]);
        holder.name.setText(itme.getInstituteName());
        final String id = itme.getID();

        return convertView;
    }

    public static class ViewHolder {
        TextView name;
        TextView tvtime;

    }


}
