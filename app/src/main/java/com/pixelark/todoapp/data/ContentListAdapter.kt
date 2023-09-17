package com.pixelark.todoapp.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.pixelark.todoapp.data.model.ContentDataModel
import com.pixelark.todoapp.data.source.ContentDatabase
import com.pixelark.todoapp.databinding.ContentItemBinding

class ContentListAdapter : RecyclerView.Adapter<ContentListAdapter.ContentListViewHolder>() {

    private val selectedItems: MutableList<ContentDataModel> = mutableListOf()
    private var isInEditMode: Boolean = false
    private val contentList = mutableListOf<ContentDataModel>()

    class ContentListViewHolder(val binding: ContentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentListViewHolder {
        val binding = ContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    override fun onBindViewHolder(holder: ContentListViewHolder, position: Int) {
        val data = contentList[position]
        holder.binding.contentItemTvTitle.text = data.title
        holder.binding.contentItemTvDescription.text = data.description
        holder.binding.contentItemCbCheck.setOnCheckedChangeListener { _, isChecked ->
            data.isChecked = isChecked
            if (isChecked) {
                selectedItems.add(data)
            } else {
                selectedItems.remove(data)
            }
        }
        holder.binding.contentItemCbCheck.isChecked = data.isChecked
        holder.binding.contentItemCbCheck.isVisible = isInEditMode
    }

    fun setEditMode(isInEditMode: Boolean) {
        this.isInEditMode = isInEditMode
        notifyDataSetChanged()
    }

    fun addItem(contentDataModel: ContentDataModel) {
        contentList.add(contentDataModel)
        notifyItemChanged(contentList.size - 1)
    }

    fun deleteSelected() {
        for (selectedItem in selectedItems) {
            contentList.remove(selectedItem)
        }
        notifyDataSetChanged()
        selectedItems.clear()
        ContentDatabase.setContentList(contentList)
    }
}