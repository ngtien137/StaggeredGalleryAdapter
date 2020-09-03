package com.lhd.galleryadapterdemo.ui

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.base.baselibrary.utils.observer
import com.base.baselibrary.utils.post
import com.base.baselibrary.viewmodel.Event
import com.base.baselibrary.viewmodel.autoViewModels
import com.lhd.galleryadapterdemo.R
import com.lhd.galleryadapterdemo.adapter.CollageAdapter
import com.lhd.galleryadapterdemo.databinding.FragmentHomeBinding
import com.lhd.galleryadapterdemo.viewmodels.HomeViewModel


class HomeFragment : BaseMainFragment<FragmentHomeBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    private val viewModel by autoViewModels<HomeViewModel>()

    private val adapter by lazy{
        CollageAdapter()
    }

    override fun initBinding() {
        binding.viewModel = viewModel
        binding.adapter = adapter
    }

    override fun initView() {
        observer(viewModel.liveListPhoto) {
            if (!it.isNullOrEmpty()){
                viewModel.eventLoading.value = Event(true)
                adapter.data = it
                viewModel.eventLoading.value = Event(false)
            }
        }
        activity.grantPermission {
            viewModel.loadListMedia()
        }
        binding.rvGallery.addItemDecoration(DividerItemDecoration(activity,RecyclerView.VERTICAL))
    }

    override fun setHandleBack(): Boolean {
        return false
    }

}