package io.github.leibnik.zhihu.adapter;

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

import java.util.List;

import io.github.leibnik.zhihu.R;
import io.github.leibnik.zhihu.constant.Constant;
import io.github.leibnik.zhihu.entity.NewsADay;
import io.github.leibnik.zhihu.entity.Summary;
import io.github.leibnik.zhihu.ui.activity.DayNewsDetailActivity;
import io.github.leibnik.zhihu.view.BannerView;
import io.github.leibnik.zhihu.view.JustifyTextView;

/**
 * Created by Droidroid on 2016/3/22.
 */
public class DaySummaryAdapter extends RecyclerView.Adapter<DaySummaryAdapter.ViewHolder> {

    private List<Summary> data;
    private List<NewsADay.TopStories> topStories;
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

    public DaySummaryAdapter(Context context, NewsADay newsADay, boolean isColorTheme) {
        mContext = context;
        this.topStories = newsADay.getTop_stories();
        this.data = newsADay.getStories();
        this.isColorTheme = isColorTheme;
    }

    public void loadMore(NewsADay newsADay) {
        data.addAll(newsADay.getStories());
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        JustifyTextView tvTitle;
        TextView tvTime;
        ImageView ivTitle;
        BannerView bannerView;
        RelativeLayout rlCard;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == IS_HEADER) {
                bannerView = (BannerView) itemView.findViewById(R.id.banner);
                if (topStories != null && topStories.size() > 0){
                    bannerView.setData(topStories);
                }
            }
            if (viewType == IS_NORMAL) {
                tvTime = (TextView) itemView.findViewById(R.id.time_tv);
                tvTitle = (JustifyTextView) itemView.findViewById(R.id.summary_tv);
                ivTitle = (ImageView) itemView.findViewById(R.id.summary_iv);
                rlCard = (RelativeLayout) itemView.findViewById(R.id.card_rl);
            }

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IS_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_summary_header, parent, false);
            return new ViewHolder(view, viewType);
        } else if (viewType == IS_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary_footer, parent, false);
            return new ViewHolder(view, viewType);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary_normal, parent, false);
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
                        .placeholder(R.mipmap.loading).error(R.mipmap.error).into(holder.ivTitle);
            }
        }
        if (holder.getItemViewType() == IS_HEADER) {
            holder.bannerView.startPlay();
            holder.bannerView.setOnItemClickListener(new BannerView.OnItemClickListener() {
                @Override
                public void click(View v, NewsADay.TopStories entity) {
                    int[] location = new int[2];
                    v.getLocationOnScreen(location);
                    location[0] += v.getWidth() / 2;
                    Intent intent = new Intent(mContext, DayNewsDetailActivity.class);
                    intent.putExtra("id", entity.getId());
                    intent.putExtra("location", location);
                    intent.putExtra("title", entity.getTitle());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size() + 2;
        }
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else if (position == getItemCount() - 1) {
            return IS_FOOTER;
        } else {
            return IS_NORMAL;
        }
    }

    public Summary getSingleData(int position) {
        return data.get(position - 1);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Summary data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
