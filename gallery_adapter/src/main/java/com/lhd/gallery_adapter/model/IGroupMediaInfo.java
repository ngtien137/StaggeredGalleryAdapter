package com.lhd.gallery_adapter.model;

import androidx.annotation.NonNull;

import java.util.List;

public interface IGroupMediaInfo<M extends IMediaData> {
    List<M> getListGroupItems();

    String getKeyGroup();

    void setGroupInfo(@NonNull String key,@NonNull List<M> listGroupItems);
}
