package com.example.rickandmorty.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.databinding.ItemCharacterBinding
import com.example.rickandmorty.network.domain.ResultX
import com.example.rickandmorty.viewmodel.CharactersFeedViewModel

class CharactersFeedAdapter(
    private val charactersFeedViewModel: CharactersFeedViewModel,
    private var list: List<ResultX> = emptyList()
) : RecyclerView.Adapter<CharactersFeedAdapter.CharactersViewHolder>() {

    fun setData(list1: List<ResultX>) {
        this.list = list1.take(20)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding  = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun getItemCount(): Int = if (list.isNullOrEmpty()) 0 else list.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val listItem = list[position]
        listItem.image.let {
            holder.image.let { image ->
                Glide.with(holder.itemView.context).load(listItem.image).into(image)
            }
        }
        holder.name.text = listItem.name
        holder.description.text = listItem.species
        holder.status.text=listItem.status

        holder.itemView.setOnClickListener {
            listItem.let {
                charactersFeedViewModel.onListItemClicked(listItem)
            }
        }
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItems: Int? = recyclerView.layoutManager?.itemCount
                val scrolledItems: Int? =
                    (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                if (totalItems == null || scrolledItems == null) return
                if (scrolledItems <= totalItems) {
                    charactersFeedViewModel.getCharactersResponse()
                }
            }
        })
    }

    class CharactersViewHolder(binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        val image: ImageView = binding.imageView
        val name: TextView = binding.textName
        val status: TextView = binding.textStatus
        val description: TextView =binding.textDescription
    }
}