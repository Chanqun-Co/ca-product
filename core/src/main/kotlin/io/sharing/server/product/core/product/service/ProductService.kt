package io.sharing.server.product.core.product.service

import io.sharing.server.core.support.stereotype.UseCase
import io.sharing.server.product.core.product.domain.Product
import io.sharing.server.product.core.product.port.inp.CreateProductCommand
import io.sharing.server.product.core.product.port.inp.ProductCreation
import io.sharing.server.product.core.product.port.outp.ProductRepository
import jakarta.transaction.Transactional

@UseCase
@Transactional
class ProductService(val productRepository: ProductRepository) : ProductCreation {
    override fun createProduct(command: CreateProductCommand): Long? {

        val carModel = command.carModel
        val color = command.color
        val distance = command.distance
        val rentalFee = command.rentalFee
        val licensePlate = command.licensePlate
        val status = command.status
        val description = command.description
        val images = command.images
        val userUUID = command.userUUID

        val product = Product.create(
            carModel = carModel,
            color = color,
            distance = distance,
            rentalFee = rentalFee,
            licensePlate = licensePlate,
            status = status,
            description = description,
            images = images,
            userUUID = userUUID
        )

        val savedProduct = productRepository.save(product)

        return savedProduct.id
    }
}
