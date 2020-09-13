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
import com.base.baselibrary.views.ext.toast
import com.lhd.gallery_adapter.adapter.GalleryAdapter
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
        GalleryAdapter.ENABLE_LOG = true
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
                    if (isLoadMore) {
                        isLoadMore = false
                        if (lastListGroupSize != adapter.groupData.size)
                            adapter.notifyDataForLoadMore(lastListGroupSize)
                        else
                            adapter.notifyLoadMoreIfStillShow()
                    } else {
                        adapter.notifyDataSetChanged()
                    }
                }, dispathcherOut = Dispatchers.Main)
            }
        }
        activity.grantPermission {
            viewModel.loadListMedia()
        }
        //binding.rvGallery.addItemDecoration(DividerItemDecoration(activity, RecyclerView.VERTICAL))
        observer(viewModel.liveListPhotoSelected) {

        }
    }

    override fun setHandleBack(): Boolean {
        return false
    }

    private var lastListGroupSize = 0
    private var isLoadMore = false
    override fun onHandleLoadMore() {
        isLoadMore = true
        lastListGroupSize = adapter.groupData.size
        adapter.showLoadMore(true)
        viewModel.loadListMedia(true, eventLoading = null)
    }

    override fun onViewHandleCheckClicked(item: AppPhoto?, position: Int) {

    }

    override fun onItemSelected(
        viewRootItem: View?,
        item: AppPhoto?,
        groupPosition: Int,
        selected: Boolean
    ) {
        val scale = if (selected) 0.9f else 1f
        viewRootItem?.animate()?.scaleX(scale)?.scaleY(scale)
    }

    override fun onValidateBeforeCheckingItem(item: AppPhoto?, groupPosition: Int): Boolean {
        if (adapter.listSelected.value!!.size == 6) {
            toast("Limit check 6 items")
            return false
        }
        return true
    }

    override fun onViewClick(vId: Int) {
        when (vId) {
            R.id.btnGroupMode -> {
                navigateTo(R.id.action_homeFragment_to_groupFragment)
            }
        }
    }

}