package com.example.aviatickets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aviatickets.R
import com.example.aviatickets.databinding.ItemOfferBinding
import com.example.aviatickets.model.entity.Offer

class OfferListAdapter : RecyclerView.Adapter<OfferListAdapter.ViewHolder>() {

    private var items: List<Offer> = emptyList() // Change to List instead of ArrayList
r
    // Add a method to set items
    fun setItems(offerList: List<Offer>) {
        val diffResult = DiffUtil.calculateDiff(OfferDiffCallback(items, offerList))
        items = offerList
        diffResult.dispatchUpdatesTo(this)
    }

    // Add a submitList method for updating data with DiffUtil
    fun submitList(newItems: List<Offer>) {
        val diffResult = DiffUtil.calculateDiff(OfferDiffCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOfferBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun sortByPrice() {
        items = items.sortedBy { it.price }
        notifyDataSetChanged()
    }

    fun sortByDuration() {
        items = items.sortedBy { it.flight.duration }
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemOfferBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(offer: Offer) {
            val flight = offer.flight

            with(binding) {
                departureTime.text = flight.departureTimeInfo
                arrivalTime.text = flight.arrivalTimeInfo
                route.text = context.getString(
                    R.string.route_fmt,
                    flight.departureLocation.code,
                    flight.arrivalLocation.code
                )
                duration.text = context.getString(
                    R.string.time_fmt,
                    getTimeFormat(flight.duration).first.toString(),
                    getTimeFormat(flight.duration).second.toString()
                )
                direct.text = context.getString(R.string.direct)
                price.text = context.getString(R.string.price_fmt, offer.price.toString())
            }
        }

        private fun getTimeFormat(minutes: Int): Pair<Int, Int> = Pair(
            first = minutes / 60,
            second = minutes % 60
        )

    }

    // Define a class that implements DiffUtil.Callback
    private class OfferDiffCallback(
        private val oldList: List<Offer>,
        private val newList: List<Offer>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id // Assuming Offer has a unique id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition] // Assuming Offer has proper equals implementation
        }
    }
}
