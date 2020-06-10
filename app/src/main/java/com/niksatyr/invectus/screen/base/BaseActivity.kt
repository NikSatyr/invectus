package com.niksatyr.invectus.screen.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel>(
    @LayoutRes layoutId: Int,
    viewModelClass: KClass<VM>
) : AppCompatActivity(layoutId) {

    val viewModel: VM by viewModel(viewModelClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (canNavigateUp()) {
            setupActionBar()
        }

        viewModel.state.observe(this, Observer { onStateUpdated(it) })
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

    private fun onStateUpdated(state: BaseViewModel.State) {
        when (state) {
            is BaseViewModel.State.Success -> onSuccess()
            is BaseViewModel.State.Loading -> onLoading()
            is BaseViewModel.State.Error -> onError(state.reason)
        }
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

}