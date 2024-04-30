package io.sharing.server.product.core.product.port

import io.sharing.server.product.core.carmodel.domain.*
import io.sharing.server.product.core.product.domain.*
import io.sharing.server.product.core.product.port.outp.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class ProductRepositoryTest @Autowired constructor(
    private val productRepository: ProductRepository,
    private val testEntityManager: TestEntityManager
) {
    @Test
    fun `상품 생성 성공`() {
        val carModel = CarModel(name = CarName.K7, manufacturer = Manufacturer.KIA, modelYear = 2021, fuel = Fuel.GASOLINE)
        testEntityManager.persist(carModel)
        testEntityManager.flush() // Ensure the persistence context is synchronized

        val product = Product(
            carModel = carModel,
            color = ProductColor.WHITE,
            distance = 5000,
            rentalFee = 299,
            licensePlate = "XYZ123",
            status = ProductStatus.AVAILABLE,
            description = "Low mileage, excellent condition",
            images = mutableListOf("url1.jpg", "url2.jpg"),
            userUUID = "123e4567-e89b-12d3-a456-426614174000"
        )

        productRepository.save(product)
        testEntityManager.flush() // Ensure the persistence context is synchronized

        val retrievedProduct = productRepository.findByIdOrNull(product.id!!)

        assertNotNull(retrievedProduct, "Product should not be null")
        retrievedProduct?.let {
            assertEquals(product.carModel, it.carModel, "Car models should match")
            assertEquals(product.color, it.color, "Colors should match")
            assertEquals(product.distance, it.distance, "Distances should match")
            assertEquals(product.rentalFee, it.rentalFee, "Rental fees should match")
            assertEquals(product.licensePlate, it.licensePlate, "License plates should match")
            assertEquals(product.status, it.status, "Statuses should match")
            assertEquals(product.description, it.description, "Descriptions should match")
            assertEquals(product.images, it.images, "Image lists should match")
            assertEquals(product.userUUID, it.userUUID, "User UUIDs should match")
        }
    }

    @Test
    fun `상품 삭제 테스트`() {
        val product = testEntityManager.persistFlushFind(
            Product(
                carModel = testEntityManager.persistFlushFind(CarModel(name = CarName.K7, manufacturer = Manufacturer.KIA, modelYear = 2021, fuel = Fuel.GASOLINE)),
                color = ProductColor.WHITE,
                distance = 5000,
                rentalFee = 299,
                licensePlate = "XYZ123",
                status = ProductStatus.AVAILABLE,
                description = "Low mileage, excellent condition",
                images = mutableListOf("url1.jpg", "url2.jpg"),
                userUUID = "123e4567-e89b-12d3-a456-426614174000"
            )
        )

        assertNotNull(productRepository.findByIdOrNull(product.id), "Product should exist before deletion")

        productRepository.delete(product)
        testEntityManager.flush()

        assertNull(productRepository.findByIdOrNull(product.id), "Product should not exist after deletion")
    }

    @Test
    fun `상품 조회 테스트`() {
        val product = testEntityManager.persistFlushFind(
            Product(
                carModel = testEntityManager.persistFlushFind(CarModel(name = CarName.K7, manufacturer = Manufacturer.KIA, modelYear = 2021, fuel = Fuel.GASOLINE)),
                color = ProductColor.WHITE,
                distance = 5000,
                rentalFee = 299,
                licensePlate = "XYZ123",
                status = ProductStatus.AVAILABLE,
                description = "Low mileage, excellent condition",
                images = mutableListOf("url1.jpg", "url2.jpg"),
                userUUID = "123e4567-e89b-12d3-a456-426614174000"
            )
        )

        val retrievedProduct = productRepository.findByIdOrNull(product.id)
        assertNotNull(retrievedProduct, "Product should be retrievable")
    }

    @Test
    fun `상품 업데이트 테스트`() {
        val product = testEntityManager.persistFlushFind(
            Product(
                carModel = testEntityManager.persistFlushFind(CarModel(name = CarName.K7, manufacturer = Manufacturer.KIA, modelYear = 2021, fuel = Fuel.GASOLINE)),
                color = ProductColor.WHITE,
                distance = 5000,
                rentalFee = 299,
                licensePlate = "XYZ123",
                status = ProductStatus.AVAILABLE,
                description = "Low mileage, excellent condition",
                images = mutableListOf("url1.jpg", "url2.jpg"),
                userUUID = "123e4567-e89b-12d3-a456-426614174000"
            )
        )

        // Update some fields
        product.color = ProductColor.BLACK
        product.distance = 10000
        product.description = "Updated description"
        productRepository.save(product)
        testEntityManager.flush()

        // Check updated fields
        val updatedProduct = productRepository.findByIdOrNull(product.id)
        assertNotNull(updatedProduct)
        assertEquals(ProductColor.BLACK, updatedProduct?.color, "Color should be updated")
        assertEquals(10000, updatedProduct?.distance, "Distance should be updated")
        assertEquals("Updated description", updatedProduct?.description, "Description should be updated")
    }
}
