package com.lhd.gallery_adapter.adapter;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lhd.gallery_adapter.R;
import com.lhd.gallery_adapter.adapter.listener.IGalleryAdapterListener;
import com.lhd.gallery_adapter.adapter.module.GalleryLoadMore;
import com.lhd.gallery_adapter.adapter.module.GallerySelect;
import com.lhd.gallery_adapter.model.GroupMedia;
import com.lhd.gallery_adapter.model.IMediaData;
import com.lhd.gallery_adapter.utils.CollageGroupLayoutUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GalleryAdapter<T extends IMediaData> extends RecyclerView.Adapter<GalleryViewHolder> {

    //Max columns in a row current supported
    private static int MAX_COLUMNS = 3;

    //List Group of medias
    private List<GroupMedia<T>> listGroup;
    //List medias
    private List<T> data;
    //Layout for media
    private int layoutResource = -1;
    private LayoutInflater layoutInflater;

    /**
     * collageColumns define how many medias can group with each others
     * Current support max = MAX_COLUMNS
     * {@link #MAX_COLUMNS
     */
    private int collageColumns = MAX_COLUMNS;

    private RecyclerView recyclerView;
    private boolean isShowLoadMore = false;

    private GalleryLoadMore annotationGalleryLoadMore;
    private GallerySelect annotationSelect;

    private IGalleryAdapterListener listener;

    /**
     * For select support
     */
    private MutableLiveData<Boolean> liveModeSelected = new MutableLiveData<>();
    private MutableLiveData<Stack<T>> liveListSelected = new MutableLiveData<>();
    private int lastSelectedPosition = -1;

    public GalleryAdapter(int layoutResource, float borderPercent) {
        liveModeSelected.setValue(false);
        liveListSelected.setValue(new Stack<>());
        this.data = new ArrayList<>();
        this.layoutResource = layoutResource;
        listGroup = new ArrayList<>();
        CollageGroupLayoutUtils.BORDER_PERCENT = borderPercent;
        Annotation[] listAnnotation = getClass().getDeclaredAnnotations();
        for (int i = 0; i < listAnnotation.length; i++) {
            Annotation annotation = listAnnotation[i];
            if (annotation instanceof GalleryLoadMore) {
                annotationGalleryLoadMore = (GalleryLoadMore) annotation;
            } else if (annotation instanceof GallerySelect) {
                annotationSelect = (GallerySelect) annotation;
            }
        }
    }

    public GalleryAdapter(int layoutResource) {
        this(layoutResource, 0.0f);
    }

    public void setListSelected(MutableLiveData<Stack<T>> liveStackSelected) {
        liveListSelected = liveStackSelected;
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
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {

        }
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
                        if (annotationGalleryLoadMore != null) {
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
            if (annotationGalleryLoadMore.enableLayoutLoadMore()) {
                listGroup.add(null);
                try {
                    notifyItemInserted(listGroup.size() - 1);
                    recyclerView.scrollToPosition(listGroup.size() - 1);
                } catch (Exception e) {

                }
            }
        } else {
            isShowLoadMore = false;
            if (annotationGalleryLoadMore.enableLayoutLoadMore()) {
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
        setData(data, collageColumns, true);
    }

    public void setData(List<T> data, boolean notifyDataAfterGenerate) {
        setData(data, collageColumns, notifyDataAfterGenerate);
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

    public IGalleryAdapterListener getListener() {
        return listener;
    }

    public MutableLiveData<Boolean> getLiveModeSelected() {
        return liveModeSelected;
    }

    public MutableLiveData<Stack<T>> getListSelected() {
        return liveListSelected;
    }

    public GalleryLoadMore getAnnotationLoadMore() {
        return annotationGalleryLoadMore;
    }

    public GallerySelect getAnnotationSelect() {
        return annotationSelect;
    }

    public void changeModeSelect(boolean select) {
        liveModeSelected.setValue(select);
    }

    private void setCollageColumns(int columns) {
        if (columns > MAX_COLUMNS) {
            collageColumns = MAX_COLUMNS;
        } else if (columns < 1) {
            collageColumns = 1;
        } else {
            collageColumns = columns;
        }
    }

    public void notifyDataToLastFromPosition(int position) {
        notifyItemRangeChanged(position, listGroup.size() - position);
    }

    public void notifyDataForLoadMore(int oldSizeBeforeLoadMore) {
        int position = oldSizeBeforeLoadMore - collageColumns;
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
            if (stackData.size() > collageColumns) {
                recommendSize = collageColumns;
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

