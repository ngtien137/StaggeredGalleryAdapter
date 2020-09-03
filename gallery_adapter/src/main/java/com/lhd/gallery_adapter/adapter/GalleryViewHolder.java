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
        Log.e("AdapterPosition: ", "" + getAdapterPosition());
        GroupMedia<T> mediaData = galleryAdapter.getGroupData().get(getAdapterPosition());
        List<T> medias = mediaData.getListMedia();
        List<GalleryGridData> gridDatas = mediaData.getListGalleryGridData();
        FrameLayout groupLayout = (FrameLayout) itemView;
        RecyclerView recyclerView = galleryAdapter.getRecyclerView();
        groupLayout.removeAllViews();
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
            binding.getRoot().setX(recyclerView.getWidth() * gridData.getxPercent());
            binding.getRoot().setY(recyclerView.getWidth() * gridData.getyPercent());
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
        }
    }

}

