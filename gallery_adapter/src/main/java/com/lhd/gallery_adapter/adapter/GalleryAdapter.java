package com.lhd.gallery_adapter.adapter;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lhd.gallery_adapter.R;
import com.lhd.gallery_adapter.adapter.listener.IGalleryAdapterListener;
import com.lhd.gallery_adapter.adapter.module.GalleryLoadMore;
import com.lhd.gallery_adapter.model.GroupMedia;
import com.lhd.gallery_adapter.model.IMediaData;
import com.lhd.gallery_adapter.utils.CollageGroupLayoutUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GalleryAdapter<T extends IMediaData> extends RecyclerView.Adapter<GalleryViewHolder> {

    private static int MAX_COLUMNS = 3;

    private List<GroupMedia<T>> listGroup;
    private List<T> data;
    private int layoutResource = -1;
    private LayoutInflater layoutInflater;
    private int collageColums = MAX_COLUMNS;
    private RecyclerView recyclerView;
    private boolean isShowLoadMore = false;

    private GalleryLoadMore aGalleryLoadMore;

    private IGalleryAdapterListener listener;

    public GalleryAdapter(int layoutResource, float borderPercent) {
        this.data = new ArrayList<>();
        this.layoutResource = layoutResource;
        listGroup = new ArrayList<>();
        CollageGroupLayoutUtils.BORDER_PERCENT = borderPercent;
        Annotation[] listAnnotation = getClass().getDeclaredAnnotations();
        for (int i = 0; i < listAnnotation.length; i++) {
            Annotation annotation = listAnnotation[i];
            if (annotation instanceof GalleryLoadMore) {
                aGalleryLoadMore = (GalleryLoadMore) annotation;
            }
        }
    }

    public GalleryAdapter(int layoutResource) {
        this(layoutResource, 0.0f);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listGroup.size() - 1) {
                    if (!isShowLoadMore) {
                        if (aGalleryLoadMore != null) {
                            showLoadMore(true);
                            if (listener != null) {
                                listener.onHandleLoadMore();
                            }
                        }
                    }
                }
            }
        });
    }

    public void showLoadMore(boolean show) {
        if (show) {
            isShowLoadMore = true;
            if (aGalleryLoadMore.enableLayoutLoadMore()) {
                listGroup.add(null);
                try {
                    notifyItemInserted(listGroup.size() - 1);
                } catch (Exception e) {

                }
            }
        } else {
            isShowLoadMore = false;
            if (aGalleryLoadMore.enableLayoutLoadMore()) {
                if (!listGroup.isEmpty() && listGroup.get(listGroup.size() - 1) == null) {
                    listGroup.remove(listGroup.get(listGroup.size() - 1));
                    try {
                        notifyItemRemoved(listGroup.size() - 1);
                    } catch (Exception e) {

                    }
                }
            }
        }
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
        setData(data, collageColums, true);
    }

    public void setData(List<T> data, boolean notifyDataAfterGenerate) {
        setData(data, collageColums, notifyDataAfterGenerate);
    }

    public void setData(List<T> data, int columns, boolean notifyDataAfterGenerate) {
        this.data = data;
        generateListGroup(data, columns);
        if (notifyDataAfterGenerate) {
            (new Handler(Looper.getMainLooper())).post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
        showLoadMore(false);
    }


    public List<GroupMedia<T>> getGroupData() {
        return listGroup;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public int getLayoutResource() {
        return layoutResource;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setListener(IGalleryAdapterListener listener) {
        this.listener = listener;
    }

    public GalleryLoadMore getAnnotationLoadMore() {
        return aGalleryLoadMore;
    }

    private void setCollageColumns(int columns) {
        if (columns > MAX_COLUMNS) {
            collageColums = MAX_COLUMNS;
        } else if (columns < 1) {
            collageColums = 1;
        } else {
            collageColums = columns;
        }
    }

    public void notifyDataToLastFromPosition(int position) {
        notifyItemRangeChanged(position, listGroup.size() - position);
    }

    public void notifyDataForLoadMore(int oldSizeBeforeLoadMore) {
        int position = oldSizeBeforeLoadMore - collageColums;
        notifyDataToLastFromPosition(position);
    }

    public void generateListGroup(List<T> data, int columns) {
        setCollageColumns(columns);
        generateListGroup(data);
    }

    public void generateListGroup(List<T> data) {
        if (data.isEmpty())
            return;
        CollageGroupLayoutUtils.loadSupportedLayouts();
        Stack<T> stackData = new Stack<>();
        stackData.addAll(data);
        List<GroupMedia<T>> listGroupMedia = new ArrayList<>();
        while (!stackData.empty()) {
            GroupMedia<T> groupMedia = new GroupMedia<>();
            int recommendSize = 1;
            if (stackData.size() > collageColums) {
                recommendSize = collageColums;
            }
            List<T> subList = new ArrayList<>();
            String keyCollage = "";
            do {
                subList.clear();
                if (stackData.size() == 1) {
                    subList.add(stackData.get(0));
                } else {
                    for (int i = 0; i < recommendSize; i++) {
                        subList.add(stackData.get(i));
                    }
                }
                keyCollage = GroupMedia.getKeyCollage(subList);
                if (CollageGroupLayoutUtils.getLayoutSupport(recommendSize, keyCollage) != null) {
                    groupMedia.setListGalleryGridData(CollageGroupLayoutUtils.getLayoutSupport(recommendSize, keyCollage));
                    break;
                }
                recommendSize--;
            } while (recommendSize > 0 && CollageGroupLayoutUtils.getLayoutSupport(recommendSize + 1, keyCollage) == null);
            groupMedia.setListMedia(subList);
            stackData.removeAll(subList);
            listGroupMedia.add(groupMedia);
        }
        listGroup = listGroupMedia;
    }
}

