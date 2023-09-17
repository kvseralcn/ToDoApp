package com.pixelark.todoapp.data.model

data class ContentDataModel(
    var id: Int,
    val title: String,
    val description: String,
    @Transient
    var isChecked: Boolean = false
)
