package io.sharing.server.product.core.product.domain

import io.sharing.server.product.core.carmodel.domain.CarModel
import io.sharing.server.product.core.carmodel.domain.CarName
import io.sharing.server.product.core.carmodel.domain.Fuel
import io.sharing.server.product.core.carmodel.domain.Manufacturer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ProductTest {

    private val carModel = CarModel(
            name = CarName.K7, // 가정: CarName 열거형에 TESLA_MODEL_S가 있다.
            manufacturer = Manufacturer.KIA, // 가정: Manufacturer 열거형에 TESLA가 있다.
            modelYear = 2022,
            fuel = Fuel.GASOLINE // 가정: Fuel 열거형에 ELECTRIC가 있다.
    )
    private val userUUID = UUID.randomUUID().toString()
    private val initialColor = ProductColor.BLACK
    private val initialDistance = 10000
    private val initialRentalFee = 500
    private val initialLicensePlate = "123가-4567"
    private val initialStatus = ProductStatus.REGISTERED
    private val initialDescription = "A well-maintained, comfortable car."
    private val initialImages = mutableListOf("image1.jpg", "image2.jpg")

    @Test
    fun `상품 생성`() {
        val product = Product.create(
                carModel, initialColor, initialDistance, initialRentalFee,
                initialLicensePlate, initialStatus, initialDescription, initialImages, userUUID
        )

        assertNotNull(product)
        assertEquals(initialColor, product.color)
        assertEquals(initialDistance, product.distance)
        assertEquals(initialRentalFee, product.rentalFee)
        assertEquals(initialLicensePlate, product.licensePlate)
        assertEquals(initialStatus, product.status)
        assertEquals(initialDescription, product.description)
        assertEquals(initialImages, product.images)
        assertEquals(userUUID, product.userUUID)
    }

    @Test
    fun `제품 정보 업데이트`() {
        val product = Product.create(
                carModel, initialColor, initialDistance, initialRentalFee,
                initialLicensePlate, initialStatus, initialDescription, initialImages, userUUID
        )

        val updatedColor = ProductColor.WHITE
        val updatedDistance = 12000
        val updatedRentalFee = 550
        val updatedLicensePlate = "XYZ-9876"
        val updatedDescription = "An updated description of the car."
        val updatedImages = mutableListOf("image3.jpg", "image4.jpg")

        product.update(updatedColor, updatedDistance, updatedRentalFee, updatedLicensePlate, updatedDescription, updatedImages) // Assuming userUUID can be updated if necessary

        assertEquals(updatedColor, product.color)
        assertEquals(updatedDistance, product.distance)
        assertEquals(updatedRentalFee, product.rentalFee)
        assertEquals(updatedLicensePlate, product.licensePlate)
        assertEquals(updatedDescription, product.description)
        assertEquals(updatedImages, product.images)
        assertEquals(userUUID, product.userUUID) // Check if this assertion is needed based on your design
    }

    @Test
    fun `제품 승인 상태로 변경`() {
        val product = Product.create(
                carModel, initialColor, initialDistance, initialRentalFee,
                initialLicensePlate, initialStatus, initialDescription, initialImages, userUUID
        )

        product.approve()

        assertEquals(ProductStatus.AVAILABLE, product.status)
    }

    @Test
    fun `제품 거절 상태로 변경`() {
        val product = Product.create(
                carModel, initialColor, initialDistance, initialRentalFee,
                initialLicensePlate, initialStatus, initialDescription, initialImages, userUUID
        )

        product.reject()

        assertEquals(ProductStatus.REJECTED, product.status)
    }
}
