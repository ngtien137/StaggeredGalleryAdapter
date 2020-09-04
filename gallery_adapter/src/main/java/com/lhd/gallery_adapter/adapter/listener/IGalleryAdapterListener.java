package com.lhd.gallery_adapter.adapter.listener;

import android.view.View;

import com.lhd.gallery_adapter.model.IMediaData;

public interface IGalleryAdapterListener<T extends IMediaData> {
    void onHandleLoadMore();

    void onItemSelected(View viewHandleSelect, T item, int groupPosition, boolean selected);

    void onViewHandleCheckClicked(T item, int groupPosition);

    boolean onValidateBeforeCheckingItem(T item, int groupPosition);
}
