package com.lhd.gallery_adapter.adapter.module;

import androidx.annotation.LayoutRes;

public @interface GallerySelect {
    @LayoutRes
    int layoutHandleCheck() default -1;

    boolean enableSelectedModeByLongClick() default true;

    boolean enableUnSelect() default true;

    boolean enableMultiSelect() default false;

    boolean enableSelectItemMultipleTime() default false;

    boolean disableSelectModeWhenEmpty() default true;

    boolean validCheckAgainAfterEnableSelectedByLongClick() default true;
}
