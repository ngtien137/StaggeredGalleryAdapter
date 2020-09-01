package com.lhd.galleryadapterdemo.data

import androidx.lifecycle.MutableLiveData
import com.base.baselibrary.utils.getApplication
import com.base.baselibrary.utils.media_provider.getMedia
import com.base.baselibrary.utils.postSelf
import com.base.baselibrary.viewmodel.Event
import com.base.baselibrary.views.ext.doJob
import com.lhd.galleryadapterdemo.models.AppMedia
import com.lhd.galleryadapterdemo.models.AppPhoto
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MediaRepository {
    val liveListMedia by lazy {
        MutableLiveData<List<AppMedia>>()
    }

    val liveListMediaSelected by lazy {
        MutableLiveData<Stack<AppMedia>>(Stack())
    }

    fun loadListMedia(forceLoad: Boolean = false, eventLoading: MutableLiveData<Event>? = null) {
        if (forceLoad || liveListMedia.value.isNullOrEmpty()) {
            eventLoading?.value = Event(true)
            doJob({
                val listMedia = ArrayList<AppMedia>()
                val listImages =
                    getApplication().getMedia(AppPhoto::class.java, onCheckIfAddItem = {
                        File(it.path).exists() && !it.path.endsWith("gif", true)
                    })
                listMedia.addAll(listImages)
                listMedia
            }, {
                eventLoading?.value = Event(false)
                liveListMedia.value = it
            }, dispathcherOut = Dispatchers.Main)
        }
    }

    fun clearListSelected() {
        liveListMediaSelected.value?.clear()
        liveListMediaSelected.postSelf()
    }
}