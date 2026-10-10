package com.ejden.shared

data class ProductUi(
    val id: Long,
    val name: String,
    val barcode: String?,
    val priceCfa: Long,
    val quantity: Double,
    val minQuantity: Double
)

data class ProductDraft(
    val name: String,
    val barcode: String?,
    val priceCfa: Long,
    val quantity: Double,
    val minQuantity: Double
)
