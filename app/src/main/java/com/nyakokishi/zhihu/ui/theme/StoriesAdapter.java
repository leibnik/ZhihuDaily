package com.nyakokishi.zhihu.ui.theme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.nyakokishi.data.data.DailyStory;
import com.nyakokishi.data.data.ThemeStory;
import com.nyakokishi.zhihu.R;

/**
 * Created by nyakokishi on 2016/3/24.
 */
public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private List<DailyStory> mData;
    private String mTitle;
    private String mImageUrl;
    private Context mContext;
    public static final int IS_HEADER = 0;
    public static final int IS_NORMAL = 1;
    private boolean isColorTheme;
    private OnItemClickListener mOnItemClickListener;

    public void updateTheme(boolean isColorTheme) {
        this.isColorTheme = isColorTheme;
    }

    public interface OnItemClickListener {
        void OnItemClick(View v, DailyStory data);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTv;
        ImageView itemIv, headerIv;
        TextView headerTv;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == IS_NORMAL) {
                itemTv = (TextView) itemView.findViewById(R.id.summary_tv);
                itemIv = (ImageView) itemView.findViewById(R.id.summary_iv);
            }
            if (viewType == IS_HEADER) {
                headerIv = (ImageView) itemView.findViewById(R.id.header_iv);
                headerTv = (TextView) itemView.findViewById(R.id.header_tv);
            }
        }
    }

    public StoriesAdapter(Context context, ThemeStory data) {
        this.mData = data.getStories();
        this.mTitle = data.getDescription();
        this.mImageUrl = data.getBackground();
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme_story_header, parent, false);
            return new ViewHolder(view, viewType);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_normal, parent, false);
            return new ViewHolder(view, viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mData == null) {
            return;
        }
        if (holder.getItemViewType() == IS_NORMAL) {
            if (mData.get(position - 1).getImages() != null && mData.get(position - 1).getImages().size() > 0) {
                holder.itemIv.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(mData.get(position - 1).getImages().get(0))
                        .placeholder(R.drawable.loading).error(R.drawable.error).into(holder.itemIv);
                holder.itemTv.setText(mData.get(position - 1).getTitle());
            } else {
                holder.itemIv.setVisibility(View.GONE);
                holder.itemTv.setText(mData.get(position - 1).getTitle());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.OnItemClick(v, mData.get(position - 1));
                }
            });
        }
        if (holder.getItemViewType() == IS_HEADER) {
            if (!TextUtils.isEmpty(mImageUrl)) {
                Glide.with(mContext.getApplicationContext()).load(mImageUrl).into(holder.headerIv);
            }
            holder.headerTv.setText(mTitle);
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size() + 1;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? IS_HEADER : IS_NORMAL;
    }
}
