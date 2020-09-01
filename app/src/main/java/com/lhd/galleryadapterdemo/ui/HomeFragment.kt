package com.lhd.galleryadapterdemo.ui

import com.base.baselibrary.viewmodel.autoViewModels
import com.lhd.galleryadapterdemo.R
import com.lhd.galleryadapterdemo.databinding.FragmentHomeBinding
import com.lhd.galleryadapterdemo.viewmodels.HomeViewModel


class HomeFragment : BaseMainFragment<FragmentHomeBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    private val viewModel by autoViewModels<HomeViewModel>()

    override fun initBinding() {
        binding.viewModel = viewModel
    }

    override fun initView() {

    }

    override fun setHandleBack(): Boolean {
        return false
    }

}