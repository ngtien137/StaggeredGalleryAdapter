package com.lhd.gallery_adapter.utils;

import com.lhd.gallery_adapter.model.MediaSize;

public class GalleryGridData {
    private float widthPercent;
    private float heightPercent;
    private float xPercent;
    private float yPercent;

    public GalleryGridData(float widthPercent, float heightPercent, float xPercent, float yPercent) {
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
        this.xPercent = xPercent;
        this.yPercent = yPercent;
    }

    public MediaSize getMediaSize() {
        return MediaSize.getMediaSize(widthPercent, heightPercent);
    }
}
