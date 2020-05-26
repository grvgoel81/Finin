package com.example.finin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finin.base.Event
import com.example.finin.model.Data
import com.example.finin.model.UserResponse
import com.example.finin.utils.GetUsersData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val getUsersData: GetUsersData) :
    ViewModel() {

    private val _userLiveData by lazy { MutableLiveData<Event<UserResponse>>() }
    val userLiveData: LiveData<Event<UserResponse>> by lazy { _userLiveData }


    var loadingState = MutableLiveData<Boolean>()

    private val _apiError by lazy { MutableLiveData<Event<Throwable>>() }
    val apiError: LiveData<Event<Throwable>> by lazy { _apiError }

    private val disposable by lazy { CompositeDisposable() }


    fun fetchUsersData(page: Int, delay: Int) {
        val issueDisposable = getUsersData.getUsersData(page, delay)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingState.value = true }
            .doOnEvent { _, _ -> loadingState.value = false }
            .doOnError { loadingState.value = false }
            .subscribe(
                { it?.let { it -> Event(it).run(_userLiveData::postValue) } }
                ,
                { Event(it).run(_apiError::postValue) }
            )
        disposable.add(issueDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}