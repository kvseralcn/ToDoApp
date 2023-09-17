package com.pixelark.todoapp.data.source

import com.pixelark.todoapp.data.model.ContentDataModel

object ContentDatabase {
    private val contentList = mutableListOf<ContentDataModel>()

    fun getContentList(): List<ContentDataModel> {
        return contentList
    }

    fun setContentList(contentList: List<ContentDataModel>) {
        this.contentList.clear()
        this.contentList.addAll(contentList)
    }

    fun addContent(title: String, description: String): ContentDataModel {
        val newContent = ContentDataModel(
            id = (contentList.lastOrNull()?.id ?: 0) + 1,
            title = title,
            description = description
        )
        contentList.add(newContent)
        return newContent
    }

    fun deleteContent(position: Int) {
        contentList.removeAt(position)
    }
}