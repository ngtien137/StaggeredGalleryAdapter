package com.lhd.galleryadapterdemo.ui

import androidx.databinding.ViewDataBinding
import com.base.baselibrary.fragment.BaseNavigationFragment
import com.lhd.galleryadapterdemo.MainActivity

abstract class BaseMainFragment<BD:ViewDataBinding> : BaseNavigationFragment<BD, MainActivity>(){
}