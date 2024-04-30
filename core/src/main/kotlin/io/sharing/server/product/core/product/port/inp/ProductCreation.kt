package io.sharing.server.product.core.product.port.inp

import io.sharing.server.product.core.carmodel.domain.CarModel
import io.sharing.server.product.core.product.domain.ProductColor
import io.sharing.server.product.core.product.domain.ProductStatus

interface ProductCreation {
    fun createProduct(command: CreateProductCommand): Long?
}

// Example command data class
data class CreateProductCommand(
    val carModel: CarModel,
    val color: ProductColor,
    val distance: Int,
    val rentalFee: Int,
    val licensePlate: String,
    val status: ProductStatus,
    val description: String,
    val images: MutableList<String>,
    val userUUID: String
)
