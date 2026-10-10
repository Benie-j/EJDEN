package com.ejden.app.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    indices = [
        Index(value = ["name"]),
        Index(value = ["barcode"])
    ]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val barcode: String?,
    val priceCfa: Long,
    val quantity: Double,
    val minQuantity: Double,
    val createdAt: Long,
    val updatedAt: Long
)
