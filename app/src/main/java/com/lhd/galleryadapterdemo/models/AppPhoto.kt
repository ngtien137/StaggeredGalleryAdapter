package com.lhd.galleryadapterdemo.models

import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import com.base.baselibrary.utils.media_provider.MediaInfo
import com.lhd.gallery_adapter.model.IMediaData
import com.lhd.gallery_adapter.model.MediaSize
import com.lhd.galleryadapterdemo.utils.Const
import java.util.*

data class AppPhoto(
    @MediaInfo(MediaStore.Files.FileColumns._ID)
    override var id: Long = -1,
    @MediaInfo(MediaStore.Files.FileColumns.DATA)
    override var path: String = "",
    @MediaInfo(MediaStore.Files.FileColumns.DATE_MODIFIED)
    override var date: Long = Date().time,
    override var type: Int = Const.TYPE_IMAGE
) : AppMedia(id, path, date, type), IMediaData {
    override fun getUri(): Uri {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    var mediaSizeData: MediaSize? = null

    override fun getMediaSize(): MediaSize {
        if (mediaSizeData == null) {
            val bitmap = BitmapFactory.decodeFile(path)
            mediaSizeData = MediaSize.getMediaSize(bitmap?.width ?: 1, bitmap?.height ?: 1)
        }
        return mediaSizeData!!
    }

    override fun getMediaDataSource(): String {
        return path
    }
}


