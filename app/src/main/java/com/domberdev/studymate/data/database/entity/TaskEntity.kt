package com.domberdev.studymate.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.domberdev.studymate.domain.model.Task

@Entity("task_table")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Long = 0,
    @ColumnInfo("title") val titulo: String,
    @ColumnInfo("subject") val materia: String,
    @ColumnInfo("color") val color: Int,
    @ColumnInfo("description") val descripcion: String,
    @ColumnInfo("deadline") val deadline: String,
    @ColumnInfo("type") val tipo: Int,
    @ColumnInfo("status") val estatus: Boolean
)

fun Task.toDatabase() = TaskEntity(id, titulo, materia, color, descripcion, deadline, tipo, estatus)