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

    public float getWidthPercent() {
        return widthPercent;
    }

    public void setWidthPercent(float widthPercent) {
        this.widthPercent = widthPercent;
    }

    public float getHeightPercent() {
        return heightPercent;
    }

    public void setHeightPercent(float heightPercent) {
        this.heightPercent = heightPercent;
    }

    public float getxPercent() {
        return xPercent;
    }

    public void setxPercent(float xPercent) {
        this.xPercent = xPercent;
    }

    public float getyPercent() {
        return yPercent;
    }

    public void setyPercent(float yPercent) {
        this.yPercent = yPercent;
    }
}
