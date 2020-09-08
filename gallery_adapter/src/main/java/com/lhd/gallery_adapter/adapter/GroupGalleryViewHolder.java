package com.lhd.gallery_adapter.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class GroupGalleryViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public ViewDataBinding getBinding() {
        return binding;
    }

    public GroupGalleryViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
