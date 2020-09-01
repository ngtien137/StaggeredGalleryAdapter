package com.lhd.galleryadapterdemo.models

import android.net.Uri
import com.base.baselibrary.utils.media_provider.MediaModelBase
import com.lhd.galleryadapterdemo.utils.Const
import java.util.*

abstract class AppMedia(
    open var id: Long = -1,
    open var path: String = "",
    open var date: Long = Date().time,
    open var type: Int = Const.TYPE_IMAGE
) : MediaModelBase(){
    override fun toString(): String {
        return "AppMedia(id=$id, path='$path', date=$date)"
    }
}