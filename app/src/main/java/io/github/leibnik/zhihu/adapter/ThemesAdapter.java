package io.github.leibnik.zhihu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.github.leibnik.zhihu.entity.Themes;
import io.github.leibnik.zhihu.R;

/**
 * Created by Droidroid on 2016/3/22.
 */
public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ViewHolder> {

    private List<Themes> data;
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

    public ThemesAdapter(Context context, List<Themes> data, int currentThemeId) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_themes, parent, false);
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
