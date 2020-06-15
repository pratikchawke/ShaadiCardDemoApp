package com.pratik.shadimatchercard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pratik.shadimatchercard.R
import com.pratik.shadimatchercard.databinding.ShaadicardItemsBinding
import com.pratik.shadimatchercard.listener.ClickListener
import com.pratik.shadimatchercard.model.Result

class PersonsListAdapter(private val listOfPersonsList: List<Result>) :
    RecyclerView.Adapter<PersonsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsBinding: ShaadicardItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.shaadicard_items,
            parent,
            false
        )
        return ViewHolder(itemsBinding)
    }

    override fun getItemCount(): Int {
        return listOfPersonsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val personData = listOfPersonsList.get(position)
        holder.onBind(personData)
        holder.acceptView.setOnClickListener {click ->
            holder.acceptanceView.visibility = View.GONE
            holder.status.visibility = View.VISIBLE
            holder.status.text = "Accepted"
        }
        holder.declineView.setOnClickListener {click ->
            holder.acceptanceView.visibility = View.GONE
            holder.status.visibility = View.VISIBLE
            holder.status.text = "Declined"
        }
    }


    class ViewHolder(itemView: ShaadicardItemsBinding) : RecyclerView.ViewHolder(itemView.root) {
        val itemBinding: ShaadicardItemsBinding = itemView
        val acceptView: ImageView = itemView.acceptImageView
        val declineView: ImageView = itemView.declineImageView
        val status: TextView = itemView.statusTextView
        val acceptanceView: LinearLayout = itemView.acceptanceView

        fun onBind(result: Result) {
            itemBinding.result = result
        }
    }

}