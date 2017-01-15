package com.nyakokishi.zhihu.ui.daily;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.nyakokishi.data.data.DailyStory;
import com.nyakokishi.data.data.Daily;
import com.nyakokishi.zhihu.R;
import com.nyakokishi.zhihu.constant.Constant;
import com.nyakokishi.zhihu.ui.daily.detail.DetailActivity;
import com.nyakokishi.zhihu.widget.BannerView;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private List<DailyStory> data = new ArrayList<>();
    private List<Daily.TopStory> topStories = new ArrayList<>();
    private Context mContext;
    private static final int IS_HEADER = 0;
    private static final int IS_NORMAL = 1;
    private static final int IS_FOOTER = 2;

    private boolean isColorTheme;
    private OnItemClickListener mItemClickListener = null;

    public void updateTheme(boolean isColorTheme) {
        this.isColorTheme = isColorTheme;
        notifyDataSetChanged();
    }

    public StoriesAdapter(Context context, boolean isColorTheme) {
        mContext = context;
        this.isColorTheme = isColorTheme;
    }

    public void loadMore(Daily daily) {
        data.addAll(daily.getStories());
        notifyDataSetChanged();
    }

    public void refreshData(Daily daily) {
        topStories.addAll(daily.getTopStories());
        data.addAll(daily.getStories());
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvTime;
        ImageView ivTitle;
        BannerView bannerView;
        RelativeLayout rlCard;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == IS_HEADER) {
                bannerView = (BannerView) itemView.findViewById(R.id.banner);
                if (topStories != null && topStories.size() > 0) {
                    bannerView.setData(topStories);
                }
            }
            if (viewType == IS_NORMAL) {
                tvTime = (TextView) itemView.findViewById(R.id.time_tv);
                tvTitle = (TextView) itemView.findViewById(R.id.summary_tv);
                ivTitle = (ImageView) itemView.findViewById(R.id.summary_iv);
                rlCard = (RelativeLayout) itemView.findViewById(R.id.card_rl);
            }

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_story_header, parent, false);
            return new ViewHolder(view, viewType);
        } else if (viewType == IS_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_footer, parent, false);
            return new ViewHolder(view, viewType);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_normal, parent, false);
            return new ViewHolder(view, viewType);
        }

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (data == null) {
            return;
        }
        if (holder.getItemViewType() == IS_NORMAL) {
            if (holder.bannerView != null) {
                holder.bannerView.stopPlay();
            }
            if (data.get(position - 1).getType() == Constant.DAILY_DATE) {
                holder.tvTime.setVisibility(View.VISIBLE);
                holder.tvTime.setTextColor(mContext.getResources().getColor(isColorTheme ? R.color.colortheme_timetv_textcolor
                        : R.color.picturetheme_timetv_textcolor));
                holder.ivTitle.setVisibility(View.GONE);
                holder.tvTitle.setVisibility(View.GONE);
                holder.rlCard.setBackground(null);
            } else {
                holder.tvTime.setVisibility(View.GONE);
                holder.ivTitle.setVisibility(View.VISIBLE);
                holder.tvTitle.setVisibility(View.VISIBLE);
                holder.rlCard.setBackground(mContext.getResources()
                        .getDrawable(R.drawable.item_background_selector));
                if (mItemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.onItemClick(v, data.get(position - 1));
                        }
                    });
                }

            }
            holder.tvTime.setText(data.get(position - 1).getTitle());
            holder.tvTitle.setText(data.get(position - 1).getTitle() + "\n");
            if (data.get(position - 1).getImages() != null && data.get(position - 1).getImages().size() > 0) {
                Glide.with(mContext.getApplicationContext()).load(data.get(position - 1).getImages().get(0))
                        .placeholder(R.drawable.loading).error(R.drawable.error).into(holder.ivTitle);
            }
        }
        if (holder.getItemViewType() == IS_HEADER) {
            holder.bannerView.startPlay();
            holder.bannerView.setOnItemClickListener(new BannerView.OnItemClickListener() {
                @Override
                public void click(View v, Daily.TopStory entity) {
                    int[] location = new int[2];
                    v.getLocationOnScreen(location);
                    location[0] += v.getWidth() / 2;
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    DailyStory dailyStory = new DailyStory();
                    dailyStory.setId(entity.getId());
                    dailyStory.setTitle(entity.getTitle());
                    intent.putExtra("dailyStory", dailyStory);
                    intent.putExtra("location", location);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (data.size() != 0 && topStories.size() != 0) {
            return data.size() + 2;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size() == 0 && topStories.size() == 0) {
            return IS_FOOTER;
        }
        if (position == 0) {
            return IS_HEADER;
        } else if (position == getItemCount() - 1) {
            return IS_FOOTER;
        } else {
            return IS_NORMAL;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, DailyStory data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
