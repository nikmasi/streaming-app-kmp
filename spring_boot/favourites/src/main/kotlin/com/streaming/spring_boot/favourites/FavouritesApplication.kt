package com.streaming.spring_boot.favourites

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FavouritesApplication

fun main(args: Array<String>) {
	runApplication<FavouritesApplication>(*args)
}
