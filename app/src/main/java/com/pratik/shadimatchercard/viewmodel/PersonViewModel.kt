package com.pratik.shadimatchercard.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratik.shadimatchercard.MainActivity.Companion.loader
import com.pratik.shadimatchercard.model.PersonsList
import com.pratik.shadimatchercard.retrofit.ApiRequest
import com.pratik.shadimatchercard.retrofit.RetrofitModule
import com.pratik.shadimatchercard.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonViewModel : ViewModel() {

    val TAG = PersonViewModel::class.java.simpleName
    var personsMutableLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    val personLiveDataList: LiveData<List<Result>> = personsMutableLiveData
    var apiRequest: ApiRequest

    init {
        val retrofitModule = RetrofitModule
        apiRequest = retrofitModule.createApiRequest(retrofitModule.getRetrofit())
        getPersonsList()
    }

    fun getPersonsList() {
        loader.showLoading()
        apiRequest.getPersonsList(10)
            .enqueue(object : Callback<PersonsList> {
                override fun onResponse(
                    call: Call<PersonsList>,
                    response: Response<PersonsList>
                ) {
                    Log.d(TAG, "Response : " + response.body())
                    if (response.body() != null) {
                        personsMutableLiveData!!.value = response.body()!!.results
                        loader.dismissLoading()
                    }
                }

                override fun onFailure(call: Call<PersonsList>, t: Throwable) {
                    Log.d(TAG, "Error Msg : " + t)
                    loader.dismissLoading()
                }
            })
    }

}

