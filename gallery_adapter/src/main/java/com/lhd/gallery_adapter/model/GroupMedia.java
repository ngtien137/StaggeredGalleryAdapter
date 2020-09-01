package com.lhd.gallery_adapter.model;

import java.util.ArrayList;
import java.util.List;

public class GroupMedia<T extends IMediaData> {

    private List<T> listMedia = new ArrayList<>();

    private String keyCollage = "";

    public List<T> getListMedia() {
        return listMedia;
    }

    public void setListMedia(List<T> listMedia) {
        this.listMedia = listMedia;
    }
}
