package io.sharing.server.product.api.dto

import io.sharing.server.product.core.carmodel.domain.CarModel
import io.sharing.server.product.core.carmodel.domain.CarName
import io.sharing.server.product.core.carmodel.domain.Fuel
import io.sharing.server.product.core.carmodel.domain.Manufacturer
import io.sharing.server.product.core.product.domain.ProductColor
import io.sharing.server.product.core.product.domain.ProductStatus
import io.sharing.server.product.core.product.port.inp.CreateProductCommand

class ProductCreateReq(
    val carModelName: String, // CarModel의 이름을 나타내는 String
    val manufacturerName: String, // Manufacturer의 이름
    val modelYear: Int, // 연식
    val fuelType: String, // 연료 타입
    val color: String, // 색상
    val distance: Int,
    val rentalFee: Int,
    val licensePlate: String,
    val description: String,
    val images: MutableList<String>,
    val userUUID: String
) {
    fun toCommand(): CreateProductCommand {
        val carModel = CarModel(
            name = CarName.valueOf(carModelName),
            manufacturer = Manufacturer.valueOf(manufacturerName),
            modelYear = modelYear,
            fuel = Fuel.valueOf(fuelType)
        )

        return CreateProductCommand(
            carModel = carModel,
            color = ProductColor.valueOf(color.uppercase()),
            distance = distance,
            rentalFee = rentalFee,
            licensePlate = licensePlate,
            status = ProductStatus.REGISTERED,
            description = description,
            images = images,
            userUUID = userUUID
        )
    }
}
