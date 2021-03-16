package com.example.newsmvvm.util

import androidx.room.TypeConverter
import com.example.newsmvvm.model.Source

class Conventers {
    @TypeConverter
    fun fromSource(source: Source): String = source.name

    @TypeConverter
    fun toSource(name: String): Source = Source(name,name)
}