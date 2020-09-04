package com.lhd.galleryadapterdemo.ui

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.base.baselibrary.utils.observer
import com.base.baselibrary.utils.post
import com.base.baselibrary.viewmodel.Event
import com.base.baselibrary.viewmodel.autoViewModels
import com.base.baselibrary.views.ext.async
import com.base.baselibrary.views.ext.doJob
import com.lhd.gallery_adapter.adapter.listener.IGalleryAdapterListener
import com.lhd.gallery_adapter.utils.CollageGroupLayoutUtils
import com.lhd.galleryadapterdemo.R
import com.lhd.galleryadapterdemo.adapter.CollageAdapter
import com.lhd.galleryadapterdemo.databinding.FragmentHomeBinding
import com.lhd.galleryadapterdemo.models.AppPhoto
import com.lhd.galleryadapterdemo.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.item_photo.view.*
import kotlinx.coroutines.Dispatchers


class HomeFragment : BaseMainFragment<FragmentHomeBinding>(), IGalleryAdapterListener<AppPhoto> {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    private val viewModel by autoViewModels<HomeViewModel>()

    private val adapter by lazy {
        CollageAdapter(0.00f).apply {
            listener = this@HomeFragment
        }
    }

    override fun initBinding() {
        binding.viewModel = viewModel
        binding.adapter = adapter
        adapter.listSelected = viewModel.liveListPhotoSelected
    }

    override fun initView() {
        observer(viewModel.liveListPhoto) { list ->
            if (!list.isNullOrEmpty()) {
                async({
                    adapter.setData(list, false)
                }, {
                    if (!isLoadMore) {
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter.notifyDataForLoadMore(lastListGroupSize)
                        isLoadMore = false
                    }
                }, dispathcherOut = Dispatchers.Main)
            }
        }
        activity.grantPermission {
            viewModel.loadListMedia()
        }
        //binding.rvGallery.addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
        observer(viewModel.liveListPhotoSelected){

        }
    }

    override fun setHandleBack(): Boolean {
        return false
    }

    private var isLoadMore = false
    private var lastListGroupSize = 0
    override fun onHandleLoadMore() {
        isLoadMore = true
        lastListGroupSize = adapter.groupData.size
        viewModel.loadListMedia(true, eventLoading = null)
    }

    override fun onViewHandleCheckClicked(item: AppPhoto?, position: Int) {

    }

    override fun onItemSelected(
        viewHandleSelect: View?,
        item: AppPhoto?,
        groupPosition: Int,
        selected: Boolean
    ) {
        val scale = if (selected) 0.9f else 1f
        viewHandleSelect?.animate()?.scaleX(scale)?.scaleY(scale)
    }

}