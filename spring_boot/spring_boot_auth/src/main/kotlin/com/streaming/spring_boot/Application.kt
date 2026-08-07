package com.streaming.spring_boot

//import com.streaming.spring_boot.user.client.CatalogClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
//@EnableFeignClients(
//	basePackageClasses = [CatalogClient::class]
//)
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
