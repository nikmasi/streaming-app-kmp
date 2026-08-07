package com.streaming.spring_boot.user.client

import com.streaming.spring_boot.user.model.CatalogInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

//@FeignClient(
//    name = "catalog",
//    url = "\${catalog.url}"
//)
//interface CatalogClient {
//
//    @GetMapping("/api/v1/catalog/admin/info")
//    fun getInfo(): CatalogInfoResponse
//}
