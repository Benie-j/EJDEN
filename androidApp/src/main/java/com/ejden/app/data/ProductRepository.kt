package com.ejden.app.data

import com.ejden.shared.ProductDraft
import com.ejden.shared.ProductUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val dao: ProductDao
) {
    fun observeProducts(): Flow<List<ProductUi>> =
        dao.observeProducts().map { products ->
            products.map { product ->
                ProductUi(
                    id = product.id,
                    name = product.name,
                    barcode = product.barcode,
                    priceCfa = product.priceCfa,
                    quantity = product.quantity,
                    minQuantity = product.minQuantity
                )
            }
        }

    suspend fun addProduct(draft: ProductDraft) {
        val now = System.currentTimeMillis()
        dao.insert(
            ProductEntity(
                name = draft.name.trim(),
                barcode = draft.barcode?.trim()?.takeIf { it.isNotEmpty() },
                priceCfa = draft.priceCfa,
                quantity = draft.quantity,
                minQuantity = draft.minQuantity,
                createdAt = now,
                updatedAt = now
            )
        )
    }

    suspend fun deleteProduct(id: Long) {
        dao.deleteById(id)
    }
}
