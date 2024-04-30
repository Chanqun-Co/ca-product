package io.sharing.server.product.api

import io.sharing.server.product.api.dto.ProductCreateReq
import io.sharing.server.product.core.product.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product")
class ProductApi(private val productService: ProductService) {

    @PostMapping
    fun create(@RequestBody @Valid req: ProductCreateReq): ResponseEntity<Map<String, Long?>> {
        val createProductId = this.productService.createProduct(req.toCommand())
        return ResponseEntity.ok().body(mapOf("productId" to createProductId))
    }
}
