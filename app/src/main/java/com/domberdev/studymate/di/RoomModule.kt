package com.domberdev.studymate.di

import android.content.Context
import androidx.room.Room
import com.domberdev.studymate.data.database.StudyMateDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val STUDYMATE_DATABASE_NAME = "studymate_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, StudyMateDatabase::class.java, STUDYMATE_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideRoomDao(db: StudyMateDatabase) = db.getStudyMateDao()
}