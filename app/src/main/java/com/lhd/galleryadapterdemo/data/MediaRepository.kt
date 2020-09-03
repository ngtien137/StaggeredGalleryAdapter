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
    val liveListPhoto by lazy {
        MutableLiveData<List<AppPhoto>>()
    }

    val liveListPhotoSelected by lazy {
        MutableLiveData<Stack<AppMedia>>(Stack())
    }

    fun loadListPhoto(
        itemCount: Int,
        forceLoad: Boolean = false,
        eventLoading: MutableLiveData<Event>? = null
    ) {
        if (forceLoad || liveListPhoto.value.isNullOrEmpty()) {
            eventLoading?.value = Event(true)
            doJob({
                val listMedia = ArrayList<AppPhoto>()
                val listImages =
                    getApplication().getMedia(
                        AppPhoto::class.java,
                        onCheckIfAddItem = { currentList, photo ->
                            File(photo.path).exists() && !photo.path.endsWith("gif", true)
                        },
                        onCheckContinueLoad = { currentList, photo ->
                            currentList.size < itemCount
                        })
                listMedia.addAll(listImages)
                listMedia
            }, {
                eventLoading?.value = Event(false)
                liveListPhoto.value = it
            }, dispathcherOut = Dispatchers.Main)
        }
    }

    fun clearListSelected() {
        liveListPhotoSelected.value?.clear()
        liveListPhotoSelected.postSelf()
    }
}