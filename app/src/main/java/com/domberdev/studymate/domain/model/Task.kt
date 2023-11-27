package com.domberdev.studymate.domain.model

import com.domberdev.studymate.data.database.entity.TaskEntity

data class Task (
    val id: Long,
    val titulo: String,
    val materia: String,
    val color: Int,
    val descripcion: String,
    val deadline: String,
    val tipo: Int,
    val estatus: Boolean
)

fun TaskEntity.toDomain() = Task(id, titulo, materia, color, descripcion, deadline, tipo, estatus)