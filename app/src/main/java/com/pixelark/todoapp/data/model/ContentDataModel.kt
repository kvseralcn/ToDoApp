package com.pixelark.todoapp.data.model

import com.pixelark.todoapp.data.enum.TodoPriority

data class ContentDataModel(
    var id: Int,
    val title: String,
    val description: String,
    val priority: TodoPriority,
    @Transient
    var isChecked: Boolean = false
)
