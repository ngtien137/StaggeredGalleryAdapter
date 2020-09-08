package com.lhd.galleryadapterdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.base.baselibrary.viewmodel.Auto
import com.base.baselibrary.viewmodel.Event
import com.lhd.galleryadapterdemo.data.MediaRepository
import com.lhd.galleryadapterdemo.models.AppPhoto
import java.util.*

class GroupViewModel @Auto private constructor(private val mediaRepository: MediaRepository) :
    ViewModel() {

    val liveListPhoto by lazy {
        mediaRepository.liveListPhoto
    }

    val liveListPhotoSelected by lazy {
        MutableLiveData<Stack<AppPhoto>>(Stack())
    }

    val eventLoading = MutableLiveData(Event())

    var currentItemCount = 0
    var pageCount = 8

    fun loadListMedia(
        forceLoad: Boolean = false,
        eventLoading: MutableLiveData<Event>? = this.eventLoading
    ) {
        currentItemCount += pageCount
        mediaRepository.loadListPhoto(currentItemCount, forceLoad, eventLoading)
    }

    fun clearListSelected() {
        mediaRepository.clearListSelected()
    }
}