package com.lhd.gallery_adapter.adapter.module;

public @interface GalleryLoadMore {
    int layoutLoadMoreResource() default -1;
    boolean enableLayoutLoadMore() default true;
}
