package com.lhd.galleryadapterdemo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.base.baselibrary.viewmodel.Auto
import com.base.baselibrary.viewmodel.Event
import com.lhd.galleryadapterdemo.data.MediaRepository

class HomeViewModel @Auto private constructor(private val mediaRepository: MediaRepository) :
    ViewModel() {
    val liveListMedia by lazy {
        mediaRepository.liveListMedia
    }

    val liveListMediaSelected by lazy {
        mediaRepository.liveListMediaSelected
    }

    val eventLoading = MutableLiveData(Event())

    fun loadListMedia(forceLoad: Boolean = false) {
        mediaRepository.loadListMedia(forceLoad, eventLoading)
    }

    fun clearListSelected() {
        mediaRepository.clearListSelected()
    }
}