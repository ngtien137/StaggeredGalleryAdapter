package com.lhd.galleryadapterdemo.adapter

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.lhd.gallery_adapter.adapter.GalleryAdapter
import com.lhd.gallery_adapter.adapter.module.GalleryLoadMore
import com.lhd.gallery_adapter.adapter.module.GallerySelect
import com.lhd.galleryadapterdemo.R
import com.lhd.galleryadapterdemo.models.AppPhoto
import kotlinx.android.synthetic.main.item_photo.view.*

@GalleryLoadMore(layoutLoadMoreResource = R.layout.item_load_more)
@GallerySelect(
    layoutHandleCheck = R.id.cvItem,
    enableMultiSelect = true,
    enableSelectedModeByLongClick = true
)
class CollageAdapter(borderPercent: Float) :
    GalleryAdapter<AppPhoto>(R.layout.item_photo, borderPercent) {

    override fun onBindItemViewHolder(
        item: AppPhoto,
        groupPosition: Int,
        binding: ViewDataBinding
    ) {
        val scale = if (isItemSelected(item)) 0.9f else 1f
        binding.root.cvItem.scaleX = scale
        binding.root.cvItem.scaleY = scale
    }

}