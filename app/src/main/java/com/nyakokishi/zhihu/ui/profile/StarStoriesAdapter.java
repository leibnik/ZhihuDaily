package com.nyakokishi.zhihu.ui.profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.nyakokishi.data.data.DailyStory;
import com.nyakokishi.zhihu.R;

/**
 * Created by nyakokishi on 2016/3/30.
 */
public class StarStoriesAdapter extends RecyclerView.Adapter<StarStoriesAdapter.ViewHolder> {
    private Context mContext;
    private List<DailyStory> mData;
    public static final int MODE_NORMAL = 1;
    public static final int MODE_FOOTER = 2;
    private OnItemClickListener mOnItemClickListener;
    private boolean isFooterGone = false;

    public void setIsFooterGone(boolean isFooterGone) {
        this.isFooterGone = isFooterGone;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void OnItemClick(View v, DailyStory data);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public StarStoriesAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<DailyStory> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<DailyStory> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MODE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_normal, parent, false);
            return new ViewHolder(view, viewType);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_footer, parent, false);
            return new ViewHolder(view, viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder.getItemViewType() == MODE_NORMAL) {
            if (mData.get(position).getImages() != null && mData.get(position).getImages().size() > 0) {
                holder.itemIv.setVisibility(View.VISIBLE);
                Glide.with(mContext.getApplicationContext()).load(mData.get(position).getImages().get(0))
                        .placeholder(R.drawable.loading).error(R.drawable.error).into(holder.itemIv);
                holder.itemTv.setText(mData.get(position).getTitle());
            } else {
                holder.itemIv.setVisibility(View.GONE);
                holder.itemTv.setText(mData.get(position).getTitle());
            }
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.OnItemClick(v, mData.get(position));
                    }
                });
            }

        }
        if (holder.getItemViewType() == MODE_FOOTER) {
            if (isFooterGone) {
                holder.progressBar.setVisibility(View.GONE);
            } else {
                holder.progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null && mData.size() > 0) {
            return mData.size() + 1;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return MODE_FOOTER;
        }
        return MODE_NORMAL;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTv;
        ImageView itemIv;
        ProgressBar progressBar;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == MODE_NORMAL) {
                itemTv = (TextView) itemView.findViewById(R.id.summary_tv);
                itemIv = (ImageView) itemView.findViewById(R.id.summary_iv);
            }
            if (viewType == MODE_FOOTER) {
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            }
        }
    }
}
