package com.lhd.gallery_adapter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.lhd.gallery_adapter.BR;
import com.lhd.gallery_adapter.model.GroupMedia;
import com.lhd.gallery_adapter.model.IMediaData;
import com.lhd.gallery_adapter.utils.CollageGroupLayoutUtils;

import java.util.List;

class GalleryViewHolder extends RecyclerView.ViewHolder {

    private View itemView;

    public float getBorderPercent() {
        return CollageGroupLayoutUtils.BORDER_PERCENT;
    }

    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public <T extends IMediaData> void applyGroupData(List<GroupMedia<T>> list, int layoutResource, LayoutInflater layoutInflater) {
        GroupMedia<T> mediaData = list.get(getAdapterPosition());
        List<T> medias = mediaData.getListMedia();
        for (int i = 0; i < medias.size(); i++) {
            T item = medias.get(i);
            ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, layoutResource, (ViewGroup) itemView, true);
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
        }
    }
}

