package com.lhd.gallery_adapter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lhd.gallery_adapter.R;
import com.lhd.gallery_adapter.model.GroupMedia;
import com.lhd.gallery_adapter.model.IMediaData;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter<T extends IMediaData> extends RecyclerView.Adapter<GalleryViewHolder> {

    private float borderPercent = 0.01f;
    private List<GroupMedia<T>> listGroup;
    private List<T> data;
    private int layoutResource = -1;

    public GalleryAdapter(int layoutResource) {
        this.data = new ArrayList<>();
        this.layoutResource = layoutResource;
        listGroup = new ArrayList<>();
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_media, parent, false), borderPercent);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.applyGroupData(listGroup, layoutResource);
    }

    public float getBorderPercent() {
        return borderPercent;
    }

    public void setBorderPercent(float borderPercent) {
        this.borderPercent = borderPercent;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        generateListGroup(data);
        notifyDataSetChanged();
    }

    public void generateListGroup(List<T> data) {
        if (data.isEmpty())
            return;
        List<GroupMedia<T>> listGroupMedia = new ArrayList<>();
        int index = 0;
        while (index < data.size()) {
            IMediaData mediaData = data.get(index);
        }
    }
}

