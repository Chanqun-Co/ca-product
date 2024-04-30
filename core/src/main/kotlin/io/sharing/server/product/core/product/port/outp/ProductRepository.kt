package io.sharing.server.product.core.product.port.outp

import io.sharing.server.product.core.product.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long>
