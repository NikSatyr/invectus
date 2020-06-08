package com.niksatyr.invectus.screen.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    val state = MutableLiveData<State>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    sealed class State {
        object Loading : State()
        object Success : State()
        class Error(val reason: String) : State()
    }

}