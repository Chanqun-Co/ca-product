package io.sharing.server.product.core.product.domain

import io.sharing.server.product.core.product.domain.ProductStatus.*
import io.sharing.server.core.support.jpa.BaseAggregateRoot
import io.sharing.server.product.core.carmodel.domain.CarModel
import jakarta.persistence.*

/**
 * 상품
 *
 * - 관리자는 등록된 상품을 승인 또는 거절을 할 수 있다.
 */
@Entity
class Product(
    /** 사용자 UUID */
    @Column(nullable = false)
    var userUUID: String,

    /** 차량 모델 */
    @ManyToOne(fetch = FetchType.LAZY)
    val carModel: CarModel,

    /** 색상 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var color: ProductColor,

    /** 주행거리 */
    @Column(nullable = false)
    var distance: Int,

    /** 대여료 */
    @Column(nullable = false)
    var rentalFee: Int,

    /** 차량번호 */
    @Column(length = 50, nullable = false)
    var licensePlate: String,

    /** 상태 */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    var status: ProductStatus = REGISTERED,

    /** 설명 */
    @Column(columnDefinition = "TEXT")
    var description: String,

    /** 이미지 */
    @ElementCollection
    @CollectionTable(name = "product_img", joinColumns = [JoinColumn(name = "product_id")])
    var images: MutableList<String> = mutableListOf()

) : BaseAggregateRoot<Product>() {
    // update 분리
    fun update(
        color: ProductColor, distance: Int, rentalFee: Int, licensePlate: String,
        description: String, images: MutableList<String>
    ) {
        require(rentalFee >= MINIMUM_FEE)
        require(images.size <= MAXIMUM_IMAGE_COUNT)
        this.color = color
        this.distance = distance
        this.rentalFee = rentalFee
        this.licensePlate = licensePlate
        this.description = description
        this.images = images
        this.userUUID = userUUID // Consider if updating the userUUID is necessary. If not, remove this line.
    }

    fun approve() {
        check(this.status == REGISTERED)
        this.status = AVAILABLE
    }

    fun reject() {
        check(this.status == REGISTERED)
        this.status = REJECTED
    }

    companion object {
        const val MAXIMUM_IMAGE_COUNT = 10
        const val MINIMUM_FEE = 0

        fun create(
            carModel: CarModel, color: ProductColor, distance: Int, rentalFee: Int,
            licensePlate: String, status: ProductStatus, description: String,
            images: MutableList<String>, userUUID: String
        ): Product {
            require(rentalFee >= MINIMUM_FEE)
            require(images.size <= MAXIMUM_IMAGE_COUNT)

            return Product(
                carModel = carModel, color = color, distance = distance, rentalFee = rentalFee,
                licensePlate = licensePlate, status = status, description = description,
                images = images, userUUID = userUUID
            )
        }
    }
}
