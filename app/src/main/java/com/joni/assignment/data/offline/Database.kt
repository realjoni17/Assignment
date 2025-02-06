package com.joni.assignment.data.offline

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joni.assignment.domain.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
