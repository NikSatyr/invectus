package com.niksatyr.invectus.screen.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (canNavigateUp()) {
            setupActionBar()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (canNavigateUp()) {
            finish()
            true
        } else super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (canNavigateUp()) {
            finish()
        }
    }

    fun onStateUpdated(state: BaseViewModel.State) {
        when (state) {
            is BaseViewModel.State.Success -> onSuccess()
            is BaseViewModel.State.Loading -> onLoading()
            is BaseViewModel.State.Error -> onError(state.reason)
        }
    }

    open fun onSuccess() {
        // no-op
    }

    open fun onError(reason: String) {
        // no-op
    }

    open fun onLoading() {
        // no-op
    }

    open fun canNavigateUp() = false

    private fun setupActionBar() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

}