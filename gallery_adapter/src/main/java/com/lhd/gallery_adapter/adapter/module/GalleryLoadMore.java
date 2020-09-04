package com.lhd.gallery_adapter.adapter.module;

import androidx.annotation.LayoutRes;

public @interface GalleryLoadMore {
    @LayoutRes
    int layoutLoadMoreResource() default -1;
    boolean enableLayoutLoadMore() default false;
}
