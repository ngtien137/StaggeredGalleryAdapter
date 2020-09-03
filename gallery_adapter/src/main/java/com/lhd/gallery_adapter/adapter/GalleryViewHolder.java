package com.lhd.gallery_adapter.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.lhd.gallery_adapter.BR;
import com.lhd.gallery_adapter.R;
import com.lhd.gallery_adapter.model.GroupMedia;
import com.lhd.gallery_adapter.model.IMediaData;
import com.lhd.gallery_adapter.utils.CollageGroupLayoutUtils;
import com.lhd.gallery_adapter.utils.GalleryGridData;

import java.util.List;
import java.util.Stack;

class GalleryViewHolder extends RecyclerView.ViewHolder {

    public float getBorderPercent() {
        return CollageGroupLayoutUtils.BORDER_PERCENT;
    }

    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public <T extends IMediaData> void applyToAdapter(GalleryAdapter<T> galleryAdapter) {
        FrameLayout groupLayout = (FrameLayout) itemView;
        RecyclerView recyclerView = galleryAdapter.getRecyclerView();
        groupLayout.removeAllViews();
        GroupMedia<T> mediaData = galleryAdapter.getGroupData().get(getAdapterPosition());
        if (mediaData == null && getAdapterPosition() == galleryAdapter.getGroupData().size() - 1) {
            int layoutId = galleryAdapter.getAnnotationLoadMore().layoutLoadMoreResource();
            if (layoutId == -1)
                layoutId = R.layout.item_load_more_default;
            View v = LayoutInflater.from(recyclerView.getContext()).inflate(layoutId, null, false);
            groupLayout.addView(v);
            return;
        }
        List<T> medias = mediaData.getListMedia();
        List<GalleryGridData> gridDatas = mediaData.getListGalleryGridData();
        Stack<T> stackData = new Stack<>();
        stackData.addAll(medias);
        for (int i = 0; i < gridDatas.size(); i++) {
            GalleryGridData gridData = gridDatas.get(i);
            T item = null;
            for (T media : stackData) {
                if (media.getMediaSize() == gridData.getMediaSize()) {
                    item = media;
                    break;
                }
            }
            stackData.remove(item);
            ViewDataBinding binding = DataBindingUtil.inflate(galleryAdapter.getLayoutInflater(), galleryAdapter.getLayoutResource(), (ViewGroup) itemView, false);
            Log.e("Test: ", "RecyclerView: " + galleryAdapter.getRecyclerView().getWidth());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.getRoot().getLayoutParams();
            layoutParams.width = (int) (recyclerView.getWidth() * gridData.getWidthPercent());
            layoutParams.height = (int) (recyclerView.getWidth() * gridData.getHeightPercent());
            binding.getRoot().setLayoutParams(layoutParams);
            groupLayout.addView(binding.getRoot());
            layoutParams.setMargins((int)(recyclerView.getWidth() * gridData.getxPercent()),(int)(recyclerView.getWidth() * gridData.getyPercent()),0,(int)(recyclerView.getWidth()*galleryAdapter.getBorderPercent()));
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
        }
    }

}

