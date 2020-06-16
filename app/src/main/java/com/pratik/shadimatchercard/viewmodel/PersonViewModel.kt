package com.pratik.shadimatchercard.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airbnb.lottie.utils.Utils
import com.google.gson.Gson
import com.pratik.shadimatchercard.MainActivity
import com.pratik.shadimatchercard.MainActivity.Companion.db
import com.pratik.shadimatchercard.MainActivity.Companion.loader
import com.pratik.shadimatchercard.db.Entity
import com.pratik.shadimatchercard.model.PersonsList
import com.pratik.shadimatchercard.retrofit.ApiRequest
import com.pratik.shadimatchercard.retrofit.RetrofitModule
import com.pratik.shadimatchercard.model.Result
import com.pratik.shadimatchercard.utils.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class PersonViewModel(private val context: Context) : ViewModel() {

    val TAG = PersonViewModel::class.java.simpleName
    var personsMutableLiveData: MutableLiveData<ArrayList<Result>> = MutableLiveData()
    val personLiveDataList: LiveData<ArrayList<Result>> = personsMutableLiveData
    var apiRequest: ApiRequest

    init {
        val retrofitModule = RetrofitModule
        apiRequest = retrofitModule.createApiRequest(retrofitModule.getRetrofit())
        getPersonsList()
    }

    fun getPersonsList() {
        val listOfData = db.dao().entityList

        if (listOfData.size > 1) {
            getDataForDB(listOfData)
            return
        }
        if(Util.isInternetConnected(context)){
            getDataFromWebServices()
        }else{
            val activity: MainActivity = context as MainActivity
            Util.showNoNetworkDialog(activity)
        }

    }

    private fun getDataFromWebServices(){
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
                        inserDataIntoDB(personsMutableLiveData!!.value!!)
                        loader.dismissLoading()
                    }
                }

                override fun onFailure(call: Call<PersonsList>, t: Throwable) {
                    Log.d(TAG, "Error Msg : " + t)
                    loader.dismissLoading()
                }
            })
    }

    fun inserDataIntoDB(resultList: ArrayList<Result>) {
        for (result in resultList) {
            val id = result.email
            val gson = Gson()
            val jsonObject = gson.toJson(result)
            val entity = Entity(id, jsonObject)
            db.dao().insert(entity)
        }
    }

    private fun getDataForDB(listOfData: List<Entity>) {
        val list: ArrayList<Result> = ArrayList()
        for (entity in listOfData) {
            val gson = Gson()
            val result = gson.fromJson(entity.result, Result::class.java)
            list.add(result)
        }
        personsMutableLiveData!!.value = list

    }
}

