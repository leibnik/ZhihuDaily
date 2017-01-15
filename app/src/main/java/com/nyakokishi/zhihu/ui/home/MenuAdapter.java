package com.nyakokishi.zhihu.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.nyakokishi.data.data.Theme;
import com.nyakokishi.zhihu.R;

/**
 * Created by nyakokishi on 2016/3/22.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<Theme> data;
    private OnItemClickListener onItemClickListener = null;
    private int currentThemeId;
    private Context mContext;


    public interface OnItemClickListener {
        void onItemClick(View view, Object data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }

    public MenuAdapter(Context context, List<Theme> data, int currentThemeId) {
        this.data = data;
        this.currentThemeId = currentThemeId;
        this.mContext = context;
    }

    public void setCurrentThemeId(int currentThemeId) {
        this.currentThemeId = currentThemeId;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(data.get(position).getName());

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, data.get(position));
                }
            });
        }
        if (data.get(position).getId() == currentThemeId) {
            ((LinearLayout) holder.mTextView.getParent()).setBackgroundColor(mContext.getResources()
                    .getColor(R.color.menu_item_selected_background));
        } else {
            ((LinearLayout) holder.mTextView.getParent()).setBackgroundColor(mContext.getResources()
                    .getColor(android.R.color.transparent));
        }

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


}
