package com.streaming.spring_boot.favourites

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class FavouritesApplication

fun main(args: Array<String>) {
	runApplication<FavouritesApplication>(*args)
}
