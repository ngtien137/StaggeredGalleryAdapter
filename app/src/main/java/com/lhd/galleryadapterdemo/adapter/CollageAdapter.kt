package com.lhd.galleryadapterdemo.adapter

import com.lhd.gallery_adapter.adapter.GalleryAdapter
import com.lhd.gallery_adapter.adapter.module.GalleryLoadMore
import com.lhd.galleryadapterdemo.R
import com.lhd.galleryadapterdemo.models.AppPhoto

@GalleryLoadMore
class CollageAdapter(borderPercent:Float) : GalleryAdapter<AppPhoto>(R.layout.item_photo, borderPercent) {

}