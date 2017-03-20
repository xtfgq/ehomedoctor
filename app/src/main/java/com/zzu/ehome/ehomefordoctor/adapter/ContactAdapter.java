

package com.zzu.ehome.ehomefordoctor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiang.android.lib.adapter.BaseAdapter;
import com.jiang.android.lib.adapter.expand.StickyRecyclerHeadersAdapter;
import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.entity.UsersBySignDoctor;
import com.zzu.ehome.ehomefordoctor.view.IndexAdapter;

import java.util.List;



public class ContactAdapter extends BaseAdapter<UsersBySignDoctor, ContactAdapter.ContactViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, IndexAdapter, View.OnClickListener {
    private List<UsersBySignDoctor> mLists;
    private Context mContext;

    public ContactAdapter(Context ct, List<UsersBySignDoctor> mLists) {
        this.mLists = mLists;
        mContext = ct;
        this.addAll(mLists);
    }

    public void setLists(List<UsersBySignDoctor> mLists){
        this.mLists = mLists;
//       this. mLists.addAll(mLists);
//        notifyDataSetChanged();
//       this.clear();
//       this.addAll(this.mLists);
//notifyDataSetChanged();
    }


    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hz, parent, false);
        view.setOnClickListener(this);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ContactViewHolder holder, int position) {
        holder.mName.setText(getItem(position).getUser_FullName());
        String imgurl = getItem(position).getUser_Icon();
        if (!TextUtils.isEmpty(imgurl)) {
            if(imgurl.contains("vine.gif")){
                holder.mIcon.setImageResource(R.drawable.rc_ic_def_coversation_portrait);
            }else {
               Glide.with(mContext).load(imgurl).into(holder.mIcon);
            }
        }
        holder.itemView.setTag(getItem(position));
    }
    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }
    @Override
    public long getHeaderId(int position) {
        return getItem(position).getSortLetters().charAt(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        String showValue = String.valueOf(getItem(position).getSortLetters().charAt(0));
        textView.setText(showValue);
        holder.itemView.setTag(getItem(position));
    }


    public int getPositionForSection(char section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mLists.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;

    }


    public static  class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public ImageView mIcon;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.item_contact_title);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }

    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        <T> void onItemClick(View view, T t);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
