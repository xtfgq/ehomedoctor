package com.zzu.ehome.ehomefordoctor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.entity.ResultContent;
import com.zzu.ehome.ehomefordoctor.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 */
public class InspectionReportAdapter extends BaseListAdapter<ResultContent>{
    private List<ResultContent> mList;
    private Context mContext;



    public InspectionReportAdapter(Context context, List<ResultContent> objects) {
        super(context, objects);
        this.mList = objects;
        this.mContext = context;

    }

    @Override
    public View getGqView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ResultContent item=mList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(R.layout.ocr2_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvtime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.ivocr=(ImageView)convertView.findViewById(R.id.ivocr);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(item.getOCRTypeName());
        holder.tvtime.setText(DateUtils.StringPattern(item.getCreatedDate(), "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd"));
        if(Integer.valueOf(item.getFromTo())==2){
            holder.ivocr.setVisibility(View.VISIBLE);
        }else{
            holder.ivocr.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }


    public class ViewHolder {

        private TextView name;
        private TextView tvtime;
        private ImageView ivocr;
    }


}
