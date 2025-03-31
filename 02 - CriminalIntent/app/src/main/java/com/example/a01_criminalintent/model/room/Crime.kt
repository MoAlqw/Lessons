package com.example.a01_criminalintent.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Crime(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false,
    @ColumnInfo(defaultValue = "")
    var suspect: String = ""
) {
    val photoFileName get() = "IMG_$id.jpg"
}