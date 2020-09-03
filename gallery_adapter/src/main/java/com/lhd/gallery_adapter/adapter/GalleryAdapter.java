package com.lhd.gallery_adapter.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lhd.gallery_adapter.R;
import com.lhd.gallery_adapter.model.GroupMedia;
import com.lhd.gallery_adapter.model.IMediaData;
import com.lhd.gallery_adapter.utils.CollageGroupLayoutUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GalleryAdapter<T extends IMediaData> extends RecyclerView.Adapter<GalleryViewHolder> {

    private List<GroupMedia<T>> listGroup;
    private List<T> data;
    private int layoutResource = -1;
    private LayoutInflater layoutInflater;
    private int maxColumns = 3;
    private RecyclerView recyclerView;

    public GalleryAdapter(int layoutResource, float borderPercent) {
        this.data = new ArrayList<>();
        this.layoutResource = layoutResource;
        listGroup = new ArrayList<>();
        CollageGroupLayoutUtils.BORDER_PERCENT = borderPercent;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new GalleryViewHolder(layoutInflater.inflate(R.layout.item_group_media, parent, false));
    }

    @Override
    public int getItemCount() {
        return listGroup.size();
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.applyToAdapter(this);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public float getBorderPercent() {
        return CollageGroupLayoutUtils.BORDER_PERCENT;
    }

    public void setBorderPercent(float borderPercent) {
        CollageGroupLayoutUtils.BORDER_PERCENT = borderPercent;
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

    public List<GroupMedia<T>> getGroupData(){
        return listGroup;
    }

    public LayoutInflater getLayoutInflater(){
        return layoutInflater;
    }

    public int getLayoutResource(){
        return layoutResource;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void generateListGroup(List<T> data) {
        if (data.isEmpty())
            return;
        Stack<T> stackData = new Stack<>();
        stackData.addAll(data);
        List<GroupMedia<T>> listGroupMedia = new ArrayList<>();
        while (!stackData.empty()) {
            GroupMedia<T> groupMedia = new GroupMedia<>();
            int recommendSize = 1;
            if (stackData.size() > maxColumns) {
                recommendSize = maxColumns;
            }
            List<T> subList = new ArrayList<>();
            String keyCollage = "";
            do {
                subList.clear();
                if (stackData.size()==1){
                    subList.add(stackData.get(0));
                }else{
                    for (int i = 0;i<recommendSize;i++){
                        subList.add(stackData.get(i));
                    }
                }
                keyCollage = GroupMedia.getKeyCollage(subList);
                if (CollageGroupLayoutUtils.getLayoutSupport(recommendSize, keyCollage) == null) {
                    groupMedia.setListMedia(stackData.get(0));
                    stackData.remove(stackData.get(0));
                } else {
                    groupMedia.setListMedia(subList);
                    stackData.removeAll(subList);
                }
                if (CollageGroupLayoutUtils.getLayoutSupport(recommendSize, keyCollage)!=null){
                    groupMedia.setListGalleryGridData(CollageGroupLayoutUtils.getLayoutSupport(recommendSize, keyCollage));
                }
                recommendSize--;
            }while (recommendSize>0&&CollageGroupLayoutUtils.getLayoutSupport(recommendSize+1, keyCollage)==null);
            listGroupMedia.add(groupMedia);
        }
        listGroup = listGroupMedia;
    }
}

