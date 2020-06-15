package com.pratik.shadimatchercard

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.lifecycle.Observer
import com.pratik.shadimatchercard.listener.LoadingListener
import com.pratik.shadimatchercard.viewmodel.PersonViewModel

class MainActivity : AppCompatActivity(), LoadingListener {

    val TAG = MainActivity::class.java.simpleName
    private var dialog: Dialog? = null
    lateinit var personViewModel: PersonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loader = this;
        personViewModel = PersonViewModel()
        setContentView(R.layout.activity_main)
        personViewModel.personLiveDataList.observe(this,
            Observer { dataList ->
                if (dataList != null) {
                    Log.d(TAG, "List : " + dataList.toString())
                }
            })

    }

    companion object {
        lateinit var loader: LoadingListener
    }

    override fun showLoading() {
        runOnUiThread { showLoader(this) }
    }

    override fun dismissLoading() {
        runOnUiThread { hideLoader() }
    }

    fun hideLoader() {
        Log.d(TAG, "Hide Loading !!!!")
        try {
            if (dialog!! != null) {
                if (dialog!!.isShowing) dialog!!.dismiss()
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception $e")
        }
    }

    fun showLoader(context: Context) {
        Log.d(TAG, "Show Loading !!!!")
        try {
            dialog = getProgressDialog(context)
            dialog!!.show()
        } catch (e: Exception) {
            Log.d(TAG, "Exception $e")
        }
    }

    fun getProgressDialog(context: Context): Dialog {
        if (dialog == null) {
            dialog = Dialog(context)
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(false)
        val factory = LayoutInflater.from(context)
        val customPopupView: View = factory.inflate(R.layout.loading_dialog, null)
        dialog!!.setContentView(customPopupView)
        return dialog!!
    }
}