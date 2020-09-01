package com.lhd.galleryadapterdemo

import android.Manifest
import com.base.baselibrary.activity.BaseActivity
import com.lhd.galleryadapterdemo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val listPermission = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

    }

    fun grantPermission(onAllow: () -> Unit) {
        doRequestPermission(listPermission, onAllow)
    }

}