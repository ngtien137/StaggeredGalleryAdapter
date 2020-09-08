package com.lhd.galleryadapterdemo.ui

import android.view.View
import com.base.baselibrary.utils.observer
import com.base.baselibrary.viewmodel.autoViewModels
import com.base.baselibrary.views.ext.loge
import com.lhd.gallery_adapter.adapter.listener.IGalleryAdapterListener
import com.lhd.galleryadapterdemo.R
import com.lhd.galleryadapterdemo.adapter.group.ParentGalleryAdapter
import com.lhd.galleryadapterdemo.databinding.FragmentGroupBinding
import com.lhd.galleryadapterdemo.models.AppPhoto
import com.lhd.galleryadapterdemo.viewmodels.GroupViewModel

class GroupFragment : BaseMainFragment<FragmentGroupBinding>(), IGalleryAdapterListener<AppPhoto> {
    override fun getLayoutId(): Int {
        return R.layout.fragment_group
    }

    private val viewModel by autoViewModels<GroupViewModel>()

    private val adapter by lazy {
        ParentGalleryAdapter().apply {
            listener = this@GroupFragment
        }
    }

    override fun initBinding() {
        binding.viewModel = viewModel
        binding.adapter = adapter
        adapter.setListSelected(viewModel.liveListPhotoSelected)
    }

    override fun initView() {
        observer(viewModel.liveListPhoto) {
            it?.let {
                adapter.setData(it, true)
            }
        }
    }

    override fun onViewClick(vId: Int) {
        when (vId) {

        }
    }

    override fun onHandleLoadMore() {
        viewModel.loadListMedia(true, null)
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

    override fun onViewHandleCheckClicked(item: AppPhoto?, groupPosition: Int) {

    }

    override fun onValidateBeforeCheckingItem(item: AppPhoto?, groupPosition: Int): Boolean {
        return true
    }

}