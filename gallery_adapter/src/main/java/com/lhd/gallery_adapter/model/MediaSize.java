package com.lhd.gallery_adapter.model;

public enum MediaSize {
    VERTICAL, HORIZONTAL, SQUARE;

    public static MediaSize getMediaSize(Number width, Number height) {
        float w = (float) width;
        float h = (float) height;
        if (w / h > 1.2f) {
            return HORIZONTAL;
        } else if (w / h < 0.8f) {
            return VERTICAL;
        } else
            return SQUARE;
    }
}
