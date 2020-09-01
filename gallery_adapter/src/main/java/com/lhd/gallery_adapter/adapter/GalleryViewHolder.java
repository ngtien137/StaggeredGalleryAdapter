package com.lhd.gallery_adapter.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lhd.gallery_adapter.model.GroupMedia;
import com.lhd.gallery_adapter.model.IMediaData;

import java.util.List;

class GalleryViewHolder extends RecyclerView.ViewHolder {

    private float borderPercent = 0.01f;

    public GalleryViewHolder(@NonNull View itemView, float borderPercent) {
        super(itemView);
        this.borderPercent = borderPercent;
    }

    public <T extends IMediaData> void applyGroupData(List<GroupMedia<T>> list, int layoutResource){
        GroupMedia<T> mediaData = list.get(getAdapterPosition());
    }
}

