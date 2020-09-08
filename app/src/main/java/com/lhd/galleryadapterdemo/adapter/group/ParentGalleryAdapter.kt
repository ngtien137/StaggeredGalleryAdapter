package com.lhd.galleryadapterdemo.adapter.group

import com.lhd.gallery_adapter.adapter.GalleryAdapter
import com.lhd.gallery_adapter.adapter.GroupGalleryAdapter
import com.lhd.gallery_adapter.adapter.module.GalleryLoadMore
import com.lhd.galleryadapterdemo.R
import com.lhd.galleryadapterdemo.models.AppPhoto
import com.lhd.galleryadapterdemo.models.GroupPhoto

@GalleryLoadMore(layoutLoadMoreResource = R.layout.item_load_more,enableLayoutLoadMore = true)
class ParentGalleryAdapter : GroupGalleryAdapter<GroupPhoto, AppPhoto>(R.layout.item_group_photo) {
    override fun onCreateChildGalleryAdapter(): GalleryAdapter<AppPhoto> {
        return ChildGalleryAdapter(0f)
    }

    override fun onCreateGroup(): GroupPhoto {
        return GroupPhoto()
    }

}