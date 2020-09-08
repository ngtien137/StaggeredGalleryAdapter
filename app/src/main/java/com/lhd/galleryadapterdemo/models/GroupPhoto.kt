package com.lhd.galleryadapterdemo.models

import com.lhd.gallery_adapter.model.IGroupMediaInfo
import com.lhd.gallery_adapter.model.IMediaData

class GroupPhoto : IGroupMediaInfo<AppPhoto> {

    var titleTime: String = ""
    var listPhoto = mutableListOf<AppPhoto>()

    override fun getListGroupItems(): MutableList<AppPhoto> {
        return listPhoto
    }

    override fun getKeyGroup(): String {
        return titleTime
    }

    override fun setGroupInfo(key: String, listGroupItems: MutableList<AppPhoto>) {
        this.titleTime = key
        this.listPhoto = listGroupItems
    }
}