package com.nakul.meesho.view;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nakul.meesho.R;
import com.nakul.meesho.databinding.RowLayoutBinding;
import com.nakul.meesho.models.RepoPullModelClass;

public class PullRequestPageListAdapter extends PagedListAdapter<RepoPullModelClass, PullRequestPageListAdapter.ViewHolder> {

    PullRequestPageListAdapter() {
        super(RepoPullModelClass.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.row_layout, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.binding.setModel(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RowLayoutBinding binding;

        ViewHolder(RowLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

    }

}
