package com.lhd.galleryadapterdemo.models

import android.net.Uri
import android.provider.MediaStore
import com.base.baselibrary.utils.media_provider.MediaInfo
import com.lhd.galleryadapterdemo.utils.Const
import java.util.*

data class AppVideo(
    @MediaInfo(MediaStore.Files.FileColumns._ID)
    override var id: Long = -1,
    @MediaInfo(MediaStore.Files.FileColumns.DATA)
    override var path: String = "",
    @MediaInfo(MediaStore.Files.FileColumns.DATE_MODIFIED)
    override var date: Long = Date().time,
    override var type: Int = Const.TYPE_VIDEO
) : AppMedia(id, path, date, type) {
    override fun getUri(): Uri {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    }
}