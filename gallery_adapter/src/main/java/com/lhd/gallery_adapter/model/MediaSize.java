package com.lhd.gallery_adapter.model;

public enum MediaSize {
    VERTICAL, HORIZONTAL, SQUARE;

    public static MediaSize getMediaSize(int width, int height) {
        return getMediaSize((float) width, (float) height);
    }

    public static MediaSize getMediaSize(float width, float height) {
        if (width / height > 1.2f) {
            return HORIZONTAL;
        } else if (width / height < 0.8f) {
            return VERTICAL;
        } else
            return SQUARE;
    }
}
