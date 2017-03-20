package com.zzu.ehome.ehomefordoctor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.entity.BloodRoutine;

import java.util.List;

import static android.R.attr.value;
import static com.igexin.push.core.a.l;

/**
 * Created by Administrator on 2016/9/14.
 */
public class OcrDetailAdapter extends BaseListAdapter<BloodRoutine> {


    private List<BloodRoutine> mList;
    private Context mContext;


    public OcrDetailAdapter(Context context, List<BloodRoutine> objects) {
        super(context, objects);
        this.mList = objects;
        this.mContext = context;

    }

    @Override
    public View getGqView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(R.layout.ocr_detail_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.tvname);
            holder.num = (TextView) convertView.findViewById(R.id.ednum);
            holder.AToB = (TextView) convertView.findViewById(R.id.tvAtoB);
            holder.tvrange = (TextView) convertView.findViewById(R.id.tvRange);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        BloodRoutine item = getItem(position);

        holder.name.setText(item.getCHK_ItemName_Z());
        if(item.getItemRange().contains("-")) {
            String[] range = item.getItemRange().split("-");
            float low = Float.valueOf(range[0]);
            float hight = Float.valueOf(range[1]);
            float value = Float.valueOf(item.getItemValue());

            if (Float.compare(value, low) < 0 || Float.compare(value, hight) > 0) {
                holder.num.setTextColor(Color.RED);
                holder.AToB.setTextColor(Color.RED);
            } else {
                holder.num.setTextColor(Color.parseColor("#949395"));
                holder.AToB.setTextColor(Color.parseColor("#949395"));
            }
        }else{
          float ll=Float.valueOf(item.getItemRange());
            float v=Float.valueOf(item.getItemValue());
            if (Float.compare(v, ll)==0){
                holder.num.setTextColor(Color.parseColor("#949395"));
                holder.AToB.setTextColor(Color.parseColor("#949395"));
            }else{
                holder.num.setTextColor(Color.RED);
                holder.AToB.setTextColor(Color.RED);
            }
        }

        holder.num.setText(item.getItemValue());
        holder.AToB.setText(item.getItemUnit());
        holder.tvrange.setText(item.getItemRange());
        return convertView;
    }


    public class ViewHolder {

        private TextView name;
        private TextView num;
        private TextView AToB;
        private TextView tvrange;
    }
}
