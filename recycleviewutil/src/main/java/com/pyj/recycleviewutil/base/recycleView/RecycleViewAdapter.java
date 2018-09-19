package com.pyj.recycleviewutil.base.recycleView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class RecycleViewAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected List<T> dataSource;
    protected AdapterListener<T, VH> adapterListener;

    public List<T> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<T> dataSource) {
        this.dataSource = dataSource;
    }

    public AdapterListener<T, VH> getAdapterListener() {
        return adapterListener;
    }

    public void setAdapterListener(AdapterListener<T, VH> adapterListener) {
        this.adapterListener = adapterListener;
    }

    public void reset(List<T> dataSource) {
        this.dataSource = dataSource;
        this.notifyDataSetChanged();
    }

    public void resetPositionItem(int position, T t) {
        this.notifyItemChanged(position, t);
    }

    public void clear() {
        this.dataSource.clear();
        this.notifyDataSetChanged();
    }

    public void appendDataSource(List<T> dataSource) {
        if (dataSource != null && dataSource.size() > 0) {
            int oldSize = this.dataSource.size();
            if (oldSize > 0) {
                this.dataSource.addAll(dataSource);
                notifyItemRangeInserted(oldSize, dataSource.size());
            } else {
                this.dataSource = dataSource;
                this.notifyDataSetChanged();
            }
        }
    }

    public int getItemCount() {
        return dataSource != null ? dataSource.size() : 0;
    }

    public abstract int getItemLayout();

    public abstract VH createViewHolder(View v);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getItemLayout(),
                        parent,
                        false);

        final VH holder = createViewHolder(view);

        onBeforeCreateViewHolder(holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterListener != null) {
                    Object tag = v.getTag();
                    if (tag == null) {
                        return;
                    }

                    int position = (int) tag;
                    int size = dataSource.size();
                    if (position >= 0 && size > 0 && position < size) {
                        T item = dataSource.get(position);
                        adapterListener.onItemClick(holder, item);
                    }
                }
            }
        });

        onAfterCreateViewHolder(holder);

        return holder;
    }


    public void onBeforeCreateViewHolder(VH holder) {
    }

    public void onAfterCreateViewHolder(VH holder) {
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        int size = dataSource.size();
        if (position >= 0 && size > 0 && position < size) {
            T item = dataSource.get(position);
            onBeforeBindViewHolder(item, holder, position);

            holder.itemView.setTag(position);
            if (adapterListener != null) {
                adapterListener.onItemRender(holder, item);
            }

            onAfterBindViewHolder(item, holder, position);
        }
    }

    public void onBeforeBindViewHolder(T item, VH holder, int position) {
    }

    public void onAfterBindViewHolder(T item, VH holder, int position) {
    }

    public interface AdapterListener<T, VH extends RecyclerView.ViewHolder> {
        void onItemClick(VH holder, T item);

        void onItemRender(VH holder, T item);
    }

    public static class AbstractAdapterListener<T, VH extends RecyclerView.ViewHolder>
            implements AdapterListener<T, VH> {

        @Override
        public void onItemClick(VH holder, T item) {
        }

        @Override
        public void onItemRender(VH holder, T item) {
        }
    }
}
