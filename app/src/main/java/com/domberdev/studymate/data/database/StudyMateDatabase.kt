package com.domberdev.studymate.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.domberdev.studymate.data.database.dao.StudyMateDao
import com.domberdev.studymate.data.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class StudyMateDatabase: RoomDatabase() {

    abstract fun getStudyMateDao(): StudyMateDao
}