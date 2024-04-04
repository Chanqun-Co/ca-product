package io.sharing.server.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class CaProductApplication

fun main(args: Array<String>) {
    runApplication<CaProductApplication>(*args)
}
