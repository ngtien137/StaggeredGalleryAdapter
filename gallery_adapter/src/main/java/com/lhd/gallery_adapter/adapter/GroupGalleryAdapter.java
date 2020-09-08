package com.lhd.gallery_adapter.adapter;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lhd.gallery_adapter.BR;
import com.lhd.gallery_adapter.adapter.listener.IGalleryAdapterListener;
import com.lhd.gallery_adapter.adapter.module.GalleryLoadMore;
import com.lhd.gallery_adapter.model.IGroupMediaInfo;
import com.lhd.gallery_adapter.model.IMediaData;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public abstract class GroupGalleryAdapter<G extends IGroupMediaInfo<M>, M extends IMediaData> extends RecyclerView.Adapter<GroupGalleryViewHolder> {

    private List<M> data;
    private List<G> groups;
    private Map<String, List<M>> mapGroup;
    private int layoutResource = -1;
    private LayoutInflater layoutInflater;
    private MutableLiveData<Stack<M>> liveListSelected;
    private MutableLiveData<Boolean> liveModeSelected;
    private IGalleryAdapterListener<M> listener;
    private Map<G, GalleryAdapter<M>> mapAdapters;
    private RecyclerView recyclerView;
    private boolean isShowLoadMore = false;

    private GalleryLoadMore annotationGalleryLoadMore;

    public GroupGalleryAdapter(int layoutResource) {
        this.layoutResource = layoutResource;
        data = new ArrayList<>();
        liveListSelected = new MutableLiveData<>();
        liveModeSelected = new MutableLiveData<>();
        liveModeSelected.setValue(false);
        mapAdapters = new HashMap();
        Annotation[] listAnnotation = getClass().getDeclaredAnnotations();
        for (int i = 0; i < listAnnotation.length; i++) {
            Annotation annotation = listAnnotation[i];
            if (annotation instanceof GalleryLoadMore) {
                annotationGalleryLoadMore = (GalleryLoadMore) annotation;
                break;
            }
        }
    }

    @NonNull
    @Override
    public GroupGalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, layoutResource, parent, false);
        return new GroupGalleryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupGalleryViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        G item = getGroup(pos);
        ViewDataBinding binding = holder.getBinding();
        onPreExecuteBinding(binding);
        GalleryAdapter<M> adapter = onCreateChildGalleryAdapter();
        connectWithChildAdapter(adapter, holder);
        mapAdapters.put(item, adapter);
        binding.setVariable(BR.item, item);
        binding.setVariable(BR.adapter, adapter);
        binding.setLifecycleOwner((LifecycleOwner) holder.itemView.getContext());
        binding.executePendingBindings();
    }

    public void onPreExecuteBinding(ViewDataBinding binding) {

    }

    //Define how to create children adapter
    public abstract GalleryAdapter<M> onCreateChildGalleryAdapter();

    //Define how to create empty group object
    public abstract G onCreateGroup();

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.VERTICAL, false));
        if (annotationGalleryLoadMore != null && annotationGalleryLoadMore.enableLayoutLoadMore()) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (linearLayoutManager != null) {
                        int lastCompleteShownPosition = linearLayoutManager.findLastVisibleItemPosition();
                        if (lastCompleteShownPosition == groups.size() - 1) {
                            if (!isShowLoadMore) {
                                if (annotationGalleryLoadMore != null) {
                                    if (listener != null) {
                                        listener.onHandleLoadMore();
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public G getGroup(int position) {
        return groups.get(position);
    }

    public void setListener(IGalleryAdapterListener<M> listener) {
        this.listener = listener;
    }

    public IGalleryAdapterListener<M> getListener() {
        return listener;
    }

    public void setListSelected(MutableLiveData<Stack<M>> liveListSelected) {
        this.liveListSelected = liveListSelected;
    }

    public void connectWithChildAdapter(GalleryAdapter<M> adapter, GroupGalleryViewHolder holder) {
        adapter.setListSelected(liveListSelected);
        adapter.setListener(listener);
        adapter.setLiveModeSelected(liveModeSelected);
        adapter.setData(getGroup(holder.getAdapterPosition()).getListGroupItems(), true);
    }

    public void setData(List<M> data, boolean notifyDataAfterSetData) {
        this.data = data;
        if (data.isEmpty())
            return;
        mapGroup = new HashMap<>();
        groups = new ArrayList<>();
        for (M media : data) {
            if (mapGroup.get(media.getKeyGroup()) == null) {
                List<M> list = new ArrayList<>();
                list.add(media);
                G group = onCreateGroup();
                group.setGroupInfo(media.getKeyGroup(), list);
                groups.add(group);
                mapGroup.put(media.getKeyGroup(), list);
            } else {
                mapGroup.get(media.getKeyGroup()).add(media);
            }
        }
        if (notifyDataAfterSetData) {
            (new Handler(Looper.getMainLooper())).post(this::notifyDataSetChanged);
        }
    }
}
